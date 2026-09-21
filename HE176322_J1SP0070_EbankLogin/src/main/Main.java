package main;

import constants.Constants;
import constants.Message;
import controller.EbankController;
import dto.LoginRequestDTO;
import java.util.Locale;
import java.util.Scanner;
import utils.CaptchaUtils;
import utils.LanguageUtils;
import utils.Validation;

/**
 * MAIN: the work flow. Every keyboard read, every validation and the captcha happen here;
 * each menu option then calls the controller exactly once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: the menu once, then the flow of the chosen option.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EbankController controller = new EbankController();
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        int choice = 0;

        // the menu is always English: no language has been chosen yet
        System.out.println(Message.MENU);
        choice = inputChoice(sc);

        // one option = one flow; options 1 and 2 differ only in the language
        switch (choice) {
            // option 1: switch the interface to Vietnamese, then log in
            case Constants.MENU_VIETNAMESE:
                requestDTO.setLocale(new Locale(Constants.LANGUAGE_VI));
                inputLogin(sc, requestDTO);
                controller.login(requestDTO);
                break;

            // option 2: keep the English interface, then log in
            case Constants.MENU_ENGLISH:
                requestDTO.setLocale(new Locale(Constants.LANGUAGE_EN));
                inputLogin(sc, requestDTO);
                controller.login(requestDTO);
                break;

            // option 3: leave without printing anything
            default:
                break;
        }
    }

    // Asks for the menu choice until it is a number from 1 to 3.
    private static int inputChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // The brief's Function 6: account number, password, then captcha - each asked again at
    // once until it is valid - all stored in the request.
    private static void inputLogin(Scanner sc, LoginRequestDTO requestDTO) {
        requestDTO.setAccountNumber(inputAccountNumber(sc, requestDTO.getLocale()));
        requestDTO.setPassword(inputPassword(sc, requestDTO.getLocale()));
        requestDTO.setCaptchaGenerate(CaptchaUtils.generateCaptcha());
        requestDTO.setCaptchaInput(inputCaptcha(sc, requestDTO));
    }

    // Asks for the account number until it is exactly 10 digits.
    private static String inputAccountNumber(Scanner sc, Locale locale) {
        String line = "";

        // keep asking until the line is a valid account number
        while (true) {
            System.out.print(LanguageUtils.getText(locale, Message.KEY_ACCOUNT_PROMPT));
            line = sc.nextLine();

            // valid: hand it back to the flow
            if (Validation.isMatch(line, Constants.ACCOUNT_REGEX)) {
                return line;
            }

            // invalid: the error in the chosen language, then ask again
            System.out.println(LanguageUtils.getText(locale, Message.KEY_ACCOUNT_ERROR));
        }
    }

    // Asks for the password until it is 8-31 letters and digits with both kinds.
    private static String inputPassword(Scanner sc, Locale locale) {
        String line = "";

        // keep asking until the line is a valid password
        while (true) {
            System.out.print(LanguageUtils.getText(locale, Message.KEY_PASSWORD_PROMPT));
            line = sc.nextLine();

            // valid: hand it back to the flow
            if (Validation.isMatch(line, Constants.PASSWORD_REGEX)) {
                return line;
            }

            // invalid: the error in the chosen language, then ask again
            System.out.println(LanguageUtils.getText(locale, Message.KEY_PASSWORD_ERROR));
        }
    }

    // Shows the captcha once, then asks for its characters until they are part of it.
    private static String inputCaptcha(Scanner sc, LoginRequestDTO requestDTO) {
        Locale locale = requestDTO.getLocale();
        String captchaGenerate = requestDTO.getCaptchaGenerate();
        String line = "";

        // show the label and the captcha once (two prints: no string concatenation)
        System.out.print(LanguageUtils.getText(locale, Message.KEY_CAPTCHA_LABEL));
        System.out.println(captchaGenerate);

        // keep asking the SAME captcha until the typed characters are in it
        while (true) {
            System.out.print(LanguageUtils.getText(locale, Message.KEY_CAPTCHA_PROMPT));
            line = sc.nextLine();

            // right: at least one character, and all of them inside the captcha
            if (Validation.isCaptchaMatch(line, captchaGenerate)) {
                return line;
            }

            // wrong: the error in the chosen language, then ask again
            System.out.println(LanguageUtils.getText(locale, Message.KEY_CAPTCHA_ERROR));
        }
    }
}
