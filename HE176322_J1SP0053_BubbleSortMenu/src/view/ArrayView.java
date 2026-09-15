package view;

import constants.Constants;
import constants.Message;
import dto.SortResponseDTO;

/**
 * VIEW: prints the sorted array in the brief's arrow format.
 *
 * @author HE176322
 */
public class ArrayView {

    // The sorted array to display, handed over by the controller.
    private SortResponseDTO response;

    // Creates the view.
    public ArrayView() {
    }

    // Receives the result the next display call will print.
    public void setResponse(SortResponseDTO response) {
        this.response = response;
    }

    // Prints the ascending screen: "[1]->[3]->[5]".
    public void displayAscending() {
        System.out.println(Message.TITLE_ASCENDING);
        System.out.println(join(Constants.ARROW_ASCENDING));
    }

    // Prints the descending screen: "[5]<-[3]<-[1]".
    public void displayDescending() {
        System.out.println(Message.TITLE_DESCENDING);
        System.out.println(join(Constants.ARROW_DESCENDING));
    }

    // Writes every element as "[x]" with the arrow between two elements.
    private String join(String arrow) {
        int[] values = response.getValues();
        StringBuilder line = new StringBuilder();
        // one "[x]" per element, in display order
        for (int i = 0; i < values.length; i++) {
            // the arrow goes BETWEEN elements, not before the first
            if (i > 0) {
                line.append(arrow);
            }
            line.append(String.format(Constants.ELEMENT_FORMAT, values[i]));
        }
        return line.toString();
    }
}
