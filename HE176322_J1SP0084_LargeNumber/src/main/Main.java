package main;

import constants.Message;
import controller.MultiplyController;
import dto.MultiplyRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read two numbers, then call the controller once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MultiplyController controller = new MultiplyController();
        MultiplyRequestDTO dto = new MultiplyRequestDTO();
        dto.setFirstNumber(inputNumber(sc, Message.INPUT_FIRST));
        dto.setSecondNumber(inputNumber(sc, Message.INPUT_SECOND));
        controller.multiply(dto);
    }

    // Asks for one number until the user types digits only.
    private static String inputNumber(Scanner sc, String prompt) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getDigits(line);
            } catch (Exception e) {
                // "You must input digit."
                System.out.println(e.getMessage());
            }
        }
    }
}
