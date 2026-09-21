package main;

import constants.Constants;
import constants.Message;
import controller.TaskController;
import dto.TaskRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every validation happen here; each menu option then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until the user chooses exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskController controller = new TaskController();
        TaskRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a wrong value or an unknown ID stops the function; its message is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read and check a new task, then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputTask(sc);
                        controller.addTask(requestDTO);
                        break;

                    // option 2: read and check an ID, then delete that task
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.deleteTask(requestDTO);
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
                // the message was written in Message and thrown by Validation or deleteTask
                System.out.println(e.getMessage());
            }
        }
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
                // "You must input a number." or "Please choose from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Prints a prompt and returns the line typed after it, not checked yet.
    private static String inputLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    // Option 1: the title and the seven prompts in the order of the brief's screen. Like the
    // brief's addTask, the values are checked only when all seven are typed - name, assignee,
    // reviewer, type, date, from, to - and the first wrong one throws its message.
    private static TaskRequestDTO inputTask(Scanner sc) throws Exception {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        String requirementName = "";
        String taskTypeId = "";
        String date = "";
        String planFrom = "";
        String planTo = "";
        String assignee = "";
        String reviewer = "";

        // the title of the add screen, then the seven values as typed
        System.out.println(Message.TITLE_ADD);
        requirementName = inputLine(sc, Message.INPUT_REQUIREMENT_NAME);
        taskTypeId = inputLine(sc, Message.INPUT_TASK_TYPE);
        date = inputLine(sc, Message.INPUT_DATE);
        planFrom = inputLine(sc, Message.INPUT_FROM);
        planTo = inputLine(sc, Message.INPUT_TO);
        assignee = inputLine(sc, Message.INPUT_ASSIGNEE);
        reviewer = inputLine(sc, Message.INPUT_REVIEWER);

        // check them in the order of the brief's addTask; each clean value goes in the request
        requestDTO.setRequirementName(Validation.getRequired(requirementName, Message.NAME_EMPTY));
        requestDTO.setAssignee(Validation.getRequired(assignee, Message.ASSIGNEE_EMPTY));
        requestDTO.setReviewer(Validation.getRequired(reviewer, Message.REVIEWER_EMPTY));
        requestDTO.setTaskType(Validation.getTaskType(taskTypeId));
        requestDTO.setDate(Validation.getDate(date));
        requestDTO.setPlanFrom(Validation.getPlanTime(planFrom, Message.LABEL_PLAN_FROM));
        requestDTO.setPlanTo(Validation.getPlanTime(planTo, Message.LABEL_PLAN_TO));

        // last rule, it needs both times: "Plan From must be less than Plan To"
        Validation.checkPlanOrder(requestDTO.getPlanFrom(), requestDTO.getPlanTo());
        return requestDTO;
    }

    // Option 2: the title, then the ID to delete - checked to be a whole number - into a
    // new request.
    private static TaskRequestDTO inputDelete(Scanner sc) throws Exception {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        String id = "";

        // the title of the delete screen, then the ID as typed
        System.out.println(Message.TITLE_DELETE);
        id = inputLine(sc, Message.INPUT_ID);

        // blank or not a number: the flow stops with "ID cannot be empty." / "ID must be..."
        requestDTO.setId(Validation.getId(id));
        return requestDTO;
    }
}
