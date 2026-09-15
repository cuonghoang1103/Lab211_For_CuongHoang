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
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactController controller = new ContactController();
        System.out.println(Message.TITLE);
        ContactRequestDTO dto = new ContactRequestDTO();
        dto.setPhone(inputPhone(sc));
        dto.setEmail(inputEmail(sc));
        dto.setDate(inputDate(sc));
        // a business error from the service is shown instead of a crash
        try {
            controller.saveContact(dto);
        } catch (Exception e) {
            // "Date to correct format(dd/MM/yyyy)"
            System.out.println(e.getMessage());
        }
    }

    // Asks for the phone number until checkPhone returns no error.
    private static String inputPhone(Scanner sc) {
        // keep asking until the phone is correct
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            String phone = sc.nextLine();
            String error = Validation.checkPhone(phone);
            // the brief: an empty message means the value is correct
            if (error.isEmpty()) {
                return Validation.getText(phone);
            }
            System.out.println(error);
        }
    }

    // Asks for the email until checkEmail returns no error.
    private static String inputEmail(Scanner sc) {
        // keep asking until the email is correct
        while (true) {
            System.out.print(Message.INPUT_EMAIL);
            String email = sc.nextLine();
            String error = Validation.checkEmail(email);
            // the brief: an empty message means the value is correct
            if (error.isEmpty()) {
                return Validation.getText(email);
            }
            System.out.println(error);
        }
    }

    // Asks for the date until checkDate returns no error.
    private static String inputDate(Scanner sc) {
        // keep asking until the date is correct
        while (true) {
            System.out.print(Message.INPUT_DATE);
            String date = sc.nextLine();
            String error = Validation.checkDate(date);
            // the brief: an empty message means the value is correct
            if (error.isEmpty()) {
                return Validation.getText(date);
            }
            System.out.println(error);
        }
    }
}
