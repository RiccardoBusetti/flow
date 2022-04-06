package com.riccardobusetti.flow.collectors;

import java.util.List;

public interface Collector<T, R> {

    static <T> Collector<T, List<T>> asList() {
        return new ListCollector<>();
    }

    static <T> Collector<T, Integer> count() {
        return new CountCollector<>();
    }

    static <T> Collector<T, String> join(String joinString) {
        return new JoinCollector<>(joinString);
    }

    boolean onElement(T element);

    R getResult();
}
