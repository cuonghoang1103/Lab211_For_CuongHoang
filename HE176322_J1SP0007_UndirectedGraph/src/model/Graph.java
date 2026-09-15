package model;

import constants.Constants;

/**
 * MODEL: the brief's class Graph - an undirected graph stored as an adjacency matrix, a
 * JavaBean (private field, public no-argument constructor, getter/setter) as in MVC of
 * JSP.
 *
 * @author HE176322
 */
public class Graph implements GraphRepresentation {

    // The adjacency matrix; row/column index = vertex label - 1.
    private int[][] matrix;

    // JavaBean constructor: a graph with no vertex at all.
    public Graph() {
        this.matrix = new int[0][0];
    }

    // Creates a graph with the given number of vertices and no edge: a new int array is
    // filled with 0, which already means "no edge".
    public Graph(int vertices) {
        this.matrix = new int[vertices][vertices];
    }

    // Returns the adjacency matrix.
    public int[][] getMatrix() {
        return matrix;
    }

    // Replaces the adjacency matrix.
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    // Returns how many vertices the graph has.
    public int getVertices() {
        return matrix.length;
    }

    // Joins two vertices.
    @Override
    public void addEdge(int start, int end) {
        matrix[start - Constants.FIRST_VERTEX][end - Constants.FIRST_VERTEX] = Constants.EDGE;
        matrix[end - Constants.FIRST_VERTEX][start - Constants.FIRST_VERTEX] = Constants.EDGE;
    }

    // Answers with ONE array read - the operation an adjacency matrix is good at.
    @Override
    public boolean isEdge(int start, int end) {
        return matrix[start - Constants.FIRST_VERTEX][end - Constants.FIRST_VERTEX]
                == Constants.EDGE;
    }

    // Polymorphism: overrides Object.toString() to give the matrix row by row.
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        // one line per row of the matrix
        for (int i = 0; i < matrix.length; i++) {
            // a new row starts on a new line, except the first one
            if (i > 0) {
                text.append(Constants.NEW_LINE);
            }
            // every cell of row i, separated by a space
            for (int j = 0; j < matrix[i].length; j++) {
                // no separator before the first cell
                if (j > 0) {
                    text.append(Constants.CELL_SEPARATOR);
                }
                text.append(matrix[i][j]);
            }
        }
        return text.toString();
    }
}
