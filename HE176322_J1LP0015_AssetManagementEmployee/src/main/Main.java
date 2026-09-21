package main;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import controller.EmployeeController;
import dto.AssetRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.MD5Utils;
import utils.Validation;

/**
 * MAIN: the work flow of the employee's program - reading the four files at start, the
 * menu loop and the keyboard. Every keyboard read, every validation, the reading of the
 * files and the MD5 of the password happen here; each function then calls the controller
 * once (plus the calls the brief needs before the next question).
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
        EmployeeController controller = new EmployeeController();
        AssetRequestDTO requestDTO = null;
        boolean running = true;
        boolean repeat = false;
        int choice = 0;

        // the four data files, once, at start-up
        loadData(controller);

        // show the menu again after every function, until Quit
        while (running) {
            // Y to "Do you want to continue" runs the same function again, without the menu
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

                    // Function 3: the assets are shown, a request is typed and sent, then
                    // ask to continue
                    case Constants.MENU_BORROW:
                        requestDTO = inputBorrow(sc, controller);
                        controller.sendRequest(requestDTO);
                        repeat = inputYesNo(sc, Message.ASK_CONTINUE);
                        break;

                    // Function 4: his requests are shown, one is chosen and confirmed, then
                    // ask to continue
                    case Constants.MENU_CANCEL:
                        requestDTO = inputCancel(sc, controller);

                        // the brief: confirm before cancel; N keeps the request
                        if (inputYesNo(sc, String.format(Message.ASK_CANCEL,
                                requestDTO.getRequestId().toUpperCase()))) {
                            controller.cancelRequest(requestDTO);
                        }

                        repeat = inputYesNo(sc, Message.ASK_CONTINUE);
                        break;

                    // Function 5: his borrows are shown, one is chosen and confirmed, then
                    // ask to continue
                    case Constants.MENU_RETURN:
                        requestDTO = inputReturn(sc, controller);

                        // confirm before the return; N keeps the borrow
                        if (inputYesNo(sc, String.format(Message.ASK_RETURN,
                                requestDTO.getBorrowId().toUpperCase()))) {
                            controller.returnBorrow(requestDTO);
                        }

                        repeat = inputYesNo(sc, Message.ASK_CONTINUE);
                        break;

                    // Quit: stop the loop
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

    // Start-up: reads the four data files (utils/FileUtils) into one request and hands it
    // to the controller, whose repositories turn the lines into objects.
    private static void loadData(EmployeeController controller) {
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

    // Function 3: the title and every asset (the brief shows the list first: the one
    // render of this function), then the asset and the quantity to borrow.
    private static AssetRequestDTO inputBorrow(Scanner sc, EmployeeController controller)
            throws Exception {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title, then the list; not logged in, the manager or no asset stops here
        System.out.println(Message.TITLE_BORROW);
        controller.showAssets();

        // the asset and the quantity, each asked again at once until it is legal
        requestDTO.setAssetId(inputText(sc, TextField.ASSET_ID));
        requestDTO.setQuantity((int) inputNumber(sc, NumberField.QUANTITY));
        return requestDTO;
    }

    // Function 4: the title and his requests (the brief shows them first: the one render
    // of this function), then the id of one of them.
    private static AssetRequestDTO inputCancel(Scanner sc, EmployeeController controller)
            throws Exception {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title, then the list; not logged in, the manager or no request stops here
        System.out.println(Message.TITLE_CANCEL);
        controller.showMyRequests();
        requestDTO.setRequestId(inputMyRequestId(sc, controller));
        return requestDTO;
    }

    // Function 5: the title and his borrows (the brief shows them first: the one render
    // of this function), then the id of one of them.
    private static AssetRequestDTO inputReturn(Scanner sc, EmployeeController controller)
            throws Exception {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // the title, then the list; not logged in, the manager or no borrow stops here
        System.out.println(Message.TITLE_RETURN);
        controller.showMyBorrows();
        requestDTO.setBorrowId(inputMyBorrowId(sc, controller));
        return requestDTO;
    }

    // Asks for a menu choice until it is a number from 1 to 6.
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
                // "Please choose from 1 to 6."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a request id until it is one of his requests (a check-only call to the
    // controller: it throws, it shows nothing) - the confirmation needs a real request.
    private static String inputMyRequestId(Scanner sc, EmployeeController controller) {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // keep asking until the id is one of his
        while (true) {
            requestDTO.setRequestId(inputText(sc, TextField.REQUEST_ID));

            // one of his is handed back; another id prints the reason and loops again
            try {
                controller.checkMyRequest(requestDTO);
                return requestDTO.getRequestId();
            } catch (Exception e) {
                // "You have no request with id R001."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a borrow id until it is one of his borrows (a check-only call to the
    // controller: it throws, it shows nothing) - the confirmation needs a real borrow.
    private static String inputMyBorrowId(Scanner sc, EmployeeController controller) {
        AssetRequestDTO requestDTO = new AssetRequestDTO();

        // keep asking until the id is one of his
        while (true) {
            requestDTO.setBorrowId(inputText(sc, TextField.BORROW_ID));

            // one of his is handed back; another id prints the reason and loops again
            try {
                controller.checkMyBorrow(requestDTO);
                return requestDTO.getBorrowId();
            } catch (Exception e) {
                // "You have no borrowed asset with id B003."
                System.out.println(e.getMessage());
            }
        }
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
