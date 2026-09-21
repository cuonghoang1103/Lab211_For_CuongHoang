package controller;

import dto.PathRequestDTO;
import dto.PathResponseDTO;
import service.PathService;
import view.PathView;

/**
 * CONTROLLER (and FACADE for main): takes the request from main, asks the service for the
 * result, and hands that result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class PathController {

    // Analyses the path (Controller -> Service -> Repository -> Model).
    private PathService pathService;

    // Prints the result.
    private PathView pathView;

    // Creates the controller with its service and view.
    public PathController() {
        pathService = new PathService();
        pathView = new PathView();
    }

    // Function 2 of the brief (Perform function): service analyses, view displays.
    public void analyzePath(PathRequestDTO requestDTO) {
        PathResponseDTO responseDTO = pathService.analyzePath(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        pathView.setResponseDTO(responseDTO);
        pathView.display();
    }
}
