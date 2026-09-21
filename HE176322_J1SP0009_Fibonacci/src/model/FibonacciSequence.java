package model;

import constants.Constants;
import java.util.ArrayList;

/**
 * MODEL: a Fibonacci sequence - the numbers F(0), F(1), ... - a JavaBean (private field,
 * public no-argument constructor, getter/setter). It only describes the sequence: no
 * print, no input.
 *
 * @author HE176322
 */
public class FibonacciSequence {

    // The numbers, F(0) first.
    private ArrayList<Long> numberList;

    // JavaBean constructor: an empty sequence.
    public FibonacciSequence() {
        numberList = new ArrayList<>();
    }

    // Returns the numbers.
    public ArrayList<Long> getNumberList() {
        return numberList;
    }

    // Replaces the numbers.
    public void setNumberList(ArrayList<Long> numberList) {
        this.numberList = numberList;
    }

    // Appends the next number at the end of the sequence.
    public void addNumber(long number) {
        numberList.add(number);
    }

    // Returns how many numbers the sequence holds.
    public int getSize() {
        return numberList.size();
    }

    // Polymorphism: overrides Object.toString() to give "0, 1, 1, 2", the format of the
    // brief's screen.
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        // append every number, with a separator before all but the first
        for (int i = 0; i < numberList.size(); i++) {
            // no separator in front of the very first number
            if (i > 0) {
                text.append(Constants.SEPARATOR);
            }

            // then the number itself
            text.append(numberList.get(i));
        }

        return text.toString();
    }
}
