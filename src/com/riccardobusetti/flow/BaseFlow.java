package com.riccardobusetti.flow;

import com.riccardobusetti.flow.step.StepResult;

public interface BaseFlow<T> {

    StepResult<T> next();

    boolean hasNext();
}
