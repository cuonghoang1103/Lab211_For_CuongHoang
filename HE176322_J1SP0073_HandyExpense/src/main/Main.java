package main;

import constants.Constants;
import constants.Message;
import controller.ExpenseController;
import dto.ExpenseRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - load the file, then the menu loop.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: loads the saved expenses, then shows the menu until the user
    // chooses Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseController controller = new ExpenseController();
        // a broken data file is reported, the program still starts
        try {
            controller.loadExpenses();
        } catch (Exception e) {
            // "Cannot read expenses.txt: ..."
            System.out.println(e.getMessage());
        }
        boolean running = true;
        // show the menu again after every function, until Quit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add an expense
                    case Constants.MENU_ADD:
                        controller.addExpense(inputExpense(sc));
                        break;
                    // option 2: display all expenses
                    case Constants.MENU_DISPLAY:
                        controller.displayAll();
                        break;
                    // option 3: delete an expense
                    case Constants.MENU_DELETE:
                        controller.deleteExpense(inputDelete(sc));
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
                // the message was written in Message and thrown by the service
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 4.
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
                // "You must input a number." or "Please input a number in [1, 4]."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads date, amount and content, each asked again until valid.
    private static ExpenseRequestDTO inputExpense(Scanner sc) {
        System.out.println(Message.TITLE_ADD);
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        // keep asking until the date is a real dd-MMM-yyyy date
        while (dto.getDate() == null) {
            System.out.print(Message.INPUT_DATE);
            String line = sc.nextLine();
            // a wrong date prints the format and loops again
            try {
                dto.setDate(Validation.getDate(line));
            } catch (Exception e) {
                // "Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009."
                System.out.println(e.getMessage());
            }
        }
        // keep asking until the amount is a number greater than 0
        while (dto.getAmount() <= 0) {
            System.out.print(Message.INPUT_AMOUNT);
            String line = sc.nextLine();
            // a wrong amount prints the reason and loops again
            try {
                dto.setAmount(Validation.getAmount(line));
            } catch (Exception e) {
                // "Amount must be a number." or "... greater than 0."
                System.out.println(e.getMessage());
            }
        }
        // keep asking until the content is not blank
        while (dto.getContent() == null) {
            System.out.print(Message.INPUT_CONTENT);
            String line = sc.nextLine();
            // a blank content prints the reason and loops again
            try {
                dto.setContent(Validation.getContent(line));
            } catch (Exception e) {
                // "This field must not be empty."
                System.out.println(e.getMessage());
            }
        }
        return dto;
    }

    // Option 3: reads the ID to delete, asked again until it is a number.
    private static ExpenseRequestDTO inputDelete(Scanner sc) {
        System.out.println(Message.TITLE_DELETE);
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        // keep asking until the ID is a whole number
        while (true) {
            System.out.print(Message.INPUT_ID);
            String line = sc.nextLine();
            // a letter prints "You must input a number." and loops again
            try {
                dto.setId(Validation.getInt(line));
                return dto;
            } catch (Exception e) {
                // show why the ID was refused
                System.out.println(e.getMessage());
            }
        }
    }
}
