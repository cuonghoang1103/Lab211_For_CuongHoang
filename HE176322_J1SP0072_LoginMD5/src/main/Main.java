package main;

import constants.Constants;
import constants.Message;
import controller.AccountController;
import dto.AccountRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountController controller = new AccountController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add an account
                    case Constants.MENU_ADD:
                        addAccount(sc, controller);
                        break;
                    // option 2: login
                    case Constants.MENU_LOGIN:
                        login(sc, controller);
                        break;
                    // option 3: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 1..3
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the repository
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 3.
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

    // Option 1: reads the seven values of the brief's Add User screen, then calls the
    // controller once.
    private static void addAccount(Scanner sc, AccountController controller)
            throws Exception {
        System.out.println(Message.TITLE_ADD);
        AccountRequestDTO dto = new AccountRequestDTO();
        System.out.print(Message.INPUT_ADD_ACCOUNT);
        dto.setUsername(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_ADD_PASSWORD);
        dto.setPassword(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_NAME);
        dto.setName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_PHONE);
        dto.setPhone(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_EMAIL);
        dto.setEmail(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_ADDRESS);
        dto.setAddress(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_DOB);
        dto.setDob(Validation.getText(sc.nextLine()));
        controller.addAccount(dto);
    }

    // Option 2: reads username and password and calls the controller's login once.
    private static void login(Scanner sc, AccountController controller) throws Exception {
        System.out.println(Message.TITLE_LOGIN);
        AccountRequestDTO dto = new AccountRequestDTO();
        System.out.print(Message.INPUT_LOGIN_ACCOUNT);
        dto.setUsername(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_LOGIN_PASSWORD);
        dto.setPassword(Validation.getText(sc.nextLine()));
        // "Login fail." was printed: back to the menu
        if (!controller.login(dto)) {
            return;
        }
        // any answer other than Y/y means "not now"
        if (Validation.isYes(sc.nextLine())) {
            inputNewPassword(sc, dto);
            controller.changePassword(dto);
        }
    }

    // Reads the three passwords of the change-password screen into the request that
    // already carries the username.
    private static void inputNewPassword(Scanner sc, AccountRequestDTO dto) {
        System.out.print(Message.INPUT_OLD_PASSWORD);
        dto.setOldPassword(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_NEW_PASSWORD);
        dto.setNewPassword(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_RENEW_PASSWORD);
        dto.setRenewPassword(Validation.getText(sc.nextLine()));
    }
}
