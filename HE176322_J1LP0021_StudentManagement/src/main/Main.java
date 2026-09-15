package main;

import constants.Constants;
import constants.Message;
import controller.StudentController;
import dto.StudentRequestDTO;
import dto.StudentResponseDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the main screen until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = new StudentController();
        boolean running = true;
        // show the main screen again after every function, until Exit
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: create students
                    case Constants.MENU_CREATE:
                        createStudents(sc, controller);
                        break;
                    // option 2: find by name and sort
                    case Constants.MENU_FIND_SORT:
                        findAndSort(sc, controller);
                        break;
                    // option 3: find by id, then update or delete
                    case Constants.MENU_UPDATE_DELETE:
                        updateOrDelete(sc, controller);
                        break;
                    // option 4: report
                    case Constants.MENU_REPORT:
                        System.out.println(Message.TITLE_REPORT);
                        controller.report();
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

    // Asks for a whole number until one is typed.
    private static int inputInt(Scanner sc, String prompt) {
        // keep asking until the line is a whole number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // letters print "You must input a number." and loop again
            try {
                return Validation.getInt(line);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Like inputInt, but a blank line is accepted and means "keep".
    private static Integer inputOptionalInt(Scanner sc, String prompt) {
        // keep asking until the line is blank or a whole number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // letters print "You must input a number." and loop again
            try {
                return Validation.getOptionalInt(line);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks "Do you want to continue (Y/N)?" until Y or N is typed.
    private static boolean inputContinue(Scanner sc) {
        // keep asking until Y or N is typed
        while (true) {
            System.out.print(Message.ASK_CONTINUE);
            String line = sc.nextLine();
            // another answer prints "Please enter Y or N." and loops again
            try {
                return Validation.getOption(line, Constants.YES,
                        Constants.NO).equals(Constants.YES);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks "Do you want to update (U) or delete (D) student?" until U or D is typed.
    private static boolean inputDelete(Scanner sc) {
        // keep asking until U or D is typed
        while (true) {
            System.out.print(Message.ASK_UPDATE_DELETE);
            String line = sc.nextLine();
            // another answer prints "Please enter U or D." and loops again
            try {
                return Validation.getOption(line, Constants.UPDATE,
                        Constants.DELETE).equals(Constants.DELETE);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the four fields of one new student into a request DTO.
    private static StudentRequestDTO inputStudent(Scanner sc) {
        StudentRequestDTO dto = new StudentRequestDTO();
        System.out.print(Message.INPUT_ID);
        dto.setId(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_NAME);
        dto.setStudentName(Validation.getText(sc.nextLine()));
        dto.setSemester(inputInt(sc, Message.INPUT_SEMESTER));
        System.out.print(Message.INPUT_COURSE);
        dto.setCourseName(Validation.getText(sc.nextLine()));
        return dto;
    }

    // Option 1: creates students until there are at least 10 AND the user answers N to
    // "Do you want to continue (Y/N)?".
    private static void createStudents(Scanner sc, StudentController controller) {
        System.out.println(Message.TITLE_CREATE);
        boolean creating = true;
        // one student per turn, until the user stops
        while (creating) {
            // a refused student is reported and simply not counted
            try {
                controller.createStudent(inputStudent(sc));
            } catch (Exception e) {
                // blank id, duplicate id, semester <= 0, wrong course...
                System.out.println(e.getMessage());
            }
            // below the brief's minimum: no question, ask another student
            if (!controller.checkEnoughStudents()) {
                continue;
            }
            creating = inputContinue(sc);
        }
    }

    // Option 2: reads a name (or part of it) and asks the controller to find and sort.
    private static void findAndSort(Scanner sc, StudentController controller)
            throws Exception {
        System.out.println(Message.TITLE_FIND_SORT);
        StudentRequestDTO dto = new StudentRequestDTO();
        System.out.print(Message.INPUT_SEARCH);
        dto.setSearchText(Validation.getText(sc.nextLine()));
        controller.findAndSort(dto);
    }

    // Option 3: finds a student by id, shows it, then asks U or D.
    private static void updateOrDelete(Scanner sc, StudentController controller)
            throws Exception {
        System.out.println(Message.TITLE_UPDATE_DELETE);
        StudentRequestDTO dto = new StudentRequestDTO();
        System.out.print(Message.INPUT_STUDENT_ID);
        dto.setId(Validation.getText(sc.nextLine()));
        // stop here with the reason when the id is wrong
        StudentResponseDTO found = controller.findStudent(dto);
        dto.setId(found.getId());
        // D: delete and stop
        if (inputDelete(sc)) {
            controller.deleteStudent(dto);
            return;
        }
        System.out.print(String.format(Message.INPUT_NEW_NAME, found.getStudentName()));
        dto.setStudentName(Validation.getText(sc.nextLine()));
        dto.setSemester(inputOptionalInt(sc, String.format(Message.INPUT_NEW_SEMESTER,
                found.getSemester())));
        System.out.print(String.format(Message.INPUT_NEW_COURSE, found.getCourseName()));
        dto.setCourseName(Validation.getText(sc.nextLine()));
        controller.updateStudent(dto);
    }
}
