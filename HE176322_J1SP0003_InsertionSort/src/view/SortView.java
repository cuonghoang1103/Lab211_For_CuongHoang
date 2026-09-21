package view;

import constants.Message;
import dto.SortResponseDTO;

/**
 * VIEW: prints the arrays before and after sorting. It receives the data through its
 * attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class SortView {

    // The result to print, handed over by the controller.
    private SortResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(SortResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the two result lines of the brief's screen: the array before, then after
    // sorting.
    public void display() {
        System.out.println(String.format(Message.UNSORTED_ARRAY, responseDTO.getUnsortedArray()));
        System.out.println(String.format(Message.SORTED_ARRAY, responseDTO.getSortedArray()));
    }
}
