package controller;

import dto.MatrixRequestDTO;
import dto.MatrixResponseDTO;
import service.MatrixService;
import view.MatrixView;

/**
 * CONTROLLER (Facade): receives the request from main, asks the service for the result,
 * and hands it to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class MatrixController {

    // Does the calculations (Controller -> Service -> Repository -> Model).
    private MatrixService matrixService;

    // Prints the result.
    private MatrixView matrixView;

    // Creates the controller together with its service and its view.
    public MatrixController() {
        matrixService = new MatrixService();
        matrixView = new MatrixView();
    }

    // The workflow of options 1-3 (the brief's Function 2): the service computes, the
    // view shows the result ONCE.
    public void calculateMatrix(MatrixRequestDTO requestDTO) {
        MatrixResponseDTO responseDTO = matrixService.calculateMatrix(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        matrixView.setResponseDTO(responseDTO);
        matrixView.display();
    }
}
