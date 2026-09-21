package main;

import constants.Constants;
import constants.Message;
import controller.AccountController;
import dto.AccountRequestDTO;
import java.util.Scanner;
import utils.MD5Utils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read,
 * every validation and the MD5 hashing of every password happen here (checklist 1.1); each
 * flow then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountController controller = new AccountController();
        AccountRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a broken rule or a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per flow
                switch (choice) {
                    // option 1: the seven values of the Add User screen, then add the account
                    case Constants.MENU_ADD:
                        requestDTO = inputAccount(sc);
                        controller.addAccount(requestDTO);
                        break;

                    // option 2: Account and Password, then log in; a wrong pair throws
                    // "Login fail." and the welcome question is never asked
                    case Constants.MENU_LOGIN:
                        requestDTO = inputLogin(sc);
                        controller.login(requestDTO);

                        // the answer to "Y/N:" is a small menu of its own: one answer = one
                        // flow, like the main menu
                        switch (inputAnswer(sc)) {
                            // "Y": the change-password screen, then change the password
                            case Constants.YES:
                                inputNewPassword(sc, requestDTO);
                                controller.changePassword(requestDTO);
                                break;

                            // "N" or anything else: not now, back to the main menu
                            default:
                                break;
                        }
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
                // the message was written in Message and thrown by Validation or the
                // repository
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 3.
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

    // Shows a prompt and returns the line typed, without surrounding spaces.
    private static String inputText(Scanner sc, String prompt) {
        System.out.print(prompt);
        return Validation.getText(sc.nextLine());
    }

    // Option 1: the Add User screen asks its seven values one after the other; then the
    // brief's rules are checked in the brief's order and the first broken one is thrown.
    // The password is hashed here: only its MD5 digest goes into the request.
    private static AccountRequestDTO inputAccount(Scanner sc) throws Exception {
        AccountRequestDTO requestDTO = new AccountRequestDTO();
        String username = "";
        String password = "";
        String name = "";
        String phone = "";
        String email = "";
        String address = "";
        String dob = "";

        // the title, then the seven questions of the brief's screen, with no check between
        System.out.println(Message.TITLE_ADD);
        username = inputText(sc, Message.INPUT_ADD_ACCOUNT);
        password = inputText(sc, Message.INPUT_ADD_PASSWORD);
        name = inputText(sc, Message.INPUT_NAME);
        phone = inputText(sc, Message.INPUT_PHONE);
        email = inputText(sc, Message.INPUT_EMAIL);
        address = inputText(sc, Message.INPUT_ADDRESS);
        dob = inputText(sc, Message.INPUT_DOB);

        // the brief's checks, in the brief's order; the first broken rule stops the option
        requestDTO.setUsername(Validation.getRequired(username, Message.USERNAME_EMPTY));
        password = Validation.getRequired(password, Message.PASSWORD_EMPTY);
        requestDTO.setName(Validation.getRequired(name, Message.NAME_EMPTY));
        requestDTO.setPhone(Validation.getPhone(phone));
        requestDTO.setEmail(Validation.getEmail(email));
        requestDTO.setAddress(address);
        requestDTO.setDob(Validation.getDob(dob));

        // the brief: "Password use the MD5 encryption function" - done here, in main
        requestDTO.setPassword(MD5Utils.hash(password));
        return requestDTO;
    }

    // Option 2: Account and Password of the Login screen. The password is hashed here: the
    // controller only ever sees its MD5 digest.
    private static AccountRequestDTO inputLogin(Scanner sc) {
        AccountRequestDTO requestDTO = new AccountRequestDTO();

        // the title, then the two questions of the brief's screen
        System.out.println(Message.TITLE_LOGIN);
        requestDTO.setUsername(inputText(sc, Message.INPUT_LOGIN_ACCOUNT));
        requestDTO.setPassword(MD5Utils.hash(inputText(sc, Message.INPUT_LOGIN_PASSWORD)));
        return requestDTO;
    }

    // Reads the answer to the welcome screen's "Y/N:" question: "Y" for y or Y, "N" for any
    // other line.
    private static String inputAnswer(Scanner sc) {
        return Validation.getAnswer(sc.nextLine());
    }

    // The change-password screen: old password, new password and the new one again. The
    // new one may not be empty and both typings must match; the old and the new password
    // are hashed here before they go into the request (which already holds the username).
    private static void inputNewPassword(Scanner sc, AccountRequestDTO requestDTO)
            throws Exception {
        String oldPassword = "";
        String newPassword = "";
        String renewPassword = "";

        // the three questions of the brief's screen, with no check between
        oldPassword = inputText(sc, Message.INPUT_OLD_PASSWORD);
        newPassword = inputText(sc, Message.INPUT_NEW_PASSWORD);
        renewPassword = inputText(sc, Message.INPUT_RENEW_PASSWORD);

        // "New password cannot be empty." or "The two new passwords do not match."
        newPassword = Validation.getNewPassword(newPassword, renewPassword);

        // MD5 here in main: only the two digests go into the request
        requestDTO.setOldPassword(MD5Utils.hash(oldPassword));
        requestDTO.setNewPassword(MD5Utils.hash(newPassword));
    }
}
