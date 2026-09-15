package controller;

import dto.ShapeResponseDTO;
import java.util.ArrayList;
import service.ShapeService;
import view.ShapeView;

/**
 * CONTROLLER: asks the service for the report rows and hands them to the view.
 *
 * @author HE176322
 */
public class ShapeController {

    // Builds the shapes and computes the rows.
    private ShapeService shapeService;
    // Prints the report.
    private ShapeView shapeView;

    // Creates the controller with its service and view.
    public ShapeController() {
        shapeService = new ShapeService();
        shapeView = new ShapeView();
    }

    // The only workflow: the service computes, the view displays.
    public void displayShapes() {
        ArrayList<ShapeResponseDTO> rows = shapeService.getShapeReport();
        shapeView.setShapes(rows);
        shapeView.display();
    }
}
