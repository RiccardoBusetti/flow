package com.riccardobusetti.flow;

public interface FlowScope<T> {

    void emit(T value);
}
