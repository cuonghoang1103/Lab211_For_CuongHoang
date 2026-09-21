package controller;

import dto.MultiplyRequestDTO;
import dto.MultiplyResponseDTO;
import service.LargeNumberService;
import view.MultiplyView;

/**
 * Controller: sends the two numbers to the service and the product to the view (no
 * Scanner, no printing, no static, no model).
 *
 * @author HE176322
 */
public class MultiplyController {

    // multiplies the numbers (Controller -> Service -> Repository -> Model)
    private LargeNumberService largeNumberService;

    // prints the product
    private MultiplyView multiplyView;

    // creates the controller with its service and view
    public MultiplyController() {
        largeNumberService = new LargeNumberService();
        multiplyView = new MultiplyView();
    }

    // the only workflow: multiply, then display ONCE
    public void multiply(MultiplyRequestDTO requestDTO) {
        MultiplyResponseDTO responseDTO = largeNumberService.multiply(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        multiplyView.setResponseDTO(responseDTO);
        multiplyView.display();
    }
}
