package view;

import constants.Message;
import dto.AccountResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class AccountView {

    // The account that has just logged in, handed over by the controller.
    private AccountResponseDTO account;

    // Receives the account the next display() call is about.
    public void setAccount(AccountResponseDTO account) {
        this.account = account;
    }

    // Prints the result of a login: "Login successful!" (the brief's screen) when an
    // account was handed over.
    public void display() {
        // no account: nothing logged in, nothing to show
        if (account == null) {
            return;
        }
        System.out.println(Message.LOGIN_SUCCESS);
    }

    // Prints a one-line result such as "Create account successfully!".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
