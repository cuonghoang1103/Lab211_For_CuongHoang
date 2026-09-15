package main;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import controller.EmployeeController;
import dto.AssetRequestDTO;
import dto.LoginRequestDTO;
import dto.TransactionRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the employee's program - the menu and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: reads the files, then shows the menu until Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeController controller = new EmployeeController();
        // an unreadable file is reported; the program still starts
        try {
            controller.loadData();
        } catch (Exception e) {
            // "Cannot access data file asset.dat."
            System.out.println(e.getMessage());
        }
        boolean running = true;
        // show the menu again after every function, until Quit
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // Function 1
                    case Constants.MENU_LOGIN:
                        login(sc, controller);
                        break;
                    // Function 2
                    case Constants.MENU_SEARCH:
                        searchAsset(sc, controller);
                        break;
                    // Function 3
                    case Constants.MENU_BORROW:
                        borrowAssets(sc, controller);
                        break;
                    // Function 4
                    case Constants.MENU_CANCEL:
                        cancelRequests(sc, controller);
                        break;
                    // Function 5
                    case Constants.MENU_RETURN:
                        returnAssets(sc, controller);
                        break;
                    // Quit
                    case Constants.MENU_QUIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 1..6
                    default:
                        break;
                }
            } catch (Exception e) {
                // the reason written in Message, like "You must login first."
                System.out.println(e.getMessage());
            }
        }
    }

    // Function 1: reads the id and the password.
    private static void login(Scanner sc, EmployeeController controller) throws Exception {
        System.out.println(Message.TITLE_LOGIN);
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmployeeID(inputText(sc, TextField.EMPLOYEE_ID));
        dto.setPassword(inputText(sc, TextField.PASSWORD));
        controller.login(dto);
    }

    // Function 2: reads the text to search.
    private static void searchAsset(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_SEARCH);
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setKeyword(inputText(sc, TextField.KEYWORD));
        controller.searchAsset(dto);
    }

    // Function 3: shows the assets, reads the asset and the quantity, sends the request;
    // again until the employee answers N.
    private static void borrowAssets(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_BORROW);
        controller.checkEmployee();
        // one request per round
        do {
            controller.showAllAssets();
            TransactionRequestDTO dto = new TransactionRequestDTO();
            dto.setAssetID(inputText(sc, TextField.ASSET_ID));
            dto.setQuantity((int) inputNumber(sc, NumberField.QUANTITY));
            // an unknown asset or too big a quantity ends this round only
            try {
                controller.sendRequest(dto);
            } catch (Exception e) {
                // "Asset does not exist" or "Only 5 Macbook pro 2016 left in stock."
                System.out.println(e.getMessage());
            }
        } while (inputYesNo(sc, Message.ASK_CONTINUE)); // Y repeats, N leaves
    }

    // Function 4: shows his requests, reads the id, confirms, cancels; again until N.
    private static void cancelRequests(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_CANCEL);
        controller.checkEmployee();
        // one request per round; no request left ends the function
        do {
            controller.showMyRequests();
            TransactionRequestDTO dto = new TransactionRequestDTO();
            dto.setId(inputText(sc, TextField.REQUEST_ID));
            // an id that is not his ends this round only
            try {
                controller.checkMyRequest(dto);
                // the brief: confirm before cancel
                if (inputYesNo(sc, String.format(Message.ASK_CANCEL,
                        dto.getId().toUpperCase()))) {
                    controller.cancelRequest(dto);
                } else {
                    // N: the request stays
                    System.out.println(Message.NOTHING_CANCELLED);
                }
            } catch (Exception e) {
                // "You have no request with id R001."
                System.out.println(e.getMessage());
            }
        } while (inputYesNo(sc, Message.ASK_CONTINUE)); // Y repeats, N leaves
    }

    // Function 5: shows his borrows, reads the id, confirms, returns; again until N.
    private static void returnAssets(Scanner sc, EmployeeController controller)
            throws Exception {
        System.out.println(Message.TITLE_RETURN);
        controller.checkEmployee();
        // one borrow per round; no borrow left ends the function
        do {
            controller.showMyBorrows();
            TransactionRequestDTO dto = new TransactionRequestDTO();
            dto.setId(inputText(sc, TextField.BORROW_ID));
            // an id that is not his ends this round only
            try {
                controller.checkMyBorrow(dto);
                // confirm before the return
                if (inputYesNo(sc, String.format(Message.ASK_RETURN,
                        dto.getId().toUpperCase()))) {
                    controller.returnBorrow(dto);
                } else {
                    // N: the borrow stays
                    System.out.println(Message.NOTHING_RETURNED);
                }
            } catch (Exception e) {
                // "You have no borrowed asset with id B003."
                System.out.println(e.getMessage());
            }
        } while (inputYesNo(sc, Message.ASK_CONTINUE)); // Y repeats, N leaves
    }

    // Asks for a menu choice until it is a number from 1 to 6.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_QUIT);
            } catch (Exception e) {
                // "Please choose from 1 to 6."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text field until it matches its pattern.
    private static String inputText(Scanner sc, TextField field) {
        // keep asking until the text is legal
        while (true) {
            System.out.print(field.getPrompt());
            String line = sc.nextLine();
            // a wrong text prints the field's rule and loops again
            try {
                return Validation.checkText(line, field);
            } catch (Exception e) {
                // the field's own error message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a number field until it is in its range.
    private static double inputNumber(Scanner sc, NumberField field) {
        // keep asking until the number is legal
        while (true) {
            System.out.print(field.getPrompt());
            String line = sc.nextLine();
            // a wrong number prints the field's rule and loops again
            try {
                return Validation.checkNumber(line, field);
            } catch (Exception e) {
                // the field's own error message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a (Y/N) question until the answer is Y or N.
    private static boolean inputYesNo(Scanner sc, String question) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(question);
            String line = sc.nextLine();
            // anything else prints "Please enter Y or N." and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }
}
