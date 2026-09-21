package view;

import constants.Message;
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

    // Prints what the controller set: a one-line result, or the welcome screen of the brief
    // (the title, "Hello <username>" and the change-password question, left open for the
    // answer main reads next).
    public void display() {
        // a one-line result: "Account [NghiaNV] has been added with id 1." or "Password has
        // been changed."
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the welcome screen of a successful login
        if (responseDTO.getUsername() != null) {
            System.out.println(Message.TITLE_WELCOME);
            System.out.println(String.format(Message.HELLO, responseDTO.getUsername()));
            System.out.print(String.format(Message.ASK_CHANGE, responseDTO.getName()));
        }
    }
}
