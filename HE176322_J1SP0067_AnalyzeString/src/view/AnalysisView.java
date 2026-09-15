package view;

import constants.Message;
import dto.AnalysisResponseDTO;

/**
 * VIEW: prints the result block of the brief.
 *
 * @author HE176322
 */
public class AnalysisView {

    // The result to display, handed over by the controller.
    private AnalysisResponseDTO response;

    // Creates the view.
    public AnalysisView() {
    }

    // Receives the result the next display() call will print.
    public void setResponse(AnalysisResponseDTO response) {
        this.response = response;
    }

    // Prints the title, the number of characters, then the eight lines of the brief's
    // screen in the brief's order.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(Message.LABEL_LENGTH + response.getLength());
        System.out.println(Message.LABEL_SQUARE + response.getSquareNumbers());
        System.out.println(Message.LABEL_ODD + response.getOddNumbers());
        System.out.println(Message.LABEL_EVEN + response.getEvenNumbers());
        System.out.println(Message.LABEL_ALL_NUMBERS + response.getAllNumbers());
        System.out.println(Message.LABEL_UPPER + response.getUppercase());
        System.out.println(Message.LABEL_LOWER + response.getLowercase());
        System.out.println(Message.LABEL_SPECIAL + response.getSpecial());
        System.out.println(Message.LABEL_ALL_CHARS + response.getAllCharacters());
    }
}
