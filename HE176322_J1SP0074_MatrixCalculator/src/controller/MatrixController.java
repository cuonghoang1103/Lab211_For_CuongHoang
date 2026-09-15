package controller;

import dto.MatrixRequestDTO;
import dto.MatrixResponseDTO;
import service.MatrixService;
import view.MatrixView;

/**
 * CONTROLLER (and Facade): receives the request from main, asks the service for the
 * result, and hands it to the view.
 *
 * @author HE176322
 */
public class MatrixController {

    // Does the calculations.
    private MatrixService matrixService;
    // Prints the result.
    private MatrixView matrixView;

    // Creates the controller with its service and view.
    public MatrixController() {
        matrixService = new MatrixService();
        matrixView = new MatrixView();
    }

    // Pre-check called by main right after the size of matrix 2 is typed, before its
    // values (like checkExistDoctor in the Guide sample).
    public void checkMatrixSize(MatrixRequestDTO requestDTO) throws Exception {
        matrixService.checkMatrixSize(requestDTO);
    }

    // The workflow of options 1-3: service computes, view displays.
    public void calculateMatrix(MatrixRequestDTO requestDTO) throws Exception {
        MatrixResponseDTO response = matrixService.calculateMatrix(requestDTO);
        matrixView.setResponse(response);
        matrixView.display();
    }
}
