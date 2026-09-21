package view;

import constants.Message;
import dto.FibonacciResponseDTO;

/**
 * VIEW: prints the title and the sequence. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class FibonacciView {

    // The result to print, handed over by the controller.
    private FibonacciResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(FibonacciResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the two lines of the brief's screen: the title, then the sequence.
    public void display() {
        System.out.println(String.format(Message.TITLE, responseDTO.getCount()));
        System.out.println(responseDTO.getSequence());
    }
}
