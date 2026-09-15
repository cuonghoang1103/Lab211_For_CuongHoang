package controller;

import dto.ArrayRequestDTO;
import dto.SortResponseDTO;
import service.SortService;
import view.ArrayView;

/**
 * Controller: routes the three menu functions to the service and the view (no Scanner, no
 * printing, no static).
 *
 * @author HE176322
 */
public class ArrayController {

    // stores and sorts the array
    private SortService sortService;
    // prints the sorted arrays
    private ArrayView arrayView;

    // creates the controller with its service and view
    public ArrayController() {
        sortService = new SortService();
        arrayView = new ArrayView();
    }

    // option 1: keep the typed array
    public void inputArray(ArrayRequestDTO requestDTO) {
        sortService.saveArray(requestDTO);
    }

    // option 2: show the array sorted ascending
    public void sortAscending() throws Exception {
        SortResponseDTO response = sortService.getAscending();
        arrayView.setResponse(response);
        arrayView.displayAscending();
    }

    // option 3: show the array sorted descending
    public void sortDescending() throws Exception {
        SortResponseDTO response = sortService.getDescending();
        arrayView.setResponse(response);
        arrayView.displayDescending();
    }
}
