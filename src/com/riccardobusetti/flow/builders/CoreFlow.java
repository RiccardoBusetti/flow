package com.riccardobusetti.flow.builders;

import com.riccardobusetti.flow.BaseFlow;
import com.riccardobusetti.flow.step.Step;
import com.riccardobusetti.flow.step.StepResult;

@Deprecated
public class CoreFlow<UP, IN> extends StreamFlow<UP, IN> {

    private final Step<UP, IN> step;

    protected CoreFlow(BaseFlow<UP> upstream, Step<UP, IN> step) {
        super(upstream);
        this.step = step;
    }

    @Override
    public StepResult<IN> next() {
        if (upstream != null) {
            StepResult<UP> upResult = upstream.next();

            if (upResult.canSkip()) {
                return StepResult.skip();
            } else {
                return StepResult.of(step.accept(upResult.value()));
            }
        } else {
            return null;
        }
    }
}
