package main;

import constants.Constants;
import constants.Message;
import controller.AccountController;
import dto.AccountRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - reading user.dat at start, the menu loop and the
 * keyboard. Every keyboard read, every validation and the reading of the file happen here;
 * each menu option then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: loads user.dat, then shows the menu until the user chooses
    // Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountController controller = new AccountController();
        AccountRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // MARKING "Loading user account from user.dat into Collection", once, at start
        loadData(controller);

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read a new account, then create it
                    case Constants.MENU_CREATE:
                        requestDTO = inputAccount(sc);
                        controller.addAccount(requestDTO);
                        break;

                    // option 2: read a user name and a password, then log in
                    case Constants.MENU_LOGIN:
                        requestDTO = inputAccount(sc);
                        controller.login(requestDTO);
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
                // the message was written in Message and thrown by the controller/repository
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the lines of user.dat (utils/FileUtils) into a request and hands them to the
    // controller, which lets the repository turn them into accounts.
    private static void loadData(AccountController controller) {
        AccountRequestDTO requestDTO = new AccountRequestDTO();

        // the first run: user.dat has not been created yet, so there is nothing to load
        if (!FileUtils.isFileExist(Constants.DATA_FILE)) {
            return;
        }

        // an unreadable user.dat is reported, and the program starts empty
        try {
            requestDTO.setLineList(FileUtils.readLines(Constants.DATA_FILE));
            controller.loadData(requestDTO);
        } catch (Exception e) {
            // "Can't read file user.dat"
            System.out.println(e.getMessage());
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

    // Asks for a user name until it has 5+ characters and no space.
    private static String inputUsername(Scanner sc) {
        String line = "";

        // keep asking until the user name is legal
        while (true) {
            System.out.print(Message.INPUT_USERNAME);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the password is legal
        while (true) {
            System.out.print(Message.INPUT_PASSWORD);
            line = sc.nextLine();

            // a short password or one with a space prints the reason, asks again
            try {
                return Validation.getPassword(line);
            } catch (Exception e) {
                // "You must enter least at 6 character, and no space!"
                System.out.println(e.getMessage());
            }
        }
    }

    // Options 1 and 2: a user name and a password into a new request (the brief asks the
    // same two questions, with the same rules, for create and login).
    private static AccountRequestDTO inputAccount(Scanner sc) {
        AccountRequestDTO requestDTO = new AccountRequestDTO();

        // each question is asked again at once until its answer is legal
        requestDTO.setUsername(inputUsername(sc));
        requestDTO.setPassword(inputPassword(sc));
        return requestDTO;
    }
}
