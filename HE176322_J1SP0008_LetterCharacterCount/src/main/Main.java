package main;

import constants.Message;
import controller.CountController;
import dto.CountRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the content, then call the controller once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CountController controller = new CountController();
        CountRequestDTO dto = new CountRequestDTO();
        dto.setContent(inputContent(sc));
        controller.countContent(dto);
    }

    // Asks for the content until the user types something that is not blank.
    private static String inputContent(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_CONTENT);
            String line = sc.nextLine();
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
