package main;

import constants.Message;
import controller.ContactController;
import dto.ContactRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the phone, the email and the date, each until its check
 * returns no error (Function 1), then call the controller once (Function 2).
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactController controller = new ContactController();
        ContactRequestDTO requestDTO = new ContactRequestDTO();

        // Function 1: the title, then the three values, each asked again until it is right
        System.out.println(Message.TITLE);
        requestDTO.setPhone(inputPhone(sc));
        requestDTO.setEmail(inputEmail(sc));
        requestDTO.setDate(inputDate(sc));

        // Function 2: the controller is called once; a business error from the service is
        // shown instead of a crash
        try {
            controller.saveContact(requestDTO);
        } catch (Exception e) {
            // "Date to correct format(dd/MM/yyyy)"
            System.out.println(e.getMessage());
        }
    }

    // Asks for the phone number until checkPhone returns no error.
    private static String inputPhone(Scanner sc) {
        String line = "";
        String error = "";

        // keep asking until the phone is correct
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            line = sc.nextLine();
            error = Validation.checkPhone(line);

            // the brief: an empty message means the value is correct
            if (error.isEmpty()) {
                return Validation.getText(line);
            }

            // wrong: the brief's message, then ask again
            System.out.println(error);
        }
    }

    // Asks for the email until checkEmail returns no error.
    private static String inputEmail(Scanner sc) {
        String line = "";
        String error = "";

        // keep asking until the email is correct
        while (true) {
            System.out.print(Message.INPUT_EMAIL);
            line = sc.nextLine();
            error = Validation.checkEmail(line);

            // the brief: an empty message means the value is correct
            if (error.isEmpty()) {
                return Validation.getText(line);
            }

            // wrong: the brief's message, then ask again
            System.out.println(error);
        }
    }

    // Asks for the date until checkDate returns no error.
    private static String inputDate(Scanner sc) {
        String line = "";
        String error = "";

        // keep asking until the date is correct
        while (true) {
            System.out.print(Message.INPUT_DATE);
            line = sc.nextLine();
            error = Validation.checkDate(line);

            // the brief: an empty message means the value is correct
            if (error.isEmpty()) {
                return Validation.getText(line);
            }

            // wrong: the brief's message, then ask again
            System.out.println(error);
        }
    }
}
