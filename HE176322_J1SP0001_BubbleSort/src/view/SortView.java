package view;

import constants.Message;
import dto.SortResponseDTO;

/**
 * VIEW: prints the arrays before and after sorting.
 *
 * @author HE176322
 */
public class SortView {

    // The result to display, handed over by the controller.
    private SortResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(SortResponseDTO response) {
        this.response = response;
    }

    // Prints the two lines of the brief's screen.
    public void display() {
        System.out.println(Message.LABEL_UNSORTED + response.getUnsorted());
        System.out.println(Message.LABEL_SORTED + response.getSorted());
    }
}
