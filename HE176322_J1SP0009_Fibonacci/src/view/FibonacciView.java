package view;

import constants.Message;
import dto.FibonacciResponseDTO;

/**
 * VIEW: prints the title and the sequence.
 *
 * @author HE176322
 */
public class FibonacciView {

    // The result to display, handed over by the controller.
    private FibonacciResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(FibonacciResponseDTO response) {
        this.response = response;
    }

    // Prints the two lines of the brief's screen.
    public void display() {
        System.out.println(String.format(Message.TITLE, response.getCount()));
        System.out.println(response.getSequence());
    }
}
