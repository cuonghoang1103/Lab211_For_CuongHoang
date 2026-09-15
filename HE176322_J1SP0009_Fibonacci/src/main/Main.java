package main;

import controller.FibonacciController;

/**
 * MAIN: the work flow - call the controller once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        FibonacciController controller = new FibonacciController();
        controller.displaySequence();
    }
}
