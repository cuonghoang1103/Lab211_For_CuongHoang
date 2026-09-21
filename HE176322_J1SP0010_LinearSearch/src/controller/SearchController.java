package controller;

import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import service.SearchService;
import view.SearchView;

/**
 * CONTROLLER (Facade): takes the request from main, lets the service do the work, and hands
 * the result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class SearchController {

    // Generates, keeps and searches the array (Controller -> Service -> Repository -> Model).
    private SearchService searchService;

    // Prints the result.
    private SearchView searchView;

    // Creates the controller together with its service and its view.
    public SearchController() {
        searchService = new SearchService();
        searchView = new SearchView();
    }

    // The only workflow (the brief's Function 2): the service searches, the view shows the
    // result ONCE.
    public void searchArray(SearchRequestDTO requestDTO) {
        SearchResponseDTO responseDTO = searchService.searchRandomArray(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        searchView.setResponseDTO(responseDTO);
        searchView.display();
    }
}
