package constants;

/**
 * Numbers the program logic depends on, including the graph of the brief.
 *
 * @author HE176322
 */
public final class Constants {

    // Number of vertices of the brief's graph (labelled 1 to 5).
    public static final int VERTICES = 5;

    // Label of the first vertex: the brief counts vertices from 1.
    public static final int FIRST_VERTEX = 1;

    // Cell value meaning "there is an edge" (the brief's matrix figure).
    public static final int EDGE = 1;

    // Cell value meaning "there is no edge"; Java fills int arrays with it.
    public static final int NO_EDGE = 0;

    // The edges of the brief's figure: 1-4, 2-4, 2-5, 3-5, 4-5.
    public static final int[][] EDGES = {{1, 4}, {2, 4}, {2, 5}, {3, 5}, {4, 5}};

    // Separator between two cells when the matrix is turned into text.
    public static final String CELL_SEPARATOR = " ";

    // Line break between two rows of the matrix text.
    public static final String NEW_LINE = "\n";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
