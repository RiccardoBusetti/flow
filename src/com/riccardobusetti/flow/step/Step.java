package com.riccardobusetti.flow.step;

public interface Step<T, E> {

    E accept(T input);
}
