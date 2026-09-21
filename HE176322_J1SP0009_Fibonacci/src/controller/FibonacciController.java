package controller;

import dto.FibonacciRequestDTO;
import dto.FibonacciResponseDTO;
import service.FibonacciService;
import view.FibonacciView;

/**
 * CONTROLLER (Facade): takes the request from main, lets the service compute the
 * sequence, and hands the result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class FibonacciController {

    // Computes the sequence and keeps it (Controller -> Service -> Repository -> Model).
    private FibonacciService fibonacciService;

    // Prints the sequence.
    private FibonacciView fibonacciView;

    // Creates the controller together with its service and its view.
    public FibonacciController() {
        fibonacciService = new FibonacciService();
        fibonacciView = new FibonacciView();
    }

    // The only workflow: the service computes the numbers, the view shows them ONCE.
    public void displaySequence(FibonacciRequestDTO requestDTO) {
        FibonacciResponseDTO responseDTO = fibonacciService.generateSequence(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        fibonacciView.setResponseDTO(responseDTO);
        fibonacciView.display();
    }
}
