package repository;

import model.FibonacciSequence;

/**
 * REPOSITORY: holds the data of the program - the Fibonacci sequence the service computes
 * - and only simple CRUD on it. No computing, no print.
 *
 * @author HE176322
 */
public class FibonacciRepository {

    // The sequence the program shows (the model), F(0) first.
    private FibonacciSequence fibonacciSequence;

    // Creates the store with an empty sequence.
    public FibonacciRepository() {
        fibonacciSequence = new FibonacciSequence();
    }

    // Create: appends the next computed number at the end of the sequence.
    public void addNumber(long number) {
        fibonacciSequence.addNumber(number);
    }

    // Read: returns the sequence kept so far.
    public FibonacciSequence getFibonacciSequence() {
        return fibonacciSequence;
    }
}
