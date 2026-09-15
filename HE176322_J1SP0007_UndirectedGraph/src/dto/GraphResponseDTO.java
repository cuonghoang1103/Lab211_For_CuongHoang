package dto;

/**
 * DTO carrying the answer FROM the controller OUT TO the view: which two points were
 * asked about and whether they form an edge.
 *
 * @author HE176322
 */
public class GraphResponseDTO {

    // Label of the start point that was checked.
    private int start;
    // Label of the end point that was checked.
    private int end;
    // True when the two points are joined by an edge.
    private boolean edge;

    // JavaBean constructor: an empty answer, filled through the setters.
    public GraphResponseDTO() {
    }

    // Returns the start point.
    public int getStart() {
        return start;
    }

    // Sets the start point.
    public void setStart(int start) {
        this.start = start;
    }

    // Returns the end point.
    public int getEnd() {
        return end;
    }

    // Sets the end point.
    public void setEnd(int end) {
        this.end = end;
    }

    // Tells whether the two points form an edge.
    public boolean isEdge() {
        return edge;
    }

    // Sets whether the two points form an edge.
    public void setEdge(boolean edge) {
        this.edge = edge;
    }
}
