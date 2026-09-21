package main;

import constants.Constants;
import controller.FibonacciController;
import dto.FibonacciRequestDTO;

/**
 * MAIN: the work flow. Nothing is typed, so main only puts the count of the brief (45)
 * in the request and calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: builds the request, then calls the controller once.
    public static void main(String[] args) {
        FibonacciController controller = new FibonacciController();
        FibonacciRequestDTO requestDTO = new FibonacciRequestDTO();

        // the brief's count, carried to the controller by the request
        requestDTO.setCount(Constants.SEQUENCE_LENGTH);

        // compute and display the sequence - the controller is called once
        controller.displaySequence(requestDTO);
    }
}
