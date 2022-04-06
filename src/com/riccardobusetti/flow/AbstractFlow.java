package com.riccardobusetti.flow;

import com.riccardobusetti.flow.step.StepResult;

public abstract class AbstractFlow<IN> extends SourceFlow<IN> {

    protected AbstractFlow() {
    }

    @Override
    public StepResult<IN> next() {
        return onNext();
    }

    @Override
    public boolean hasNext() {
        return onHasNext();
    }

    abstract StepResult<IN> onNext();

    abstract boolean onHasNext();
}
