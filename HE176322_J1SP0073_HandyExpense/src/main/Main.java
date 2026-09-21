package main;

import constants.Constants;
import constants.Message;
import controller.ExpenseController;
import dto.ExpenseRequestDTO;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - read the data file, then the menu loop. Every
 * keyboard read, every validation and the reading of the file happen here; each menu option
 * then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: loads the saved expenses, then shows the menu until the user
    // chooses Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseController controller = new ExpenseController();
        ExpenseRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // start-up: the lines of the data file go to the controller once; a broken file is
        // reported and the program still starts with an empty book
        try {
            requestDTO = readData();
            controller.loadExpenses(requestDTO);
        } catch (IOException e) {
            // "Cannot read expenses.txt: ..."
            System.out.println(String.format(Message.LOAD_FAIL, e.getMessage()));
        }

        // show the menu again after every function, until Quit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read a new expense, then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputExpense(sc);
                        controller.addExpense(requestDTO);
                        break;

                    // option 2: the title, then every expense and the total
                    case Constants.MENU_DISPLAY:
                        System.out.println(Message.TITLE_DISPLAY);
                        controller.displayAll();
                        break;

                    // option 3: read an ID, then delete that expense
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.deleteExpense(requestDTO);
                        break;

                    // option 4: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;

                    // unreachable: inputChoice only returns 1..4
                    default:
                        break;
                }
            } catch (Exception e) {
                // "Delete an expense fail" (or a file error), thrown under the controller
                System.out.println(e.getMessage());
            }
        }
    }

    // Start-up: reads every line of the data file into a new request - reading the file is
    // main's job (checklist 1.1). No file yet (first run) gives an empty list.
    private static ExpenseRequestDTO readData() throws IOException {
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO();

        // the repository turns these lines into expenses
        requestDTO.setLineList(FileUtils.readLines(Constants.FILE_NAME));
        return requestDTO;
    }

    // Asks for a menu choice until the user types a number from 1 to 4.
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
                // "You must input a number." or "Please input a number in [1, 4]."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the date until it is a real dd-MMM-yyyy date.
    private static Date inputDate(Scanner sc) {
        String line = "";

        // keep asking until the date is valid
        while (true) {
            System.out.print(Message.INPUT_DATE);
            line = sc.nextLine();

            // a wrong date prints the format and loops again
            try {
                return Validation.getDate(line);
            } catch (Exception e) {
                // "Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the amount until it is a number greater than 0.
    private static double inputAmount(Scanner sc) {
        String line = "";

        // keep asking until the amount is valid
        while (true) {
            System.out.print(Message.INPUT_AMOUNT);
            line = sc.nextLine();

            // a wrong amount prints the reason and loops again
            try {
                return Validation.getAmount(line);
            } catch (Exception e) {
                // "Amount must be a number." or "... greater than 0."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the content until it is not blank.
    private static String inputContent(Scanner sc) {
        String line = "";

        // keep asking until the content is not blank
        while (true) {
            System.out.print(Message.INPUT_CONTENT);
            line = sc.nextLine();

            // a blank content prints the reason and loops again
            try {
                return Validation.getContent(line);
            } catch (Exception e) {
                // "This field must not be empty."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the ID to delete until it is a whole number.
    private static int inputId(Scanner sc) {
        String line = "";

        // keep asking until the ID is a whole number
        while (true) {
            System.out.print(Message.INPUT_ID);
            line = sc.nextLine();

            // a letter prints "You must input a number." and loops again
            try {
                return Validation.getInt(line);
            } catch (Exception e) {
                // show why the ID was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, then date, amount and content into a new request.
    private static ExpenseRequestDTO inputExpense(Scanner sc) {
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO();

        // the title of the add form, then its three questions (asked again until valid)
        System.out.println(Message.TITLE_ADD);
        requestDTO.setDate(inputDate(sc));
        requestDTO.setAmount(inputAmount(sc));
        requestDTO.setContent(inputContent(sc));
        return requestDTO;
    }

    // Option 3: the title, then the ID to delete into a new request.
    private static ExpenseRequestDTO inputDelete(Scanner sc) {
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO();

        // the title of the delete form, then the ID (asked again until it is a number)
        System.out.println(Message.TITLE_DELETE);
        requestDTO.setId(inputId(sc));
        return requestDTO;
    }
}
