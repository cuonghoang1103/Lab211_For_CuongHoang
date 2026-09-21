package view;

import dto.GraphResponseDTO;

/**
 * VIEW: prints the answer "This is  an edge" / "This is not an edge". It receives the
 * data through its attribute (the ResponseDTO), never through the parameters of
 * display().
 *
 * @author HE176322
 */
public class GraphView {

    // The answer to print, handed over by the controller.
    private GraphResponseDTO responseDTO;

    // Receives the answer the next display() will print.
    public void setResponseDTO(GraphResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the last line of the brief's screen.
    public void display() {
        System.out.println(responseDTO.getMessage());
    }
}
