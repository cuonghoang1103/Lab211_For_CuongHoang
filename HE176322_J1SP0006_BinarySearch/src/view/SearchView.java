package view;

import constants.Constants;
import constants.Message;
import dto.SearchResponseDTO;

/**
 * VIEW: prints the sorted array and where the searched value is.
 *
 * @author HE176322
 */
public class SearchView {

    // The result to display, handed over by the controller.
    private SearchResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(SearchResponseDTO response) {
        this.response = response;
    }

    // Prints the two result lines of the brief's screen.
    public void display() {
        System.out.println(Message.LABEL_SORTED + response.getSortedArray());
        // the search reached an empty part: the value is absent
        if (response.getIndex() == Constants.NOT_FOUND) {
            System.out.println(String.format(Message.NOT_FOUND,
                    response.getSearchValue()));
        } else {
            // the brief's line "Found 4 at index: 5"
            System.out.println(String.format(Message.FOUND,
                    response.getSearchValue(), response.getIndex()));
        }
    }
}
