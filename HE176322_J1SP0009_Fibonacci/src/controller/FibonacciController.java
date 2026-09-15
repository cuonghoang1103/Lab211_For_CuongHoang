package controller;

import constants.Constants;
import dto.FibonacciResponseDTO;
import service.FibonacciService;
import view.FibonacciView;

/**
 * Controller: asks the service for the sequence and hands it to the view (no Scanner, no
 * printing, no static).
 *
 * @author HE176322
 */
public class FibonacciController {

    // computes the sequence
    private FibonacciService fibonacciService;
    // prints the sequence
    private FibonacciView fibonacciView;

    // creates the controller with its service and view
    public FibonacciController() {
        fibonacciService = new FibonacciService();
        fibonacciView = new FibonacciView();
    }

    // the only workflow: compute the 45 numbers, then display them
    public void displaySequence() {
        FibonacciResponseDTO response
                = fibonacciService.generateSequence(Constants.SEQUENCE_LENGTH);
        fibonacciView.setResponse(response);
        fibonacciView.display();
    }
}
