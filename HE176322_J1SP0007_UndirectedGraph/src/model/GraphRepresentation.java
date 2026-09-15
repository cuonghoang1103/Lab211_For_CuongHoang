package model;

/**
 * STRATEGY (design pattern): the contract of any way to store an undirected graph - the
 * two questions the program asks of a graph, nothing more.
 *
 * @author HE176322
 */
public interface GraphRepresentation {

    // Joins two vertices with an undirected edge.
    void addEdge(int start, int end);

    // Tells whether two vertices are joined by an edge.
    boolean isEdge(int start, int end);
}
