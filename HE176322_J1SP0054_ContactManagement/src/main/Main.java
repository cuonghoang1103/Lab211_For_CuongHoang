package main;

import constants.Constants;
import constants.Message;
import controller.ContactController;
import dto.ContactRequestDTO;
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
        ContactController controller = new ContactController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // a business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add a contact
                    case Constants.MENU_ADD:
                        addContact(sc, controller);
                        break;
                    // option 2: display all contacts
                    case Constants.MENU_DISPLAY:
                        System.out.println(Message.TITLE_DISPLAY);
                        controller.displayAll();
                        break;
                    // option 3: delete a contact
                    case Constants.MENU_DELETE:
                        deleteContact(sc, controller);
                        break;
                    // option 4: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        break;
                    // unreachable: inputChoice only returns 1..4
                    default:
                        break;
                }
            } catch (Exception e) {
                // "No found contact", thrown by the controller
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
                return Validation.getChoice(line, Constants.MENU_ADD,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please choice one option from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text until it is not blank.
    private static String inputText(Scanner sc, String field) {
        String error = String.format(Message.FIELD_BLANK, field);
        // keep asking until the text is not blank
        while (true) {
            System.out.print(String.format(Message.INPUT_FIELD, field));
            String line = sc.nextLine();
            // a blank line prints the reason and loops again
            try {
                return Validation.getNonBlank(line, error);
            } catch (Exception e) {
                // e.g. "Name must not be blank."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a phone until it is in one of the seven formats.
    private static String inputPhone(Scanner sc) {
        // keep asking until the phone matches a format
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            String line = sc.nextLine();
            // a wrong phone prints the list of formats and loops again
            try {
                return Validation.checkPhone(line);
            } catch (Exception e) {
                // "Please input Phone flow" and the seven formats
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an ID until it is a positive whole number.
    private static int inputId(Scanner sc) {
        // keep asking until the ID is a number
        while (true) {
            System.out.print(Message.INPUT_ID);
            String line = sc.nextLine();
            // a wrong ID prints "ID is digit" and loops again
            try {
                return Validation.checkId(line);
            } catch (Exception e) {
                // "ID is digit"
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads a new contact and calls the controller once.
    private static void addContact(Scanner sc, ContactController controller) {
        System.out.println(Message.TITLE_ADD);
        ContactRequestDTO dto = new ContactRequestDTO();
        dto.setFullName(inputText(sc, Message.LABEL_NAME));
        dto.setGroup(inputText(sc, Message.LABEL_GROUP));
        dto.setAddress(inputText(sc, Message.LABEL_ADDRESS));
        dto.setPhone(inputPhone(sc));
        controller.addContact(dto);
    }

    // Option 3: reads an ID and asks the controller to delete it.
    private static void deleteContact(Scanner sc, ContactController controller)
            throws Exception {
        System.out.println(Message.TITLE_DELETE);
        ContactRequestDTO dto = new ContactRequestDTO();
        dto.setId(inputId(sc));
        controller.deleteContact(dto);
    }
}
