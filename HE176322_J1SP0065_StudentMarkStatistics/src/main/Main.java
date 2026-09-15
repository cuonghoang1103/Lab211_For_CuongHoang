package main;

import constants.Message;
import controller.StudentController;
import dto.StudentRequestDTO;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: Function 1 - read students until the user answers N - then call the controller
 * once for Function 2.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = new StudentController();
        ArrayList<StudentRequestDTO> requestList = new ArrayList<>();
        System.out.println(Message.TITLE);
        // read one student, then ask whether there is another
        do {
            requestList.add(inputStudent(sc));
        } while (inputMore(sc)); // repeat while the user answers Y
        // the Y/N prompt has no line break, so the results start on a new line
        System.out.println();
        controller.classifyStudents(requestList);
    }

    // Reads one student: name, class and the three marks.
    private static StudentRequestDTO inputStudent(Scanner sc) {
        StudentRequestDTO dto = new StudentRequestDTO();
        System.out.print(Message.INPUT_NAME);
        dto.setName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_CLASSES);
        dto.setClasses(Validation.getText(sc.nextLine()));
        dto.setMaths(inputMark(sc, Message.MATHS));
        dto.setChemistry(inputMark(sc, Message.CHEMISTRY));
        dto.setPhysics(inputMark(sc, Message.PHYSICS));
        return dto;
    }

    // Asks for one mark until it is a number from 0 to 10.
    private static double inputMark(Scanner sc, String subject) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(String.format(Message.INPUT_MARK, subject));
            String line = sc.nextLine();
            // a wrong line prints the brief's message and loops again
            try {
                return Validation.getMark(line, subject);
            } catch (Exception e) {
                // "Maths is digit", "... less than equal ten", "... zero"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks the Y/N question until the answer is Y or N.
    private static boolean inputMore(Scanner sc) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.INPUT_MORE);
            String answer = sc.nextLine();
            // a legal answer ends the question
            if (Validation.isYesOrNo(answer)) {
                return Validation.isYes(answer);
            }
        }
    }
}
