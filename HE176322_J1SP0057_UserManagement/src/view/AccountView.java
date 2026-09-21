package view;

import dto.AccountResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class AccountView {

    // The answer to print, handed over by the controller.
    private AccountResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(AccountResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the answer: "Create account successfully!" or "Login successful!" (the brief's
    // screen).
    public void display() {
        System.out.println(responseDTO.getMessage());
    }
}
