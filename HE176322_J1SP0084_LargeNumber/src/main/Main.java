package main;

import constants.Message;
import controller.MultiplyController;
import dto.MultiplyRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read and validate two numbers, then call the controller once.
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
        MultiplyController controller = new MultiplyController();
        MultiplyRequestDTO requestDTO = new MultiplyRequestDTO();

        // both numbers are read and checked here, then the controller is called once
        requestDTO.setFirstNumber(inputNumber(sc, Message.INPUT_FIRST));
        requestDTO.setSecondNumber(inputNumber(sc, Message.INPUT_SECOND));
        controller.multiply(requestDTO);
    }

    // Asks for one number until the user types digits only.
    private static String inputNumber(Scanner sc, String prompt) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

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
