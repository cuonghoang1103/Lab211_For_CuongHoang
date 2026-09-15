package controller;

import dto.GraphRequestDTO;
import dto.GraphResponseDTO;
import service.GraphService;
import view.GraphView;

/**
 * CONTROLLER: takes the two points from main, asks the service for the answer, and hands
 * that answer to the view.
 *
 * @author HE176322
 */
public class GraphController {

    // Holds the graph and answers the edge question.
    private GraphService graphService;
    // Prints the answer.
    private GraphView graphView;

    // Creates the controller with its service and view.
    public GraphController() {
        graphService = new GraphService();
        graphView = new GraphView();
    }

    // The only workflow of the program: service answers, view displays.
    public void checkEdge(GraphRequestDTO requestDTO) {
        GraphResponseDTO response = graphService.checkEdge(requestDTO);
        graphView.setResponse(response);
        graphView.display();
    }
}
