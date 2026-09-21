package controller;

import dto.ShapeRequestDTO;
import dto.ShapeResponseDTO;
import service.ShapeService;
import view.ShapeView;

/**
 * CONTROLLER (Facade): takes the request from main, lets the service calculate, and hands
 * the results to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class ShapeController {

    // Builds the shapes and computes their results (Controller -> Service -> Repository ->
    // Model).
    private ShapeService shapeService;

    // Prints the results.
    private ShapeView shapeView;

    // Creates the controller together with its service and its view.
    public ShapeController() {
        shapeService = new ShapeService();
        shapeView = new ShapeView();
    }

    // The brief's Function 2 (the only workflow): the service calculates, the view shows
    // the results ONCE, and the program ends.
    public void calculate(ShapeRequestDTO requestDTO) {
        ShapeResponseDTO responseDTO = shapeService.calculateShapes(requestDTO);

        // hand the results to the view, then render them - once for the whole flow
        shapeView.setResponseDTO(responseDTO);
        shapeView.printResult();
    }
}
