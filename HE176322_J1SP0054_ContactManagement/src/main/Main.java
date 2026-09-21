package main;

import constants.Constants;
import constants.Message;
import controller.ContactController;
import dto.ContactRequestDTO;
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
        ContactController controller = new ContactController();
        ContactRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read a new contact, then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputContact(sc);
                        controller.addContact(requestDTO);
                        break;

                    // option 2: the title, then every contact
                    case Constants.MENU_DISPLAY:
                        System.out.println(Message.TITLE_DISPLAY);
                        controller.displayAll();
                        break;

                    // option 3: read an ID, then delete that contact
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.deleteContact(requestDTO);
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_ADD, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please choice one option from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text until it is not blank.
    private static String inputText(Scanner sc, String field) {
        String error = String.format(Message.FIELD_BLANK, field);
        String line = "";

        // keep asking until the text is not blank
        while (true) {
            System.out.print(String.format(Message.INPUT_FIELD, field));
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the phone matches a format
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the ID is a number
        while (true) {
            System.out.print(Message.INPUT_ID);
            line = sc.nextLine();

            // a wrong ID prints "ID is digit" and loops again
            try {
                return Validation.checkId(line);
            } catch (Exception e) {
                // "ID is digit"
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, then name, group, address and phone into a new request.
    private static ContactRequestDTO inputContact(Scanner sc) {
        ContactRequestDTO requestDTO = new ContactRequestDTO();

        // the title of the add form, then its four questions (asked again until valid)
        System.out.println(Message.TITLE_ADD);
        requestDTO.setFullName(inputText(sc, Message.LABEL_NAME));
        requestDTO.setGroup(inputText(sc, Message.LABEL_GROUP));
        requestDTO.setAddress(inputText(sc, Message.LABEL_ADDRESS));
        requestDTO.setPhone(inputPhone(sc));
        return requestDTO;
    }

    // Option 3: the title, then the ID to delete into a new request.
    private static ContactRequestDTO inputDelete(Scanner sc) {
        ContactRequestDTO requestDTO = new ContactRequestDTO();

        // the title of the delete form, then the ID (asked again until it is a number)
        System.out.println(Message.TITLE_DELETE);
        requestDTO.setId(inputId(sc));
        return requestDTO;
    }
}
