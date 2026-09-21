package main;

import constants.Constants;
import constants.Message;
import controller.DoctorController;
import dto.DoctorRequestDTO;
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

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoctorController controller = new DoctorController();
        DoctorRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read a new doctor, then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputAdd(sc);
                        controller.addDoctor(requestDTO);
                        break;

                    // option 2: read the code (checked at once) and the new values, then
                    // update that doctor
                    case Constants.MENU_UPDATE:
                        requestDTO = inputUpdate(sc, controller);
                        controller.updateDoctor(requestDTO);
                        break;

                    // option 3: read a code, then delete that doctor
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.deleteDoctor(requestDTO);
                        break;

                    // option 4: read a text, then list the doctors that contain it
                    case Constants.MENU_SEARCH:
                        requestDTO = inputSearch(sc);
                        controller.searchDoctor(requestDTO);
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
                // the message was written in Message and thrown by the controller
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
                // "Please input number" or "Please choose from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a doctor code until it is not blank (the brief: "Code is not null").
    private static String inputCode(Scanner sc) {
        String line = "";

        // keep asking until the code is not blank
        while (true) {
            System.out.print(Message.INPUT_CODE);
            line = sc.nextLine();

            // a blank code prints "Code cannot be blank." and loops again
            try {
                return Validation.getNonBlank(line, Message.CODE_BLANK);
            } catch (Exception e) {
                // show why the code was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Shows one prompt and reads one line, trimmed; a blank line is allowed (on update it
    // means "keep the old value").
    private static String inputText(Scanner sc, String prompt) {
        // one question, one answer - never asked again
        System.out.print(prompt);
        return Validation.getText(sc.nextLine());
    }

    // Asks for an availability until it is a number >= 0.
    private static int inputAvailability(Scanner sc) {
        String line = "";

        // keep asking until the availability is legal
        while (true) {
            System.out.print(Message.INPUT_AVAILABILITY);
            line = sc.nextLine();

            // a letter or a negative number prints the reason and loops again
            try {
                return Validation.checkAvailability(line);
            } catch (Exception e) {
                // show why the availability was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Like inputAvailability, but a blank line is accepted and means "keep".
    private static Integer inputOptionalAvailability(Scanner sc) {
        String line = "";

        // keep asking until the line is blank or a legal availability
        while (true) {
            System.out.print(Message.INPUT_AVAILABILITY);
            line = sc.nextLine();

            // a letter or a negative number prints the reason and loops again
            try {
                return Validation.checkOptionalAvailability(line);
            } catch (Exception e) {
                // show why the availability was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, then code, name, specialization and availability into a new
    // request.
    private static DoctorRequestDTO inputAdd(Scanner sc) {
        DoctorRequestDTO requestDTO = new DoctorRequestDTO();

        // the title of the add form, then its four questions
        System.out.println(Message.TITLE_ADD);
        requestDTO.setCode(inputCode(sc));
        requestDTO.setName(inputText(sc, Message.INPUT_NAME));
        requestDTO.setSpecialization(inputText(sc, Message.INPUT_SPECIALIZATION));
        requestDTO.setAvailability(inputAvailability(sc));
        return requestDTO;
    }

    // Option 2: the title and the code, then the new values. The brief stops at once when
    // the code does not exist, so the code goes through a check-only call to the
    // controller (it throws, it prints nothing) before the other questions.
    private static DoctorRequestDTO inputUpdate(Scanner sc, DoctorController controller)
            throws Exception {
        DoctorRequestDTO requestDTO = new DoctorRequestDTO();

        // the title of the update form, then the code
        System.out.println(Message.TITLE_UPDATE);
        requestDTO.setCode(inputText(sc, Message.INPUT_CODE));

        // check only: "Doctor code doesn’t exist" is thrown here when the code is unknown
        controller.checkExistDoctor(requestDTO);

        // the remaining information; a blank answer keeps the old value
        requestDTO.setName(inputText(sc, Message.INPUT_NAME));
        requestDTO.setSpecialization(inputText(sc, Message.INPUT_SPECIALIZATION));
        requestDTO.setAvailability(inputOptionalAvailability(sc));
        return requestDTO;
    }

    // Option 3: the title, then the code of the doctor to delete into a new request.
    private static DoctorRequestDTO inputDelete(Scanner sc) {
        DoctorRequestDTO requestDTO = new DoctorRequestDTO();

        // the title of the delete form, then the code
        System.out.println(Message.TITLE_DELETE);
        requestDTO.setCode(inputText(sc, Message.INPUT_CODE));
        return requestDTO;
    }

    // Option 4: the title, then the text to look for into a new request.
    private static DoctorRequestDTO inputSearch(Scanner sc) {
        DoctorRequestDTO requestDTO = new DoctorRequestDTO();

        // the title of the search form, then the text (a blank text lists every doctor)
        System.out.println(Message.TITLE_SEARCH);
        requestDTO.setSearchText(inputText(sc, Message.INPUT_SEARCH));
        return requestDTO;
    }
}
