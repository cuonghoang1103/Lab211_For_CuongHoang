package view;

import constants.Message;
import dto.MatrixResponseDTO;

/**
 * VIEW: prints the result block of the brief's screen.
 *
 * @author HE176322
 */
public class MatrixView {

    // The result to display, handed over by the controller.
    private MatrixResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(MatrixResponseDTO response) {
        this.response = response;
    }

    // Prints: title, matrix 1, symbol, matrix 2, "=", result.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(response.getFirstMatrix());
        System.out.println(response.getSymbol());
        System.out.println(response.getSecondMatrix());
        System.out.println(Message.SYMBOL_EQUAL);
        System.out.println(response.getResultMatrix());
    }
}
