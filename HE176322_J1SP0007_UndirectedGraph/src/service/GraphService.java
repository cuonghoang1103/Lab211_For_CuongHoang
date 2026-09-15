package service;

import constants.Constants;
import dto.GraphRequestDTO;
import dto.GraphResponseDTO;
import model.Graph;
import model.GraphRepresentation;

/**
 * SERVICE and Strategy CONTEXT: builds the brief's graph and answers "is (start, end) an
 * edge?".
 *
 * @author HE176322
 */
public class GraphService {

    // The graph of the brief, seen through its Strategy interface.
    private GraphRepresentation graph;

    // Creates the service and builds the brief's graph: an adjacency matrix (the brief's
    // class Graph) with the five edges of the figure.
    public GraphService() {
        graph = new Graph(Constants.VERTICES);
        // add each edge of the figure once; addEdge fills both cells
        for (int[] edge : Constants.EDGES) {
            graph.addEdge(edge[0], edge[1]);
        }
    }

    // Checks whether the two points of the request are joined by an edge.
    public GraphResponseDTO checkEdge(GraphRequestDTO requestDTO) {
        GraphResponseDTO response = new GraphResponseDTO();
        response.setStart(requestDTO.getStart());
        response.setEnd(requestDTO.getEnd());
        response.setEdge(graph.isEdge(requestDTO.getStart(), requestDTO.getEnd()));
        return response;
    }
}
