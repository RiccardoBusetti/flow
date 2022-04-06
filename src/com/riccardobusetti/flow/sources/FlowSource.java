package com.riccardobusetti.flow.sources;

public interface FlowSource<T> {

    T consume();

    boolean hasNext();
}
