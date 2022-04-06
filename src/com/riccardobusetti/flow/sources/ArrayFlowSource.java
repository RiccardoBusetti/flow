package com.riccardobusetti.flow.sources;

public class ArrayFlowSource<T> implements FlowSource<T> {

    private final T[] array;
    private int index = 0;

    public ArrayFlowSource(T[] array) {
        this.array = array;
    }

    @Override
    public T consume() {
        return array[index++];
    }

    @Override
    public boolean hasNext() {
        return index < array.length;
    }
}
