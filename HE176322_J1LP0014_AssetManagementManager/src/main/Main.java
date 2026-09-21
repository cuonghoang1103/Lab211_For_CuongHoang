package main;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import controller.ManagerController;
import dto.AssetRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.MD5Utils;
import utils.Validation;

/**
 * MAIN: the work flow of the manager's program - reading the four files at start, the menu
 * loop and the keyboard. Every keyboard read, every validation, the reading of the files
 * and the MD5 of the password happen here; each function then calls the controller once
 * (plus the check-only calls the brief needs before the next question).
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads the files, then shows the menu until Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ManagerController controller = new ManagerController();
        AssetRequestDTO requestDTO = null;
        boolean running = true;
        boolean repeat = false;
        int choice = 0;

        // the four data files, once, at start-up
        loadData(controller);

        // show the menu again after every function, until Quit
        while (running) {
            // Y to "Create another asset?" runs Function 3 again, without the menu
            if (!repeat) {
                System.out.println(Message.MENU);
                choice = inputChoice(sc);
            }

            repeat = false;

            // a business error of the chosen function is shown here, then the menu
            try {
                // run the function the user picked: one call to the controller per function
                switch (choice) {
                    // Function 1: the id and the password (hashed here), then log in
                    case Constants.MENU_LOGIN:
                        requestDTO = inputLogin(sc);
                        controller.login(requestDTO);
                        break;

                    // Function 2: the text, then the matching assets
                    case Constants.MENU_SEARCH:
                        requestDTO = inputSearch(sc);
                        controller.searchAsset(requestDTO);
                        break;

                    // Function 3: a new asset, then ask to create another one
                    case Constants.MENU_CREATE:
                        requestDTO = inputCreate(sc, controller);
                        controller.createAsset(requestDTO);
                        repeat = inputYesNo(sc, Message.ASK_CREATE_ANOTHER);
                        break;

                    // Function 4: an existing asset and its new values, then update it
                    case Constants.MENU_UPDATE:
                        requestDTO = inputUpdate(sc, controller);
                        controller.updateAsset(requestDTO);
                        break;

                    // Function 5: the requests are shown, one is chosen, then approve it
                    case Constants.MENU_APPROVE:
                        requestDTO = inputApprove(sc, controller);
                        controller.acceptRequest(requestDTO);
                        break;

                    // Function 6: the title, then the borrowed assets
                    case Constants.MENU_BORROWS:
                        System.out.println(Message.TITLE_BORROWS);
                        controller.showBorrows();
                        break;

                    // Quit: stop the loop
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

    // Start-up: reads the four data files (utils/FileUtils) into one request and hands it
    // to the controller, whose repositories turn the lines into objects.
    private static void loadData(ManagerController controller) {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // an unreadable file is reported; the program still starts
        try {
            requestDTO.setAssetLineList(FileUtils.readLines(Constants.ASSET_FILE));
            requestDTO.setEmployeeLineList(FileUtils.readLines(Constants.EMPLOYEE_FILE));
            requestDTO.setRequestLineList(FileUtils.readLines(Constants.REQUEST_FILE));
            requestDTO.setBorrowLineList(FileUtils.readLines(Constants.BORROW_FILE));
            controller.loadData(requestDTO);
        } catch (Exception e) {
            // "Cannot access data file asset.dat."
            System.out.println(e.getMessage());
        }
    }

    // Function 1: the title, the id and the password; the password is hashed here (MD5),
    // so the typed text goes no further.
    private static AssetRequestDTO inputLogin(Scanner sc) {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title, then the two questions
        System.out.println(Message.TITLE_LOGIN);
        requestDTO.setEmployeeId(inputText(sc, TextField.EMPLOYEE_ID));
        requestDTO.setPassword(MD5Utils.hash(inputText(sc, TextField.PASSWORD)));
        return requestDTO;
    }

    // Function 2: the title and the text to search.
    private static AssetRequestDTO inputSearch(Scanner sc) {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title, then the text
        System.out.println(Message.TITLE_SEARCH);
        requestDTO.setKeyword(inputText(sc, TextField.KEYWORD));
        return requestDTO;
    }

    // Function 3: the title, the manager check before any question (a check-only call:
    // it throws, it shows nothing), then the six values of the new asset.
    private static AssetRequestDTO inputCreate(Scanner sc, ManagerController controller)
            throws Exception {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title; not logged in or not the manager stops here
        System.out.println(Message.TITLE_CREATE);
        controller.checkManager();

        // the six values, each asked again at once until it is legal
        requestDTO.setAssetId(inputNewAssetId(sc, controller));
        requestDTO.setName(inputText(sc, TextField.NAME));
        requestDTO.setColor(inputText(sc, TextField.COLOR));
        requestDTO.setPrice(inputNumber(sc, NumberField.PRICE));
        requestDTO.setWeight(inputNumber(sc, NumberField.WEIGHT));
        requestDTO.setQuantity((int) inputNumber(sc, NumberField.QUANTITY));
        return requestDTO;
    }

    // Function 4: the title, the manager check before any question (check-only), an
    // existing asset id, then the new values - a blank line keeps the old value.
    private static AssetRequestDTO inputUpdate(Scanner sc, ManagerController controller)
            throws Exception {
        AssetRequestDTO requestDTO = null;
        Double quantity = null;

        // the title; not logged in or not the manager stops here
        System.out.println(Message.TITLE_UPDATE);
        controller.checkManager();

        // the brief: "Asset does not exist" stops here, before the new values
        requestDTO = inputExistingAssetId(sc, controller);

        // the new values; blank = keep
        System.out.println(Message.KEEP_HINT);
        requestDTO.setName(inputNewText(sc, TextField.NAME));
        requestDTO.setColor(inputNewText(sc, TextField.COLOR));
        requestDTO.setPrice(inputNewNumber(sc, NumberField.PRICE));
        requestDTO.setWeight(inputNewNumber(sc, NumberField.WEIGHT));
        quantity = inputNewNumber(sc, NumberField.QUANTITY);
        requestDTO.setQuantity((quantity == null) ? null : quantity.intValue());
        return requestDTO;
    }

    // Function 5: the title and the waiting requests (the brief shows them before the
    // choice: the one render of this function), then the id of the request to approve.
    private static AssetRequestDTO inputApprove(Scanner sc, ManagerController controller)
            throws Exception {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title, then the list; not the manager or no request stops here
        System.out.println(Message.TITLE_APPROVE);
        controller.showRequests();
        requestDTO.setRequestId(inputText(sc, TextField.REQUEST_ID));
        return requestDTO;
    }

    // Asks for a menu choice until it is a number from 1 to 7.
    private static int inputChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_QUIT);
            } catch (Exception e) {
                // "Please choose from 1 to 7."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the id of a new asset until it has the right form and is not used yet (a
    // check-only call to the controller: it throws, it shows nothing).
    private static String inputNewAssetId(Scanner sc, ManagerController controller) {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // keep asking until the id is free
        while (true) {
            requestDTO.setAssetId(inputText(sc, TextField.ASSET_ID));

            // a free id is handed back; a taken one prints the reason and loops again
            try {
                controller.checkNewAssetId(requestDTO);
                return requestDTO.getAssetId();
            } catch (Exception e) {
                // "Asset A001 already exists."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the id of the asset to update; a wrong id is thrown at once (a check-only
    // call to the controller: it throws, it shows nothing).
    private static AssetRequestDTO inputExistingAssetId(Scanner sc,
            ManagerController controller) throws Exception {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the id, then "Asset does not exist" when no asset has it
        requestDTO.setAssetId(inputText(sc, TextField.ASSET_ID));
        controller.checkAssetExist(requestDTO);
        return requestDTO;
    }

    // Asks for a text field until it matches its pattern.
    private static String inputText(Scanner sc, TextField field) {
        String line = "";

        // keep asking until the text is legal
        while (true) {
            System.out.print(field.getPrompt());
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the text is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the number is legal
        while (true) {
            System.out.print(field.getPrompt());
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the number is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(question);
            line = sc.nextLine();

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
