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

    // Receives the account the next display() call greets.
    public void setAccount(AccountResponseDTO account) {
        this.account = account;
    }

    // Prints the welcome screen of the brief: the title, "Hello ", and the question "Hi ,
    // do you want change password now?
    public void display() {
        // nothing logged in: nothing to greet
        if (account == null) {
            return;
        }
        System.out.println(Message.TITLE_WELCOME);
        System.out.println(String.format(Message.HELLO, account.getUsername()));
        System.out.print(String.format(Message.ASK_CHANGE, account.getName()));
    }

    // Prints a one-line result such as "Login fail.".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
