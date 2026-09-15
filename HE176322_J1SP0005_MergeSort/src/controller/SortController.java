package controller;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import service.MergeSortStrategy;
import service.SortService;
import view.SortView;

/**
 * CONTROLLER: takes the request from main, asks the service for the result, and hands
 * that result to the view.
 *
 * @author HE176322
 */
public class SortController {

    // Generates and sorts; configured with the merge sort strategy.
    private SortService sortService;
    // Prints the result.
    private SortView sortView;

    // Creates the controller: the service gets merge sort as its strategy.
    public SortController() {
        sortService = new SortService(new MergeSortStrategy());
        sortView = new SortView();
    }

    // The only workflow of the program: service computes, view displays.
    public void sortArray(SortRequestDTO requestDTO) {
        SortResponseDTO response = sortService.sortRandomArray(requestDTO);
        sortView.setResponse(response);
        sortView.display();
    }
}
