package main;

import constants.Constants;
import constants.Message;
import constants.SalaryStatus;
import controller.WorkerController;
import dto.WorkerRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every validation of the form happen here; each menu option then calls the controller
 * once.
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
        WorkerController controller = new WorkerController();
        WorkerRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read a new worker, then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputWorker(sc);
                        controller.addWorker(requestDTO);
                        break;

                    // option 2: read the code and the amount, then raise the salary
                    case Constants.MENU_UP:
                        requestDTO = inputSalary(sc, SalaryStatus.UP);
                        controller.changeSalary(requestDTO);
                        break;

                    // option 3: read the code and the amount, then cut the salary
                    case Constants.MENU_DOWN:
                        requestDTO = inputSalary(sc, SalaryStatus.DOWN);
                        controller.changeSalary(requestDTO);
                        break;

                    // option 4: the title, then the salary log sorted by code
                    case Constants.MENU_DISPLAY:
                        System.out.println(Message.TITLE_DISPLAY);
                        controller.displaySalaryHistory();
                        break;

                    // option 5: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;

                    // unreachable: inputChoice only returns 1..5
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the service
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 5.
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
                // "You must input a number." or "Please choose from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Shows a prompt and returns the line typed, without surrounding spaces.
    private static String inputText(Scanner sc, String prompt) {
        System.out.print(prompt);
        return Validation.getText(sc.nextLine());
    }

    // Asks for a whole number until the line is one.
    private static int inputInt(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the line is a whole number
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // letters print "You must input a number." and loop again
            try {
                return Validation.getInt(line);
            } catch (Exception e) {
                // show why the line was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a real number until the line is one.
    private static double inputDouble(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the line is a number
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // letters print "You must input a number." and loop again
            try {
                return Validation.getDouble(line);
            } catch (Exception e) {
                // show why the line was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, then the five values of a new worker into a new request (the
    // rules of the brief are checked by the service after the five answers).
    private static WorkerRequestDTO inputWorker(Scanner sc) {
        WorkerRequestDTO requestDTO = new WorkerRequestDTO();

        // the title of the add form, then its five questions in the brief's order
        System.out.println(Message.TITLE_ADD);
        requestDTO.setCode(inputText(sc, Message.INPUT_CODE));
        requestDTO.setName(inputText(sc, Message.INPUT_NAME));
        requestDTO.setAge(inputInt(sc, Message.INPUT_AGE));
        requestDTO.setSalary(inputDouble(sc, Message.INPUT_SALARY));
        requestDTO.setWorkLocation(inputText(sc, Message.INPUT_LOCATION));
        return requestDTO;
    }

    // Options 2 and 3: the title, then the code and the amount into a new request; the
    // direction (UP or DOWN) comes from the menu option, not from the keyboard.
    private static WorkerRequestDTO inputSalary(Scanner sc, SalaryStatus status) {
        WorkerRequestDTO requestDTO = new WorkerRequestDTO();

        // the title of the up/down form, then its two questions
        System.out.println(Message.TITLE_CHANGE);
        requestDTO.setStatus(status);
        requestDTO.setCode(inputText(sc, Message.INPUT_CODE));
        requestDTO.setAmount(inputDouble(sc, Message.INPUT_AMOUNT));
        return requestDTO;
    }
}
