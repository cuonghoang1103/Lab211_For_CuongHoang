package view;

import dto.ShapeResponseDTO;

/**
 * VIEW: prints the result of every shape. It receives the data through its attribute (the
 * ResponseDTO), never through the parameters of printResult().
 *
 * @author HE176322
 */
public class ShapeView {

    // The results to print, handed over by the controller.
    private ShapeResponseDTO responseDTO;

    // Receives the results the next printResult() call will print.
    public void setResponseDTO(ShapeResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // The brief's Function 3, "public void printResult()": displays the shape information -
    // the render of this view (the display() of the other labs), no parameter.
    public void printResult() {
        // one block per shape: title, properties, area, perimeter
        for (String result : responseDTO.getResultList()) {
            System.out.println(result);
        }
    }
}
