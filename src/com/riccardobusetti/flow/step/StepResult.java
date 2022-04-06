package com.riccardobusetti.flow.step;

public class StepResult<T> {

    private boolean skip = false;
    private T value;

    private StepResult() {
        this.skip = true;
    }

    private StepResult(T value) {
        this.value = value;
    }

    public static <T> StepResult<T> of(T value) {
        return new StepResult<>(value);
    }

    public static <T> StepResult<T> skip() {
        return new StepResult<>();
    }

    public boolean canSkip() {
        return skip;
    }

    public T unwrap() {
        if (canSkip())
            throw new RuntimeException("You cannot access a skipped value.");

        return value;
    }

    public T value() {
        return value;
    }
}
