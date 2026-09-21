package view;

import constants.Message;
import dto.MatrixResponseDTO;

/**
 * VIEW: prints the result block of the brief's screen. It receives the data through its
 * attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class MatrixView {

    // The result to print, handed over by the controller.
    private MatrixResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(MatrixResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints: title, matrix 1, symbol, matrix 2, "=", result.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(responseDTO.getFirstMatrix());
        System.out.println(responseDTO.getSymbol());
        System.out.println(responseDTO.getSecondMatrix());
        System.out.println(Message.SYMBOL_EQUAL);
        System.out.println(responseDTO.getResultMatrix());
    }
}
