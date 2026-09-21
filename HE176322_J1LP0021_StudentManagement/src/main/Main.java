package main;

import constants.Constants;
import constants.Message;
import controller.StudentController;
import dto.StudentRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every check of the form of what was typed happen here; each menu option then calls
 * the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the main screen until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = new StudentController();
        StudentRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the main screen again after every function, until Exit
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read at least 10 new students, then store them all
                    case Constants.MENU_CREATE:
                        requestDTO = inputStudentList(sc, controller);
                        controller.createStudents(requestDTO);
                        break;

                    // option 2: read a name (or a part of it), then find and sort
                    case Constants.MENU_FIND_SORT:
                        requestDTO = inputSearch(sc);
                        controller.findAndSort(requestDTO);
                        break;

                    // option 3: read an id and the answer U or D, then update or delete
                    case Constants.MENU_UPDATE_DELETE:
                        requestDTO = inputUpdateDelete(sc, controller);
                        controller.updateOrDeleteStudent(requestDTO);
                        break;

                    // option 4: the title, then the report
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

    // Asks for a whole number until one is typed (the "> 0" rule is the service's).
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
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Like inputInt, but a blank line is accepted and means "keep" (null).
    private static Integer inputOptionalInt(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the line is blank or a whole number
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // letters print "You must input a number." and loop again
            try {
                return Validation.getOptionalInt(line);
            } catch (Exception e) {
                // show why the value was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks "Do you want to continue (Y/N)?" until Y or N is typed; true means Y.
    private static boolean inputContinue(Scanner sc) {
        String line = "";

        // keep asking until Y or N is typed
        while (true) {
            System.out.print(Message.ASK_CONTINUE);
            line = sc.nextLine();

            // another answer prints "Please enter Y or N." and loops again
            try {
                return Constants.YES.equals(Validation.getOption(line, Constants.YES,
                        Constants.NO));
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks "Do you want to update (U) or delete (D) student?" until U or D is typed.
    private static String inputOption(Scanner sc) {
        String line = "";

        // keep asking until U or D is typed
        while (true) {
            System.out.print(Message.ASK_UPDATE_DELETE);
            line = sc.nextLine();

            // another answer prints "Please enter U or D." and loops again
            try {
                return Validation.getOption(line, Constants.UPDATE, Constants.DELETE);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the four fields of one new student into the request (the rules of the brief
    // are checked by the service after the four answers).
    private static void inputStudent(Scanner sc, StudentRequestDTO requestDTO) {
        // the four questions of a new student, in order
        System.out.print(Message.INPUT_ID);
        requestDTO.setId(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_NAME);
        requestDTO.setStudentName(Validation.getText(sc.nextLine()));
        requestDTO.setSemester(inputInt(sc, Message.INPUT_SEMESTER));
        System.out.print(Message.INPUT_COURSE);
        requestDTO.setCourseName(Validation.getText(sc.nextLine()));
    }

    // Option 1: the title, then new students until the list holds at least 10 (the
    // students already stored count too) and the user answers N to the brief's question.
    // Each student is checked before it is kept, so the request holds only good students.
    private static StudentRequestDTO inputStudentList(Scanner sc,
            StudentController controller) {
        StudentRequestDTO requestDTO = new StudentRequestDTO();
        boolean creating = true;
        int storedCount = 0;
        int count = 0;

        // the title of the Create screen
        System.out.println(Message.TITLE_CREATE);

        // read only (no render, nothing changed): the brief's "number of students" is the
        // whole list, so the students already stored count too
        storedCount = controller.countStudents();

        // one student per turn, until the user may stop and does
        while (creating) {
            // a student that breaks a rule is reported and not kept
            try {
                inputStudent(sc, requestDTO);
                keepStudent(controller, requestDTO);
            } catch (Exception e) {
                // blank id, id already used, blank name, semester <= 0, wrong course
                System.out.println(e.getMessage());
            }

            // the brief's count: students already stored + students kept in this Create
            count = storedCount + requestDTO.getStudentList().size();

            // fewer than 10: say how far off, then another student
            if (count < Constants.MIN_STUDENTS) {
                System.out.println(String.format(Message.NEED_MORE, Constants.MIN_STUDENTS,
                        count));
            } else {
                // at least 10: the brief's question decides (Y = one more student)
                creating = inputContinue(sc);
            }
        }

        return requestDTO;
    }

    // Option 1: the student main has just read joins the request only when it keeps every
    // rule of the brief - a check-only call first, then a copy of its four fields is kept.
    private static void keepStudent(StudentController controller,
            StudentRequestDTO requestDTO) throws Exception {
        // check only (no render, nothing stored): the brief counts the students, so a
        // wrong one must be refused now, not after the tenth
        controller.checkStudent(requestDTO);

        // it keeps every rule: keep a copy of its four fields
        requestDTO.getStudentList().add(new StudentRequestDTO(requestDTO.getId(),
                requestDTO.getStudentName(), requestDTO.getSemester(),
                requestDTO.getCourseName()));
    }

    // Option 2: the title, then the name (or a part of it) to look for, into a new request.
    private static StudentRequestDTO inputSearch(Scanner sc) {
        StudentRequestDTO requestDTO = new StudentRequestDTO();

        // the title of the Find and Sort screen, then its one question
        System.out.println(Message.TITLE_FIND_SORT);
        System.out.print(Message.INPUT_SEARCH);
        requestDTO.setSearchText(Validation.getText(sc.nextLine()));
        return requestDTO;
    }

    // Option 3: the title and the id, then the brief's question U or D, then - for U - the
    // new values. The brief finds the student BEFORE its question, so the id goes through
    // a check-only call to the controller first.
    private static StudentRequestDTO inputUpdateDelete(Scanner sc,
            StudentController controller) throws Exception {
        StudentRequestDTO requestDTO = new StudentRequestDTO();

        // the title of the Update/Delete screen, then the id
        System.out.println(Message.TITLE_UPDATE_DELETE);
        System.out.print(Message.INPUT_STUDENT_ID);
        requestDTO.setId(Validation.getText(sc.nextLine()));

        // check only (no render, nothing changed): "ID [..] does not exist." stops here
        controller.checkExistStudent(requestDTO);

        // the brief's question: U or D
        requestDTO.setOption(inputOption(sc));

        // U: the new values; a blank answer keeps the old one
        if (Constants.UPDATE.equals(requestDTO.getOption())) {
            System.out.print(Message.INPUT_NEW_NAME);
            requestDTO.setStudentName(Validation.getText(sc.nextLine()));
            requestDTO.setSemester(inputOptionalInt(sc, Message.INPUT_NEW_SEMESTER));
            System.out.print(Message.INPUT_NEW_COURSE);
            requestDTO.setCourseName(Validation.getText(sc.nextLine()));
        }

        return requestDTO;
    }
}
