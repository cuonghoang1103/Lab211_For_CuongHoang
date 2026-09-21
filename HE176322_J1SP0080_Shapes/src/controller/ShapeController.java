package controller;

import dto.ReportResponseDTO;
import service.ShapeService;
import view.ShapeView;

/**
 * CONTROLLER (Facade): asks the service for the report and hands it to the view once. No
 * Scanner, no print, no model.
 *
 * @author HE176322
 */
public class ShapeController {

    // Builds the shapes and computes the rows (Controller -> Service -> Repository ->
    // Model).
    private ShapeService shapeService;

    // Prints the report.
    private ShapeView shapeView;

    // Creates the controller together with its service and its view.
    public ShapeController() {
        shapeService = new ShapeService();
        shapeView = new ShapeView();
    }

    // The only workflow (the brief's Function 3): the service computes the report, the
    // view shows it ONCE.
    public void displayShapes() {
        ReportResponseDTO responseDTO = shapeService.getShapeReport();

        // hand the report to the view, then render it - once for the whole flow
        shapeView.setResponseDTO(responseDTO);
        shapeView.display();
    }
}
