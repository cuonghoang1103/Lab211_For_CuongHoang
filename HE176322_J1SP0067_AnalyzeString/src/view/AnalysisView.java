package view;

import constants.Message;
import dto.AnalysisResponseDTO;

/**
 * VIEW: prints the result block of the brief. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class AnalysisView {

    // The result to display, handed over by the controller.
    private AnalysisResponseDTO responseDTO;

    // Creates the view.
    public AnalysisView() {
    }

    // Receives the result the next display() call will print.
    public void setResponseDTO(AnalysisResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the title, the number of characters, then the eight lines of the brief's
    // screen in the brief's order.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(String.format(Message.RESULT_LENGTH, responseDTO.getLength()));
        System.out.println(String.format(Message.RESULT_SQUARE, responseDTO.getSquareNumbers()));
        System.out.println(String.format(Message.RESULT_ODD, responseDTO.getOddNumbers()));
        System.out.println(String.format(Message.RESULT_EVEN, responseDTO.getEvenNumbers()));
        System.out.println(String.format(Message.RESULT_ALL_NUMBERS, responseDTO.getAllNumbers()));
        System.out.println(String.format(Message.RESULT_UPPER, responseDTO.getUppercase()));
        System.out.println(String.format(Message.RESULT_LOWER, responseDTO.getLowercase()));
        System.out.println(String.format(Message.RESULT_SPECIAL, responseDTO.getSpecial()));
        System.out.println(String.format(Message.RESULT_ALL_CHARS,
                responseDTO.getAllCharacters()));
    }
}
