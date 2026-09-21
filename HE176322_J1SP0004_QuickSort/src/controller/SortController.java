package controller;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import service.QuickSortStrategy;
import service.SortService;
import view.SortView;

/**
 * CONTROLLER (Facade): takes the request from main, lets the service do the work, and hands
 * the result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class SortController {

    // Generates, keeps and sorts the array with quick sort (Controller -> Service ->
    // Repository -> Model).
    private SortService sortService;

    // Prints the result.
    private SortView sortView;

    // Creates the controller: the service gets quick sort as its strategy.
    public SortController() {
        sortService = new SortService(new QuickSortStrategy());
        sortView = new SortView();
    }

    // The only workflow (the brief's Function 2): the service sorts, the view shows the
    // result ONCE.
    public void sortArray(SortRequestDTO requestDTO) {
        SortResponseDTO responseDTO = sortService.sortRandomArray(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        sortView.setResponseDTO(responseDTO);
        sortView.display();
    }
}
