package controller;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import service.SortService;
import view.SortView;

/**
 * Controller: takes the request from main, asks the service for the result and hands it
 * to the view (no Scanner, no printing, no static).
 *
 * @author HE176322
 */
public class SortController {

    // generates and sorts the array
    private SortService sortService;
    // prints the result
    private SortView sortView;

    // creates the controller with its service and view
    public SortController() {
        sortService = new SortService();
        sortView = new SortView();
    }

    // the only workflow: service computes, view displays
    public void sortArray(SortRequestDTO requestDTO) {
        SortResponseDTO response = sortService.sortRandomArray(requestDTO);
        sortView.setResponse(response);
        sortView.display();
    }
}
