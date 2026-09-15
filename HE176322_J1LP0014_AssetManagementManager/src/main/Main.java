package main;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import controller.ManagerController;
import dto.AssetRequestDTO;
import dto.LoginRequestDTO;
import dto.TransactionRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the manager's program - the menu and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: reads the files, then shows the menu until Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ManagerController controller = new ManagerController();
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
                    case Constants.MENU_CREATE:
                        createAssets(sc, controller);
                        break;
                    // Function 4
                    case Constants.MENU_UPDATE:
                        updateAsset(sc, controller);
                        break;
                    // Function 5
                    case Constants.MENU_APPROVE:
                        approveRequest(sc, controller);
                        break;
                    // Function 6
                    case Constants.MENU_BORROWS:
                        System.out.println(Message.TITLE_BORROWS);
                        controller.showBorrows();
                        break;
                    // Quit
                    case Constants.MENU_QUIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 1..7
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
    private static void login(Scanner sc, ManagerController controller) throws Exception {
        System.out.println(Message.TITLE_LOGIN);
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmployeeID(inputText(sc, TextField.EMPLOYEE_ID));
        dto.setPassword(inputText(sc, TextField.PASSWORD));
        controller.login(dto);
    }

    // Function 2: reads the text to search.
    private static void searchAsset(Scanner sc, ManagerController controller)
            throws Exception {
        System.out.println(Message.TITLE_SEARCH);
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setKeyword(inputText(sc, TextField.KEYWORD));
        controller.searchAsset(dto);
    }

    // Function 3: one asset per round, until the manager answers N.
    private static void createAssets(Scanner sc, ManagerController controller)
            throws Exception {
        System.out.println(Message.TITLE_CREATE);
        controller.checkManager();
        // one asset per round
        do {
            AssetRequestDTO dto = new AssetRequestDTO();
            dto.setAssetID(inputNewAssetId(sc, controller));
            dto.setName(inputText(sc, TextField.NAME));
            dto.setColor(inputText(sc, TextField.COLOR));
            dto.setPrice(inputNumber(sc, NumberField.PRICE));
            dto.setWeight(inputNumber(sc, NumberField.WEIGHT));
            dto.setQuantity((int) inputNumber(sc, NumberField.QUANTITY));
            controller.createAsset(dto);
        } while (inputYesNo(sc, Message.ASK_CREATE_ANOTHER)); // Y repeats, N leaves
    }

    // Function 4: finds the asset, then reads the new values (blank keeps the old one).
    private static void updateAsset(Scanner sc, ManagerController controller)
            throws Exception {
        System.out.println(Message.TITLE_UPDATE);
        controller.checkManager();
        AssetRequestDTO dto = new AssetRequestDTO();
        dto.setAssetID(inputText(sc, TextField.ASSET_ID));
        // stops here with "Asset does not exist" when the id is wrong
        controller.findAsset(dto);
        System.out.println(Message.KEEP_HINT);
        dto.setName(inputNewText(sc, TextField.NAME));
        dto.setColor(inputNewText(sc, TextField.COLOR));
        dto.setPrice(inputNewNumber(sc, NumberField.PRICE));
        dto.setWeight(inputNewNumber(sc, NumberField.WEIGHT));
        Double quantity = inputNewNumber(sc, NumberField.QUANTITY);
        dto.setQuantity(quantity == null ? null : quantity.intValue());
        controller.updateAsset(dto);
    }

    // Function 5: shows the requests, reads the id to approve.
    private static void approveRequest(Scanner sc, ManagerController controller)
            throws Exception {
        System.out.println(Message.TITLE_APPROVE);
        // stops here when not a manager or when there is no request
        controller.showRequests();
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setId(inputText(sc, TextField.REQUEST_ID));
        controller.approveRequest(dto);
    }

    // Asks for a menu choice until it is a number from 1 to 7.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_QUIT);
            } catch (Exception e) {
                // "Please choose from 1 to 7."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the id of a new asset until it has the right form and is not used.
    private static String inputNewAssetId(Scanner sc, ManagerController controller)
            throws Exception {
        AssetRequestDTO dto = new AssetRequestDTO();
        // keep asking until the id is free
        while (true) {
            dto.setAssetID(inputText(sc, TextField.ASSET_ID));
            // a taken id prints the reason and loops again
            try {
                controller.checkNewAssetId(dto);
                return dto.getAssetID();
            } catch (Exception e) {
                // "Asset A001 already exists."
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

    // Update: like inputText, but a blank line returns null (= keep the old value).
    private static String inputNewText(Scanner sc, TextField field) {
        // keep asking until the text is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            String line = sc.nextLine();
            // blank: keep
            if (line.trim().isEmpty()) {
                return null;
            }
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

    // Update: like inputNumber, but a blank line returns null (= keep the old value).
    private static Double inputNewNumber(Scanner sc, NumberField field) {
        // keep asking until the number is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            String line = sc.nextLine();
            // blank: keep
            if (line.trim().isEmpty()) {
                return null;
            }
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
