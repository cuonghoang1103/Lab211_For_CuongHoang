package repository;

import model.LargeNumber;

/**
 * REPOSITORY: holds the data the algorithm works on - the two numbers typed, each as an
 * array of digits (the model) - and only simple CRUD on it. No multiplication, no print.
 *
 * @author HE176322
 */
public class NumberRepository {

    // The first number typed, as its digits.
    private LargeNumber firstNumber;

    // The second number typed, as its digits.
    private LargeNumber secondNumber;

    // Creates the store with both numbers equal to 0 (the JavaBean LargeNumber()).
    public NumberRepository() {
        firstNumber = new LargeNumber();
        secondNumber = new LargeNumber();
    }

    // Update: keeps the first number.
    public void saveFirstNumber(LargeNumber largeNumber) {
        firstNumber = largeNumber;
    }

    // Update: keeps the second number.
    public void saveSecondNumber(LargeNumber largeNumber) {
        secondNumber = largeNumber;
    }

    // Read: returns the first number.
    public LargeNumber getFirstNumber() {
        return firstNumber;
    }

    // Read: returns the second number.
    public LargeNumber getSecondNumber() {
        return secondNumber;
    }
}
