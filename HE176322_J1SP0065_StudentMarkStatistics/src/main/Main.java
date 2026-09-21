package main;

import constants.Message;
import controller.StudentController;
import dto.ReportRequestDTO;
import dto.StudentRequestDTO;
import java.util.ArrayList;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: Function 1 - read and validate students until the user answers N - then call the
 * controller once for Function 2.
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
        ReportRequestDTO requestDTO = new ReportRequestDTO();
        ArrayList<StudentRequestDTO> studentList = new ArrayList<>();

        // Function 1: the title of the brief's screen, once
        System.out.println(Message.TITLE);

        // read one student, then ask whether there is another
        do {
            studentList.add(inputStudent(sc));
        } while (inputMore(sc)); // repeat while the user answers Y

        // Function 2: classify, count and display - the controller is called once; the N
        // the user typed has already ended the line, so the results follow directly
        requestDTO.setStudentList(studentList);
        controller.classifyStudents(requestDTO);
    }

    // Reads one student: name, class and the three marks.
    private static StudentRequestDTO inputStudent(Scanner sc) {
        StudentRequestDTO studentDTO = new StudentRequestDTO();

        // the name and the class: any text, trimmed (the brief checks only the marks)
        System.out.print(Message.INPUT_NAME);
        studentDTO.setName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_CLASSES);
        studentDTO.setClasses(Validation.getText(sc.nextLine()));

        // the three marks in the order of the brief's screen, each asked until it is valid
        studentDTO.setMaths(inputMark(sc, Message.MATHS));
        studentDTO.setChemistry(inputMark(sc, Message.CHEMISTRY));
        studentDTO.setPhysics(inputMark(sc, Message.PHYSICS));
        return studentDTO;
    }

    // Asks for one mark until it is a number from 0 to 10.
    private static double inputMark(Scanner sc, String subject) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(String.format(Message.INPUT_MARK, subject));
            line = sc.nextLine();

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
        String answer = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.INPUT_MORE);
            answer = sc.nextLine();

            // a legal answer ends the question; anything else asks again (the brief has
            // no message for it)
            if (Validation.isYesOrNo(answer)) {
                return Validation.isYes(answer);
            }
        }
    }
}
