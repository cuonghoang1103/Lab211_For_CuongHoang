package main;

import constants.Message;
import controller.CountController;
import dto.CountRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the content is read and checked here, then the controller is
 * called once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads a content that is not blank, then calls the controller once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CountController controller = new CountController();
        CountRequestDTO requestDTO = new CountRequestDTO();

        // the content typed by the user, carried to the controller by the request
        requestDTO.setContent(inputContent(sc));

        // count and display - the controller is called once
        controller.countContent(requestDTO);
    }

    // Asks for the content until the user types something that is not blank.
    private static String inputContent(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_CONTENT);
            line = sc.nextLine();

            // a blank line prints the reason and loops again
            try {
                return Validation.getContent(line);
            } catch (Exception e) {
                // "Input must not be empty."
                System.out.println(e.getMessage());
            }
        }
    }
}
