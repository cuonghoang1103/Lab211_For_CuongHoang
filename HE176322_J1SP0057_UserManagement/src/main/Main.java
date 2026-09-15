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

    // Starts the program: loads user.dat, then shows the menu until the user chooses
    // Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountController controller = new AccountController();
        // an unreadable user.dat is reported, and the program starts empty
        try {
            controller.loadData();
        } catch (Exception e) {
            // "Can't read file user.dat"
            System.out.println(e.getMessage());
        }
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: create a new account
                    case Constants.MENU_CREATE:
                        createAccount(sc, controller);
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

    // Asks for a user name until it has 5+ characters and no space.
    private static String inputUsername(Scanner sc) {
        // keep asking until the user name is legal
        while (true) {
            System.out.print(Message.INPUT_USERNAME);
            String line = sc.nextLine();
            // a short name or a name with a space prints the reason, asks again
            try {
                return Validation.getUsername(line);
            } catch (Exception e) {
                // "You must enter least at 5 character, and no space!"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a password until it has 6+ characters and no space.
    private static String inputPassword(Scanner sc) {
        // keep asking until the password is legal
        while (true) {
            System.out.print(Message.INPUT_PASSWORD);
            String line = sc.nextLine();
            // a short password or one with a space prints the reason, asks again
            try {
                return Validation.getPassword(line);
            } catch (Exception e) {
                // "You must enter least at 6 character, and no space!"
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads a user name and a password into one request.
    private static AccountRequestDTO inputAccount(Scanner sc) {
        AccountRequestDTO dto = new AccountRequestDTO();
        dto.setUsername(inputUsername(sc));
        dto.setPassword(inputPassword(sc));
        return dto;
    }

    // Option 1: reads a new account and calls the controller once.
    private static void createAccount(Scanner sc, AccountController controller)
            throws Exception {
        controller.addAccount(inputAccount(sc));
    }

    // Option 2: reads a user name and password and calls the controller once.
    private static void login(Scanner sc, AccountController controller)
            throws Exception {
        controller.login(inputAccount(sc));
    }
}
