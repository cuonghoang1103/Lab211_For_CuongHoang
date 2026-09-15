package main;

import constants.Constants;
import constants.Message;
import constants.SalaryStatus;
import controller.WorkerController;
import dto.SalaryRequestDTO;
import dto.WorkerRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WorkerController controller = new WorkerController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add a worker
                    case Constants.MENU_ADD:
                        controller.addWorker(inputWorker(sc));
                        break;
                    // option 2: raise a salary
                    case Constants.MENU_UP:
                        controller.changeSalary(inputSalary(sc, SalaryStatus.UP));
                        break;
                    // option 3: cut a salary
                    case Constants.MENU_DOWN:
                        controller.changeSalary(inputSalary(sc, SalaryStatus.DOWN));
                        break;
                    // option 4: show the salary log
                    case Constants.MENU_DISPLAY:
                        controller.getInfomationSalary();
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
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a whole number until the line is one.
    private static int inputInt(Scanner sc, String prompt) {
        // keep asking until the line is a whole number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
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
        // keep asking until the line is a number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // letters print "You must input a number." and loop again
            try {
                return Validation.getDouble(line);
            } catch (Exception e) {
                // show why the line was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads the five values of a new worker into a DTO.
    private static WorkerRequestDTO inputWorker(Scanner sc) {
        System.out.println(Message.TITLE_ADD);
        WorkerRequestDTO dto = new WorkerRequestDTO();
        System.out.print(Message.INPUT_CODE);
        dto.setCode(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_NAME);
        dto.setName(Validation.getText(sc.nextLine()));
        dto.setAge(inputInt(sc, Message.INPUT_AGE));
        dto.setSalary(inputDouble(sc, Message.INPUT_SALARY));
        System.out.print(Message.INPUT_LOCATION);
        dto.setWorkLocation(Validation.getText(sc.nextLine()));
        return dto;
    }

    // Options 2 and 3: reads the code and the amount into a DTO.
    private static SalaryRequestDTO inputSalary(Scanner sc, SalaryStatus status) {
        System.out.println(Message.TITLE_CHANGE);
        SalaryRequestDTO dto = new SalaryRequestDTO();
        dto.setStatus(status);
        System.out.print(Message.INPUT_CODE);
        dto.setCode(Validation.getText(sc.nextLine()));
        dto.setAmount(inputDouble(sc, Message.INPUT_AMOUNT));
        return dto;
    }
}
