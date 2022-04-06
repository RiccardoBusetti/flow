package com.riccardobusetti.flow.sources;

import java.util.Iterator;

public class IteratorFlowSource<T> implements FlowSource<T> {

    private final Iterator<T> iterator;

    public IteratorFlowSource(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public T consume() {
        return iterator != null ? iterator.next() : null;
    }

    @Override
    public boolean hasNext() {
        return iterator != null && iterator.hasNext();
    }
}
