package controller;

import dto.PathRequestDTO;
import dto.PathResponseDTO;
import service.PathService;
import view.PathView;

/**
 * CONTROLLER (and FACADE for main): takes the request from main, asks the service for the
 * result, and hands that result to the view.
 *
 * @author HE176322
 */
public class PathController {

    // Analyses the path.
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
        PathResponseDTO response = pathService.analyzePath(requestDTO);
        pathView.setResponse(response);
        pathView.display();
    }
}
