package controller;

import dto.SortRequestDTO;
import dto.SortResponseDTO;
import service.SortService;
import view.SortView;

/**
 * CONTROLLER (Facade): routes the three menu functions to the service and the view (no
 * Scanner, no printing, no model, no static).
 *
 * @author HE176322
 */
public class SortController {

    // Stores and sorts the array (Controller -> Service -> Repository -> Model).
    private SortService sortService;

    // Prints the sorted arrays.
    private SortView sortView;

    // Creates the controller together with its service and its view.
    public SortController() {
        sortService = new SortService();
        sortView = new SortView();
    }

    // Option 1: keeps the typed array; the brief shows nothing after it, so nothing is
    // rendered.
    public void inputArray(SortRequestDTO requestDTO) {
        sortService.saveArray(requestDTO);
    }

    // Option 2: the array sorted ascending, shown ONCE.
    public void sortAscending() throws Exception {
        SortResponseDTO responseDTO = sortService.getAscending();

        // hand the result to the view, then render it - once for the whole flow
        sortView.setResponseDTO(responseDTO);
        sortView.display();
    }

    // Option 3: the array sorted descending, shown ONCE.
    public void sortDescending() throws Exception {
        SortResponseDTO responseDTO = sortService.getDescending();

        // hand the result to the view, then render it - once for the whole flow
        sortView.setResponseDTO(responseDTO);
        sortView.display();
    }
}
