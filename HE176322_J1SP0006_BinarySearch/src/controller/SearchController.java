package controller;

import dto.SearchRequestDTO;
import dto.SearchResponseDTO;
import service.BinarySearchStrategy;
import service.SearchService;
import view.SearchView;

/**
 * CONTROLLER: takes the request from main, asks the service for the result, and hands
 * that result to the view.
 *
 * @author HE176322
 */
public class SearchController {

    // Generates, sorts and searches; configured with binary search.
    private SearchService searchService;
    // Prints the result.
    private SearchView searchView;

    // Creates the controller: the service gets binary search as its strategy.
    public SearchController() {
        searchService = new SearchService(new BinarySearchStrategy());
        searchView = new SearchView();
    }

    // The only workflow of the program: service computes, view displays.
    public void searchArray(SearchRequestDTO requestDTO) {
        SearchResponseDTO response = searchService.searchRandomArray(requestDTO);
        searchView.setResponse(response);
        searchView.display();
    }
}
