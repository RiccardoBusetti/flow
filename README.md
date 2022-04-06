# flow
Flow is a simple framework that is inspired by Java Streams, RxJava and Kotlin Flows, that offers a simple
api for working with cold streams of data.

The streams of data are evaluated **lazily**, meaning that only via a collector
you can start consuming the data. It is important to note that flow can only by
collected only **once**.

*This project was made for experimenting with framework
design and is not intended to be used as a library.*

## Example Usage
In this example we are trying to merge the even and odds number in an array.
```java
Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

Flow<Integer> evens = Flow.of(numbers)
        .filter(i -> i % 2 == 0);

Flow<Integer> odds = Flow.of(numbers)
        .filter(i -> i % 2 != 0);

// "pairs" contains ["2 1", "4 3", "6 5", "8 7", "10 9"]
List<String> pairs = evens.zip(odds)
        .map(pair -> pair.getKey() + " " + pair.getValue())
        .peek(System.out::println)
        .collect(Collector.asList());
```
