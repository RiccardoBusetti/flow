package com.riccardobusetti.flow.collectors;

public class CountCollector<T> implements Collector<T, Integer> {

    private Integer count = 0;

    @Override
    public boolean onElement(T element) {
        count++;
        return true;
    }

    @Override
    public Integer getResult() {
        return count;
    }
}
