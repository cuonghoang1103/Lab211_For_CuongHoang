package view;

import dto.LoginResponseDTO;

/**
 * VIEW: prints the answer of the login. It receives the data through its attribute (the
 * ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class EbankView {

    // The answer to print, handed over by the controller.
    private LoginResponseDTO responseDTO;

    // Receives the answer the next display() will print.
    public void setResponseDTO(LoginResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the answer: the success line, or the error of a check that failed.
    public void display() {
        System.out.println(responseDTO.getMessage());
    }
}
