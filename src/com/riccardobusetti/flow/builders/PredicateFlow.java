package com.riccardobusetti.flow.builders;

import com.riccardobusetti.flow.BaseFlow;
import com.riccardobusetti.flow.step.Step;
import com.riccardobusetti.flow.step.StepResult;

@Deprecated
public class PredicateFlow<UP> extends StreamFlow<UP, UP> {

    private final Step<UP, Boolean> step;

    protected PredicateFlow(BaseFlow<UP> upstream, Step<UP, Boolean> step) {
        super(upstream);
        this.step = step;
    }

    @Override
    public StepResult<UP> next() {
        if (upstream != null) {
            StepResult<UP> upResult = upstream.next();

            if (upResult.canSkip()) {
                return StepResult.skip();
            } else {
                if (step.accept(upResult.value())) {
                    return upResult;
                } else {
                    return StepResult.skip();
                }
            }
        } else {
            return null;
        }
    }
}
