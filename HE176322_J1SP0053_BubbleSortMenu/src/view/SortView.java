package view;

import constants.Constants;
import constants.Message;
import dto.SortResponseDTO;

/**
 * VIEW: prints the sorted array in the brief's arrow format. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class SortView {

    // The sorted array to print, handed over by the controller.
    private SortResponseDTO responseDTO;

    // Creates the view.
    public SortView() {
    }

    // Receives the result the next display() will print.
    public void setResponseDTO(SortResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the screen of option 2 ("[1]->[3]->[5]") or of option 3 ("[5]<-[3]<-[1]"),
    // following the order the response is in.
    public void display() {
        // ascending: the title of option 2 and arrows pointing right
        if (responseDTO.isAscending()) {
            System.out.println(Message.TITLE_ASCENDING);
            System.out.println(join(Constants.ARROW_ASCENDING));
        } else {
            // descending: the title of option 3 and arrows pointing left
            System.out.println(Message.TITLE_DESCENDING);
            System.out.println(join(Constants.ARROW_DESCENDING));
        }
    }

    // Writes every element as "[x]" with the arrow between two elements.
    private String join(String arrow) {
        int[] sortedArray = responseDTO.getSortedArray();
        StringBuilder line = new StringBuilder();

        // one "[x]" per element, in display order
        for (int i = 0; i < sortedArray.length; i++) {
            // the arrow goes BETWEEN elements, not before the first
            if (i > 0) {
                line.append(arrow);
            }

            // the element itself, as "[x]"
            line.append(String.format(Constants.ELEMENT_FORMAT, sortedArray[i]));
        }

        return line.toString();
    }
}
