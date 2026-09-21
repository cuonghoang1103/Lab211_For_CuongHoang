package model;

import constants.Constants;

/**
 * MODEL: the brief's class Graph - an undirected graph stored as an adjacency matrix, a
 * JavaBean (private field, public no-argument constructor, getter/setter) as in MVC of
 * JSP.
 *
 * @author HE176322
 */
public class Graph implements IGraphRepresentation {

    // The adjacency matrix; row/column index = vertex label - 1.
    private int[][] matrixArray;

    // JavaBean constructor: a graph with no vertex at all.
    public Graph() {
        this.matrixArray = new int[0][0];
    }

    // Creates a graph with the given number of vertices and no edge: a new int array is
    // filled with 0, which already means "no edge".
    public Graph(int vertices) {
        this.matrixArray = new int[vertices][vertices];
    }

    // Returns the adjacency matrix.
    public int[][] getMatrixArray() {
        return matrixArray;
    }

    // Replaces the adjacency matrix.
    public void setMatrixArray(int[][] matrixArray) {
        this.matrixArray = matrixArray;
    }

    // Returns how many vertices the graph has.
    public int getVertices() {
        return matrixArray.length;
    }

    // Joins two vertices: BOTH cells, because the graph is undirected.
    @Override
    public void addEdge(int start, int end) {
        int row = start - Constants.FIRST_VERTEX;
        int column = end - Constants.FIRST_VERTEX;

        matrixArray[row][column] = Constants.EDGE;
        matrixArray[column][row] = Constants.EDGE;
    }

    // Answers with ONE array read - the operation an adjacency matrix is good at.
    @Override
    public boolean isEdge(int start, int end) {
        int row = start - Constants.FIRST_VERTEX;
        int column = end - Constants.FIRST_VERTEX;

        return matrixArray[row][column] == Constants.EDGE;
    }

    // Polymorphism: overrides Object.toString() to give the matrix row by row.
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();

        // one line per row of the matrix
        for (int i = 0; i < matrixArray.length; i++) {
            // a new row starts on a new line, except the first one
            if (i > 0) {
                text.append(Constants.NEW_LINE);
            }

            // every cell of row i, separated by a space
            for (int j = 0; j < matrixArray[i].length; j++) {
                // no separator before the first cell
                if (j > 0) {
                    text.append(Constants.CELL_SEPARATOR);
                }

                text.append(matrixArray[i][j]);
            }
        }

        return text.toString();
    }
}
