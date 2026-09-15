package main;

import constants.Constants;
import constants.Message;
import controller.TaskController;
import dto.TaskRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskController controller = new TaskController();
        boolean running = true;
        // show the menu again after every function, until exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add a task
                    case Constants.MENU_ADD:
                        addTask(sc, controller);
                        break;
                    // option 2: delete a task
                    case Constants.MENU_DELETE:
                        deleteTask(sc, controller);
                        break;
                    // option 3: show the tasks
                    case Constants.MENU_DISPLAY:
                        controller.displayTasks();
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
                // the message was written in Message and thrown by addTask/deleteTask
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
                // "You must input a number." or "Please choose from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads the seven values in the order of the brief's screen, then calls the
    // controller once.
    private static void addTask(Scanner sc, TaskController controller) throws Exception {
        System.out.println(Message.TITLE_ADD);
        TaskRequestDTO dto = new TaskRequestDTO();
        System.out.print(Message.INPUT_REQUIREMENT_NAME);
        dto.setRequirementName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_TASK_TYPE);
        dto.setTaskTypeId(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_DATE);
        dto.setDate(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_FROM);
        dto.setPlanFrom(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_TO);
        dto.setPlanTo(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_ASSIGNEE);
        dto.setAssignee(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_REVIEWER);
        dto.setReviewer(Validation.getText(sc.nextLine()));
        controller.addTask(dto);
    }

    // Option 2: reads the ID and calls the controller once.
    private static void deleteTask(Scanner sc, TaskController controller)
            throws Exception {
        System.out.println(Message.TITLE_DELETE);
        TaskRequestDTO dto = new TaskRequestDTO();
        System.out.print(Message.INPUT_ID);
        dto.setId(Validation.getText(sc.nextLine()));
        controller.deleteTask(dto);
    }
}
