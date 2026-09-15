package view;

import constants.Message;
import dto.MultiplyResponseDTO;

/**
 * VIEW: prints "first x second = product".
 *
 * @author HE176322
 */
public class MultiplyView {

    // The result to display, handed over by the controller.
    private MultiplyResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(MultiplyResponseDTO response) {
        this.response = response;
    }

    // Prints the result line of the brief's screen.
    public void display() {
        System.out.println(String.format(Message.RESULT, response.getFirstNumber(),
                response.getSecondNumber(), response.getProduct()));
    }
}
