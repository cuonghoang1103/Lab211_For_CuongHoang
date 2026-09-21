package repository;

import constants.Constants;
import model.Graph;
import model.IGraphRepresentation;

/**
 * REPOSITORY: holds the data of the program - the undirected graph, stored as the
 * brief's adjacency matrix - and only simple CRUD on it: add an edge, read an edge. No
 * rule, no print.
 *
 * @author HE176322
 */
public class GraphRepository {

    // The graph, seen through its Strategy interface: only the "new" below knows the
    // concrete class (Strategy CONTEXT).
    private IGraphRepresentation graph;

    // Creates an empty graph with the brief's five vertices.
    public GraphRepository() {
        graph = new Graph(Constants.VERTICES);
    }

    // Create: joins two vertices with an edge.
    public void addEdge(int start, int end) {
        graph.addEdge(start, end);
    }

    // Read: tells whether two vertices are joined by an edge.
    public boolean isEdge(int start, int end) {
        return graph.isEdge(start, end);
    }
}
