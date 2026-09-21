package view;

import constants.Message;
import dto.ConvertResponseDTO;

/**
 * VIEW: prints "535 (DEC) = 217 (HEX)". It receives the data through its attribute (the
 * ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ConvertView {

    // The result to print, handed over by the controller.
    private ConvertResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(ConvertResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the result line: input = output.
    public void display() {
        System.out.println(String.format(Message.RESULT, responseDTO.getInput(),
                responseDTO.getOutput()));
    }
}
