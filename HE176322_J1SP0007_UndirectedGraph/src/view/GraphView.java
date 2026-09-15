package view;

import constants.Message;
import dto.GraphResponseDTO;

/**
 * VIEW: prints the answer "This is an edge" / "This is not an edge".
 *
 * @author HE176322
 */
public class GraphView {

    // The answer to display, handed over by the controller.
    private GraphResponseDTO response;

    // Receives the answer the next display() call will print.
    public void setResponse(GraphResponseDTO response) {
        this.response = response;
    }

    // Prints the last line of the brief's screen.
    public void display() {
        // the two points are joined by an edge
        if (response.isEdge()) {
            System.out.println(Message.IS_EDGE);
        } else {
            // no edge between the two points
            System.out.println(Message.NOT_EDGE);
        }
    }
}
