package main;

import constants.Constants;
import constants.Message;
import controller.EbankController;
import dto.LoginRequestDTO;
import java.util.Locale;
import java.util.Scanner;
import utils.CaptchaUtils;
import utils.Validation;

/**
 * MAIN: the work flow - the language menu, then the login dialog.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: one menu choice, then one login.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EbankController controller = new EbankController();
        System.out.println(Message.MENU);
        LoginRequestDTO dto = new LoginRequestDTO();
        // turn the choice into a language, or leave
        switch (inputChoice(sc)) {
            // option 1: switch the interface to Vietnamese
            case Constants.MENU_VIETNAMESE:
                dto.setLocale(new Locale(Constants.LANGUAGE_VI));
                break;
            // option 2: keep the English interface
            case Constants.MENU_ENGLISH:
                dto.setLocale(new Locale(Constants.LANGUAGE_EN));
                break;
            // option 3 (and the unreachable rest): exit without logging in
            default:
                return;
        }
        controller.setLocate(dto);
        login(sc, controller);
    }

    // Asks for a menu choice until it is a number from 1 to 3.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // The brief's Function 6: account number, then password, then captcha, each asked
    // again until it is right.
    private static void login(Scanner sc, EbankController controller) {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setAccountNumber(inputAccountNumber(sc, controller));
        dto.setPassword(inputPassword(sc, controller));
        dto.setCaptchaGenerate(CaptchaUtils.generateCaptcha());
        controller.showCaptcha(dto);
        // keep asking until the typed characters are in the captcha
        while (true) {
            controller.showPrompt(Message.KEY_CAPTCHA_PROMPT);
            dto.setCaptchaInput(sc.nextLine());
            // a wrong captcha prints the error and loops again
            try {
                controller.login(dto);
                return;
            } catch (Exception e) {
                // "Captcha incorrect" / "Captcha sai"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the account number until it is 10 digits.
    private static String inputAccountNumber(Scanner sc, EbankController controller) {
        LoginRequestDTO dto = new LoginRequestDTO();
        // keep asking until the account number is valid
        while (true) {
            controller.showPrompt(Message.KEY_ACCOUNT_PROMPT);
            dto.setAccountNumber(sc.nextLine());
            // a wrong number prints the translated error and loops again
            try {
                controller.checkAccountNumber(dto);
                return dto.getAccountNumber();
            } catch (Exception e) {
                // the error is already in the chosen language
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the password until it follows the rule.
    private static String inputPassword(Scanner sc, EbankController controller) {
        LoginRequestDTO dto = new LoginRequestDTO();
        // keep asking until the password is valid
        while (true) {
            controller.showPrompt(Message.KEY_PASSWORD_PROMPT);
            dto.setPassword(sc.nextLine());
            // a wrong password prints the translated error and loops again
            try {
                controller.checkPassword(dto);
                return dto.getPassword();
            } catch (Exception e) {
                // the error is already in the chosen language
                System.out.println(e.getMessage());
            }
        }
    }
}
