package controller;

import dto.ShapeRequestDTO;
import dto.ShapeResponseDTO;
import java.util.ArrayList;
import service.ShapeService;
import view.ShapeView;

/**
 * CONTROLLER: takes the request from main, asks the service for the results, and hands
 * them to the view.
 *
 * @author HE176322
 */
public class ShapeController {

    // Builds the shapes and computes their results.
    private ShapeService shapeService;
    // Prints the results.
    private ShapeView shapeView;

    // Creates the controller with its service and view.
    public ShapeController() {
        shapeService = new ShapeService();
        shapeView = new ShapeView();
    }

    // The brief's Function 2: calculate, display, and the program ends.
    public void calculate(ShapeRequestDTO requestDTO) {
        ArrayList<ShapeResponseDTO> results = shapeService.calculateShapes(requestDTO);
        shapeView.setShapes(results);
        shapeView.printResult();
    }
}
