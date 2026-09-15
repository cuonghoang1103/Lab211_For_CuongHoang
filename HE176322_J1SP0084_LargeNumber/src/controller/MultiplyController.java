package controller;

import dto.MultiplyRequestDTO;
import dto.MultiplyResponseDTO;
import service.LargeNumberService;
import view.MultiplyView;

/**
 * Controller: sends the two numbers to the service and the product to the view (no
 * Scanner, no printing, no static).
 *
 * @author HE176322
 */
public class MultiplyController {

    // multiplies the numbers
    private LargeNumberService largeNumberService;
    // prints the product
    private MultiplyView multiplyView;

    // creates the controller with its service and view
    public MultiplyController() {
        largeNumberService = new LargeNumberService();
        multiplyView = new MultiplyView();
    }

    // the only workflow: multiply, then display
    public void multiply(MultiplyRequestDTO requestDTO) {
        MultiplyResponseDTO response = largeNumberService.multiply(requestDTO);
        multiplyView.setResponse(response);
        multiplyView.display();
    }
}
