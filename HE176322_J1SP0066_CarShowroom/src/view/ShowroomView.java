package view;

import dto.CarResponseDTO;

/**
 * VIEW: prints the result of a check. It receives the data through its attribute (the
 * ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ShowroomView {

    // The answer to print, handed over by the controller.
    private CarResponseDTO responseDTO;

    // Receives the answer the next display() will print.
    public void setResponseDTO(CarResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the answer: the one-line result "Sell Car".
    public void display() {
        System.out.println(responseDTO.getMessage());
    }
}
