import com.riccardobusetti.flow.Flow;
import com.riccardobusetti.flow.collectors.Collector;
import com.riccardobusetti.flow.sources.FlowSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        countEvenInList();
        wordToUpperCase();
        zipEvensAndOdds();
        randomNumbersMultipliedByTen();
    }

    private static void countEvenInList() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        Integer count = Flow.of(numbers)
                .filter(number -> number % 2 == 0)
                .collect(Collector.count());

        System.out.println(count);
    }

    private static void wordToUpperCase() {
        String upperWord = Flow.<Character>from(scope -> {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("What is the the word: ");
                    String word = scanner.next();

                    for (int i = 0; i < word.length(); i++) {
                        scope.emit(word.charAt(i));
                    }
                })
                .map(element -> element.toString().toUpperCase())
                .collect(Collector.join(""));

        System.out.println(upperWord);
    }

    private static void zipEvensAndOdds() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Flow<Integer> evens = Flow.of(numbers)
                .filter(i -> i % 2 == 0);

        Integer[] otherNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Flow<Integer> odds = Flow.of(otherNumbers)
                .filter(i -> i % 2 != 0);

        // When we combine, we combine until the end of the smallest flow.
        evens.zip(odds)
                .map(pair -> pair.getKey() + " " + pair.getValue())
                .peek(System.out::println)
                .collect(Collector.asList());
    }

    private static void randomNumbersMultipliedByTen() {
        FlowSource<Integer> randomSource = new FlowSource<>() {
            private Integer lastRandom = -1;

            @Override
            public Integer consume() {
                lastRandom = new Random().nextInt(10);
                return lastRandom;
            }

            @Override
            public boolean hasNext() {
                return lastRandom != null && lastRandom != 5;
            }
        };

        String result = Flow.from(randomSource)
                .peek(it -> System.out.println("Generated random number: " + it))
                .map(it -> it * 10)
                .collect(Collector.join(" "));

        System.out.println("Final numbers * 10: " + result);
    }
}
