package main;

import constants.Constants;
import constants.Message;
import controller.DoctorController;
import dto.DoctorRequestDTO;
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
        DoctorController controller = new DoctorController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add a doctor
                    case Constants.MENU_ADD:
                        addDoctor(sc, controller);
                        break;
                    // option 2: update a doctor
                    case Constants.MENU_UPDATE:
                        updateDoctor(sc, controller);
                        break;
                    // option 3: delete a doctor
                    case Constants.MENU_DELETE:
                        deleteDoctor(sc, controller);
                        break;
                    // option 4: search doctors
                    case Constants.MENU_SEARCH:
                        searchDoctor(sc, controller);
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
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please input number" or "Please choose from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a doctor code until it is not blank.
    private static String inputCode(Scanner sc) {
        // keep asking until the code is not blank
        while (true) {
            System.out.print(Message.INPUT_CODE);
            String line = sc.nextLine();
            // a blank code prints "Code cannot be blank." and loops again
            try {
                return Validation.getNonBlank(line, Message.CODE_BLANK);
            } catch (Exception e) {
                // show why the code was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an availability until it is a number >= 0.
    private static int inputAvailability(Scanner sc) {
        // keep asking until the availability is legal
        while (true) {
            System.out.print(Message.INPUT_AVAILABILITY);
            String line = sc.nextLine();
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
        // keep asking until the line is blank or a legal availability
        while (true) {
            System.out.print(Message.INPUT_AVAILABILITY);
            String line = sc.nextLine();
            // a letter or a negative number prints the reason and loops again
            try {
                return Validation.checkOptionalAvailability(line);
            } catch (Exception e) {
                // show why the availability was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads a new doctor and calls the controller once.
    private static void addDoctor(Scanner sc, DoctorController controller)
            throws Exception {
        System.out.println(Message.TITLE_ADD);
        DoctorRequestDTO dto = new DoctorRequestDTO();
        dto.setCode(inputCode(sc));
        System.out.print(Message.INPUT_NAME);
        dto.setName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_SPECIALIZATION);
        dto.setSpecialization(Validation.getText(sc.nextLine()));
        dto.setAvailability(inputAvailability(sc));
        controller.addDoctor(dto);
    }

    // Option 2: reads the code, checks it exists, then reads the new values.
    private static void updateDoctor(Scanner sc, DoctorController controller)
            throws Exception {
        System.out.println(Message.TITLE_UPDATE);
        DoctorRequestDTO dto = new DoctorRequestDTO();
        System.out.print(Message.INPUT_CODE);
        dto.setCode(Validation.getText(sc.nextLine()));
        // stop here with "Doctor code doesn't exist" before asking the rest
        controller.checkExistDoctor(dto);
        System.out.print(Message.INPUT_NAME);
        dto.setName(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_SPECIALIZATION);
        dto.setSpecialization(Validation.getText(sc.nextLine()));
        dto.setAvailability(inputOptionalAvailability(sc));
        controller.updateDoctor(dto);
    }

    // Option 3: reads a code and asks the controller to delete it.
    private static void deleteDoctor(Scanner sc, DoctorController controller)
            throws Exception {
        System.out.println(Message.TITLE_DELETE);
        DoctorRequestDTO dto = new DoctorRequestDTO();
        System.out.print(Message.INPUT_CODE);
        dto.setCode(Validation.getText(sc.nextLine()));
        controller.deleteDoctor(dto);
    }

    // Option 4: reads a search text and asks the controller to show matches.
    private static void searchDoctor(Scanner sc, DoctorController controller)
            throws Exception {
        System.out.println(Message.TITLE_SEARCH);
        DoctorRequestDTO dto = new DoctorRequestDTO();
        System.out.print(Message.INPUT_SEARCH);
        dto.setSearchText(Validation.getText(sc.nextLine()));
        controller.searchDoctor(dto);
    }
}
