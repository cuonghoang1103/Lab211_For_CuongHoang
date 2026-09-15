package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the two points of the
 * edge to check.
 *
 * @author HE176322
 */
public class GraphRequestDTO {

    // Label of the start point, already checked to be a vertex.
    private int start;
    // Label of the end point, already checked to be a vertex.
    private int end;

    // Creates an empty request; main fills it through the setters.
    public GraphRequestDTO() {
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
}
