package com.riccardobusetti.flow.builders;

import com.riccardobusetti.flow.BaseFlow;
import com.riccardobusetti.flow.SourceFlow;
import com.riccardobusetti.flow.step.StepResult;

@Deprecated
public class StreamFlow<UP, IN> extends SourceFlow<IN> {

    protected final BaseFlow<UP> upstream;

    protected StreamFlow(BaseFlow<UP> upstream) {
        this.upstream = upstream;
    }

    @Override
    public StepResult<IN> next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        if (upstream != null) {
            return upstream.hasNext();
        } else {
            return false;
        }
    }
}