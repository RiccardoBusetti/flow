package com.riccardobusetti.flow.collectors;

public class JoinCollector<T> implements Collector<T, String> {

    private static final String EMPTY = "";

    private final StringBuilder stringBuilder = new StringBuilder();
    private final String joinString;

    public JoinCollector(String joinString) {
        this.joinString = joinString;
    }

    @Override
    public boolean onElement(T element) {
        stringBuilder.append(element)
                .append(joinString);

        return true;
    }

    @Override
    public String getResult() {
        String result = stringBuilder.toString();

        return joinString.equals(EMPTY) ? result : result.substring(0, result.length() - 1);
    }
}
