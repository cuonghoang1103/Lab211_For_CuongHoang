package main;

import constants.Message;
import controller.StudentController;
import dto.StudentRequestDTO;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - Function 1 (input students until N), then one call to the
 * controller for Function 2 (sort and display).
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = new StudentController();
        ArrayList<StudentRequestDTO> requests = new ArrayList<>();
        System.out.println(Message.TITLE);
        boolean more = true;
        // one student per turn, until the user answers N
        while (more) {
            requests.add(inputStudent(sc));
            more = inputYesNo(sc);
        }
        controller.displaySortedStudents(requests);
    }

    // Reads one student: name, class and mark.
    private static StudentRequestDTO inputStudent(Scanner sc) {
        System.out.println(Message.INPUT_INFO);
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setName(inputName(sc));
        dto.setClasses(inputClasses(sc));
        dto.setMark(inputMark(sc));
        return dto;
    }

    // Asks for the name until it is not blank.
    private static String inputName(Scanner sc) {
        // keep asking until the name is not blank
        while (true) {
            System.out.print(Message.INPUT_NAME);
            String line = sc.nextLine();
            // a blank line prints "Name must not be empty." and loops again
            try {
                return Validation.getNonBlank(line, Message.NAME_EMPTY);
            } catch (Exception e) {
                // show why the name was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the class until it is not blank.
    private static String inputClasses(Scanner sc) {
        // keep asking until the class is not blank
        while (true) {
            System.out.print(Message.INPUT_CLASSES);
            String line = sc.nextLine();
            // a blank line prints "Class must not be empty." and loops again
            try {
                return Validation.getNonBlank(line, Message.CLASS_EMPTY);
            } catch (Exception e) {
                // show why the class was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the mark until it is a number from 0 to 100.
    private static float inputMark(Scanner sc) {
        // keep asking until Validation accepts the mark
        while (true) {
            System.out.print(Message.INPUT_MARK);
            String line = sc.nextLine();
            // a wrong mark prints the reason and loops again
            try {
                return Validation.getMark(line);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks whether another student follows, until the answer is Y or N.
    private static boolean inputYesNo(Scanner sc) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.ASK_MORE);
            String line = sc.nextLine();
            // a wrong answer prints "Please answer Y or N." and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // show why the answer was refused
                System.out.println(e.getMessage());
            }
        }
    }
}
