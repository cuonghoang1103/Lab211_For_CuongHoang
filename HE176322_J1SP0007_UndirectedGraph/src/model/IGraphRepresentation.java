package model;

/**
 * STRATEGY (design pattern): the contract of any way to store an undirected graph - the
 * two questions the program asks of a graph, nothing more. The name starts with "I"
 * because it is an interface (checklist 1.3).
 *
 * @author HE176322
 */
public interface IGraphRepresentation {

    // Joins two vertices with an undirected edge.
    void addEdge(int start, int end);

    // Tells whether two vertices are joined by an edge.
    boolean isEdge(int start, int end);
}
