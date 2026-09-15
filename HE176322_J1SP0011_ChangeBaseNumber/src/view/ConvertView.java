package view;

import constants.Message;
import dto.ConvertResponseDTO;

/**
 * VIEW: prints "535 (DEC) = 217 (HEX)".
 *
 * @author HE176322
 */
public class ConvertView {

    // The result to display, handed over by the controller.
    private ConvertResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(ConvertResponseDTO response) {
        this.response = response;
    }

    // Prints the result line.
    public void display() {
        System.out.println(String.format(Message.RESULT, response.getInput(),
                response.getOutput()));
    }
}
