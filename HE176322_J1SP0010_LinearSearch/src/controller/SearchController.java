package controller;

import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import service.SearchService;
import view.SearchView;

/**
 * Controller: takes the request from main, asks the service for the result and hands it
 * to the view (no Scanner, no printing, no static).
 *
 * @author HE176322
 */
public class SearchController {

    // generates and searches the array
    private SearchService searchService;
    // prints the result
    private SearchView searchView;

    // creates the controller with its service and view
    public SearchController() {
        searchService = new SearchService();
        searchView = new SearchView();
    }

    // the only workflow: service computes, view displays
    public void searchArray(SearchRequestDTO requestDTO) {
        SearchResponseDTO response = searchService.searchRandomArray(requestDTO);
        searchView.setResponse(response);
        searchView.display();
    }
}
