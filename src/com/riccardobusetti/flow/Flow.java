package com.riccardobusetti.flow;

import com.riccardobusetti.flow.collectors.Collector;
import com.riccardobusetti.flow.sources.ArrayFlowSource;
import com.riccardobusetti.flow.sources.FlowSource;
import com.riccardobusetti.flow.sources.IteratorFlowSource;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Flow<T> {

    @SafeVarargs
    static <T> Flow<T> of(T... elements) {
        return new SourceFlow<>(new ArrayFlowSource<>(elements));
    }

    static <T> Flow<T> of(Collection<T> collection) {
        Iterator<T> iterator = collection.iterator();

        return new SourceFlow<>(new IteratorFlowSource<>(iterator));
    }

    static <T> Flow<T> from(FlowSource<T> flowSource) {
        return new SourceFlow<>(flowSource);
    }

    static <T> Flow<T> from(Consumer<FlowScope<T>> block) {
        List<T> elements = new LinkedList<>();

        FlowScope<T> scope = elements::add;

        block.accept(scope);

        return new SourceFlow<>(new IteratorFlowSource<>(elements.iterator()));
    }

    <R> Flow<R> map(Function<T, R> block);

    Flow<T> filter(Function<T, Boolean> block);

    <R> Flow<Pair<T, R>> zip(Flow<R> flow);

    Flow<T> peek(Consumer<T> block);

    void collect(Consumer<T> block);

    <R> R collect(Collector<T, R> collector);
}
