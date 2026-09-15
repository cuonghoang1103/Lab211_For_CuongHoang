package model;

import constants.Constants;
import java.util.ArrayList;

/**
 * MODEL: a Fibonacci sequence - the numbers F(0), F(1), ...
 *
 * @author HE176322
 */
public class FibonacciSequence {

    // The numbers, F(0) first.
    private ArrayList<Long> numbers;

    // JavaBean constructor: an empty sequence.
    public FibonacciSequence() {
        this.numbers = new ArrayList<>();
    }

    // Returns the numbers.
    public ArrayList<Long> getNumbers() {
        return numbers;
    }

    // Replaces the numbers.
    public void setNumbers(ArrayList<Long> numbers) {
        this.numbers = numbers;
    }

    // Appends the next number at the end of the sequence.
    public void addNumber(long number) {
        numbers.add(number);
    }

    // Returns how many numbers the sequence holds.
    public int getSize() {
        return numbers.size();
    }

    // Polymorphism: overrides Object.toString() to give "0, 1, 1, 2", the format of the
    // brief's screen.
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        // append every number, with a separator before all but the first
        for (int i = 0; i < numbers.size(); i++) {
            // no separator in front of the very first number
            if (i > 0) {
                text.append(Constants.SEPARATOR);
            }
            text.append(numbers.get(i));
        }
        return text.toString();
    }
}
