package view;

import dto.StackResponseDTO;

/**
 * VIEW: prints the result of every stack operation. It receives the data through its
 * attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class StackView {

    // The result to display, handed over by the controller.
    private StackResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(StackResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the result line of the operation: "Pushed 10.   ...", "Popped 30.   ...",
    // "Get (top) = 30 ..." or "Stack (top -> bottom): [20, 10]".
    public void display() {
        System.out.println(responseDTO.getMessage());
    }
}
