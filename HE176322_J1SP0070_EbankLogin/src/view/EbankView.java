package view;

/**
 * VIEW: prints the texts of the login screen, already translated.
 *
 * @author HE176322
 */
public class EbankView {

    // Prints a prompt and stays on the same line, so the user types after it.
    public void showPrompt(String prompt) {
        System.out.print(prompt);
    }

    // Prints a whole line, such as the captcha or "Login successfully".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
