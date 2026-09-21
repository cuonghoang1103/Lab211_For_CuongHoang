package view;

import constants.Constants;
import constants.Message;
import dto.EquationResponseDTO;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: prints the solution line and the three lines of numbers. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class EquationView {

    // The result to print, handed over by the controller.
    private EquationResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(EquationResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the brief's four result lines: the solution, then the odd, even and perfect
    // square numbers (the label of the odd line comes with the result).
    public void display() {
        System.out.println(formatSolution(responseDTO.getRootList()));
        System.out.println(String.format(responseDTO.getOddLabel(),
                joinNumbers(responseDTO.getOddNumberList())));
        System.out.println(String.format(Message.LABEL_EVEN,
                joinNumbers(responseDTO.getEvenNumberList())));
        System.out.println(String.format(Message.LABEL_SQUARE,
                joinNumbers(responseDTO.getSquareNumberList())));
    }

    // Writes the solution line for the brief's three-state roots.
    private String formatSolution(ArrayList<Float> rootList) {
        // null: the equation has no solution
        if (rootList == null) {
            return Message.NO_SOLUTION;
        }

        // empty: every x is a solution
        if (rootList.isEmpty()) {
            return Message.INFINITE_SOLUTIONS;
        }

        // two roots: a real quadratic
        if (rootList.size() == Constants.TWO_ROOTS) {
            return String.format(Locale.US, Message.TWO_SOLUTIONS, rootList.get(0),
                    rootList.get(1));
        }

        // one root: ax + b = 0, or a quadratic with a = 0
        return String.format(Locale.US, Message.ONE_SOLUTION, rootList.get(0));
    }

    // Joins numbers as "5.0, -1.25" - each written by Float.toString, like the brief's
    // screen.
    private String joinNumbers(ArrayList<Float> numberList) {
        StringBuilder text = new StringBuilder();

        // the separator goes before every number except the first
        for (Float number : numberList) {
            // not the first number: separate it from the previous one
            if (text.length() > 0) {
                text.append(Constants.SEPARATOR);
            }

            // then the number itself, as Float.toString writes it
            text.append(number);
        }

        return text.toString();
    }
}
