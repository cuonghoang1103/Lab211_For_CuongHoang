package controller;

import dto.CountRequestDTO;
import dto.CountResponseDTO;
import service.CountService;
import view.CountView;

/**
 * Controller: sends the content to the service and the counts to the view (no Scanner, no
 * printing, no static).
 *
 * @author HE176322
 */
public class CountController {

    // counts words and characters
    private CountService countService;
    // prints the counts
    private CountView countView;

    // creates the controller with its service and view
    public CountController() {
        countService = new CountService();
        countView = new CountView();
    }

    // the only workflow: count, then display
    public void countContent(CountRequestDTO requestDTO) {
        CountResponseDTO response = countService.countContent(requestDTO);
        countView.setResponse(response);
        countView.display();
    }
}
