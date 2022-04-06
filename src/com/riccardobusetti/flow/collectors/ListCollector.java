package com.riccardobusetti.flow.collectors;

import java.util.ArrayList;
import java.util.List;

public class ListCollector<T> implements Collector<T, List<T>> {

    private final List<T> elements = new ArrayList<>();

    protected ListCollector() {
    }

    @Override
    public boolean onElement(T element) {
        elements.add(element);

        return true;
    }

    @Override
    public List<T> getResult() {
        return elements;
    }
}
