package view;

import constants.Message;
import dto.MultiplyResponseDTO;

/**
 * VIEW: prints "first x second = product". It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class MultiplyView {

    // The result to display, handed over by the controller.
    private MultiplyResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(MultiplyResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the result line of the brief's screen.
    public void display() {
        System.out.println(String.format(Message.RESULT, responseDTO.getFirstNumber(),
                responseDTO.getSecondNumber(), responseDTO.getProduct()));
    }
}
