package com.riccardobusetti.flow;

import com.riccardobusetti.flow.collectors.Collector;
import com.riccardobusetti.flow.sources.FlowSource;
import com.riccardobusetti.flow.step.StepResult;
import javafx.util.Pair;

import java.util.function.Consumer;
import java.util.function.Function;

public class SourceFlow<IN> implements Flow<IN>, BaseFlow<IN> {

    private boolean collected = false;
    private FlowSource<IN> flowSource;

    protected SourceFlow() {
    }

    protected SourceFlow(FlowSource<IN> flowSource) {
        this.flowSource = flowSource;
    }

    private SourceFlow<IN> getUpStream() {
        return SourceFlow.this;
    }

    private <T> T consumeUntilValid(BaseFlow<T> flow) {
        while (flow.hasNext()) {
            StepResult<T> next = flow.next();
            if (!next.canSkip())
                return next.value();
        }

        return null;
    }

    @Override
    public StepResult<IN> next() {
        if (flowSource != null) {
            return StepResult.of(flowSource.consume());
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        if (flowSource != null) {
            return flowSource.hasNext();
        } else {
            return false;
        }
    }

    @Override
    public <DOWN> Flow<DOWN> map(Function<IN, DOWN> block) {
        return new AbstractFlow<>() {
            @Override
            StepResult<DOWN> onNext() {
                StepResult<IN> upResult = getUpStream().next();

                if (upResult.canSkip()) {
                    return StepResult.skip();
                } else {
                    return StepResult.of(block.apply(upResult.value()));
                }
            }

            @Override
            boolean onHasNext() {
                return getUpStream().hasNext();
            }
        };
    }

    @Override
    public Flow<IN> filter(Function<IN, Boolean> block) {
        return new AbstractFlow<>() {
            @Override
            StepResult<IN> onNext() {
                StepResult<IN> upResult = getUpStream().next();

                if (upResult.canSkip()) {
                    return StepResult.skip();
                } else {
                    if (block.apply(upResult.value())) {
                        return upResult;
                    } else {
                        return StepResult.skip();
                    }
                }
            }

            @Override
            boolean onHasNext() {
                return getUpStream().hasNext();
            }
        };
    }

    @Override
    public <R> Flow<Pair<IN, R>> zip(Flow<R> flow) {
        if (!(flow instanceof BaseFlow)) {
            throw new RuntimeException("You must combine a valid flow.");
        }

        return new AbstractFlow<>() {
            @Override
            StepResult<Pair<IN, R>> onNext() {
                // We iterate until we find a valid element, otherwise we return null.
                IN up = consumeUntilValid(getUpStream());
                R other = consumeUntilValid((BaseFlow<R>) flow);

                if (up != null && other != null) {
                    return StepResult.of(new Pair<>(up, other));
                } else {
                    return StepResult.skip();
                }
            }

            @Override
            boolean onHasNext() {
                return getUpStream().hasNext() && ((BaseFlow<R>) flow).hasNext();
            }
        };
    }

    @Override
    public Flow<IN> peek(Consumer<IN> block) {
        return new AbstractFlow<>() {
            @Override
            StepResult<IN> onNext() {
                StepResult<IN> upResult = getUpStream().next();

                if (!upResult.canSkip()) {
                    block.accept(upResult.value());
                }

                return upResult;
            }

            @Override
            boolean onHasNext() {
                return getUpStream().hasNext();
            }
        };
    }

    @Override
    public void collect(Consumer<IN> block) {
        if (collected)
            throw new RuntimeException("You can't collect a flow multiple times");

        while (hasNext()) {
            StepResult<IN> result = next();

            if (!result.canSkip()) {
                block.accept(result.value());
            }
        }

        collected = true;
    }

    @Override
    public <R> R collect(Collector<IN, R> collector) {
        var wrapper = new Object() {
            boolean skip = false;
            boolean empty = true;
        };

        collect(element -> {
            wrapper.empty = false;

            if (!wrapper.skip) {
                boolean result = collector.onElement(element);
                wrapper.skip = !result;
            }
        });

        // We check whether the getResult only if the flow has at least an element because
        // otherwise we increase the likelihood of null pointer in the collector.
        return wrapper.empty ? null : collector.getResult();
    }
}
