package controller;

import dto.GraphRequestDTO;
import dto.GraphResponseDTO;
import service.GraphService;
import view.GraphView;

/**
 * CONTROLLER (Facade): takes the two points from main, asks the service for the answer,
 * and hands that answer to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class GraphController {

    // Builds the graph and answers the edge question.
    private GraphService graphService;

    // Prints the answer.
    private GraphView graphView;

    // Creates the controller with its service and view.
    public GraphController() {
        graphService = new GraphService();
        graphView = new GraphView();
    }

    // The only workflow of the program: the service answers, the view shows it ONCE.
    public void checkEdge(GraphRequestDTO requestDTO) {
        GraphResponseDTO responseDTO = graphService.checkEdge(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        graphView.setResponseDTO(responseDTO);
        graphView.display();
    }
}
