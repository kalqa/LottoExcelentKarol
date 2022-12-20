package pl.lotto.numberreceiver;

import java.util.Collection;
import java.util.Collection;

class NumberValidator {

    private final int MIN = 1;
    private final int MAX = 99;
    private final int AMOUNT_OF_NUMBERS = 6;

    boolean validate(Collection<Integer> numbers) {
        return CollectionContainsSixNumbers(numbers)
                && numbersInValidRange(numbers)
                && CollectionContainsDistinctNumbers(numbers);
    }

    private boolean CollectionContainsSixNumbers(Collection<Integer> numbers) {
        return numbers.size() == AMOUNT_OF_NUMBERS;
    }

    private boolean CollectionContainsDistinctNumbers(Collection<Integer> numbers) {
        int actualAmountOfNumbers = (int) numbers.stream()
                .distinct()
                .count();
        return actualAmountOfNumbers == AMOUNT_OF_NUMBERS;
    }

    private boolean numbersInValidRange(Collection<Integer> numbers) {
        return numbers.stream()
                .allMatch(a -> a >= MIN && a <= MAX);
    }


}
