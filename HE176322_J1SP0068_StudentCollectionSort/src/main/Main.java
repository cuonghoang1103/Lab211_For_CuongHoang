package main;

import constants.Message;
import controller.StudentController;
import dto.StudentDTO;
import dto.StudentRequestDTO;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - Function 1 (input and validate students until N), then one call
 * to the controller for Function 2 (sort and display).
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads the students, then calls the controller once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = new StudentController();
        StudentRequestDTO requestDTO = new StudentRequestDTO();
        ArrayList<StudentDTO> studentList = new ArrayList<>();
        boolean more = true;

        // Function 1: the title of the brief's screen, once
        System.out.println(Message.TITLE);

        // one student per turn, until the user answers N
        while (more) {
            studentList.add(inputStudent(sc));
            more = inputYesNo(sc);
        }

        // Function 2 (auto next): sort and display - the controller is called once
        requestDTO.setStudentList(studentList);
        controller.displaySortedStudents(requestDTO);
    }

    // Reads one student: name, class and mark.
    private static StudentDTO inputStudent(Scanner sc) {
        StudentDTO studentDTO = new StudentDTO();

        // the brief's line before each student, then its three fields
        System.out.println(Message.INPUT_INFO);
        studentDTO.setName(inputName(sc));
        studentDTO.setClasses(inputClasses(sc));
        studentDTO.setMark(inputMark(sc));
        return studentDTO;
    }

    // Asks for the name until it is not blank.
    private static String inputName(Scanner sc) {
        String line = "";

        // keep asking until the name is not blank
        while (true) {
            System.out.print(Message.INPUT_NAME);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the class is not blank
        while (true) {
            System.out.print(Message.INPUT_CLASSES);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until Validation accepts the mark
        while (true) {
            System.out.print(Message.INPUT_MARK);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.ASK_MORE);
            line = sc.nextLine();

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
