package controller;

import dto.CountRequestDTO;
import dto.CountResponseDTO;
import service.CountService;
import view.CountView;

/**
 * CONTROLLER (Facade): sends the content to the service and the counts to the view, once
 * (no Scanner, no printing, no model, no static).
 *
 * @author HE176322
 */
public class CountController {

    // Keeps and counts the content (Controller -> Service -> Repository -> Model).
    private CountService countService;

    // Prints the counts.
    private CountView countView;

    // Creates the controller together with its service and its view.
    public CountController() {
        countService = new CountService();
        countView = new CountView();
    }

    // The only workflow: the service counts, the view shows the two lines ONCE.
    public void countContent(CountRequestDTO requestDTO) {
        CountResponseDTO responseDTO = countService.countContent(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        countView.setResponseDTO(responseDTO);
        countView.display();
    }
}
