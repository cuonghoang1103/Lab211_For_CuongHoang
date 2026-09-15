package view;

import constants.Constants;
import constants.Message;
import dto.EquationResponseDTO;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: prints the solution line and the three lines of numbers.
 *
 * @author HE176322
 */
public class EquationView {

    // The result to display, handed over by the controller.
    private EquationResponseDTO response;
    // Label of the odd line: the brief's two screens spell it differently.
    private String oddLabel = Message.LABEL_ODD;

    // Receives the result the next display() call will print.
    public void setResponse(EquationResponseDTO response) {
        this.response = response;
    }

    // Chooses the label of the odd line for the next display() call.
    public void setOddLabel(String oddLabel) {
        this.oddLabel = oddLabel;
    }

    // Prints the brief's four result lines.
    public void display() {
        System.out.println(formatSolution(response.getRoots()));
        System.out.println(oddLabel + join(response.getOddNumbers()));
        System.out.println(Message.LABEL_EVEN + join(response.getEvenNumbers()));
        System.out.println(Message.LABEL_SQUARE + join(response.getSquareNumbers()));
    }

    // Writes the solution line for the brief's three-state roots.
    private String formatSolution(ArrayList<Float> roots) {
        // null: the equation has no solution
        if (roots == null) {
            return Message.NO_SOLUTION;
        }
        // empty: every x is a solution
        if (roots.isEmpty()) {
            return Message.INFINITE_SOLUTIONS;
        }
        // two roots: a real quadratic
        if (roots.size() == Constants.TWO_ROOTS) {
            return String.format(Locale.US, Message.TWO_SOLUTIONS, roots.get(0),
                    roots.get(1));
        }
        return String.format(Locale.US, Message.ONE_SOLUTION, roots.get(0));
    }

    // Joins numbers as "5.0, -1.25" - each written by Float.toString, like the brief's
    // screen.
    private String join(ArrayList<Float> numbers) {
        StringBuilder text = new StringBuilder();
        // the separator goes before every number except the first
        for (Float number : numbers) {
            // not the first number: separate it from the previous one
            if (text.length() > 0) {
                text.append(Constants.SEPARATOR);
            }
            text.append(number);
        }
        return text.toString();
    }
}
