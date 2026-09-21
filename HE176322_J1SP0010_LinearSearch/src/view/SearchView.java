package view;

import constants.Constants;
import constants.Message;
import dto.SearchResponseDTO;

/**
 * VIEW: prints the array and where the searched value is. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
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

    // Prints the two result lines of the brief's screen: the array, then the index.
    public void display() {
        System.out.println(String.format(Message.THE_ARRAY, responseDTO.getNumberArray()));

        // every element was checked and none matched
        if (responseDTO.getIndex() == Constants.NOT_FOUND) {
            System.out.println(String.format(Message.NOT_FOUND, responseDTO.getSearchValue()));
        } else {
            // the brief's line "Found 5 at index: 2"
            System.out.println(String.format(Message.FOUND,
                    responseDTO.getSearchValue(), responseDTO.getIndex()));
        }
    }
}
