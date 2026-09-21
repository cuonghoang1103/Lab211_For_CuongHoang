package view;

import constants.Constants;
import constants.Message;
import dto.SearchResponseDTO;

/**
 * VIEW: prints the sorted array and where the searched value is. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class SearchView {

    // The result to print, handed over by the controller.
    private SearchResponseDTO responseDTO;

    // Receives the result the next display() will print.
    public void setResponseDTO(SearchResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the two result lines of the brief's screen: the sorted array, then the index.
    public void display() {
        System.out.println(String.format(Message.SORTED_ARRAY, responseDTO.getSortedArray()));

        // the search reached an empty part: the value is absent
        if (responseDTO.getIndex() == Constants.NOT_FOUND) {
            System.out.println(String.format(Message.NOT_FOUND, responseDTO.getSearchValue()));
        } else {
            // the brief's line "Found 4 at index: 5"
            System.out.println(String.format(Message.FOUND,
                    responseDTO.getSearchValue(), responseDTO.getIndex()));
        }
    }
}
