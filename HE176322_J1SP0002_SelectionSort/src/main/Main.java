package main;

import constants.Message;
import controller.SortController;
import dto.SortRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow. The keyboard is read and checked here (the brief's Function 1);
 * the sorting (Function 2) is one call to the controller.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads a legal size, then calls the controller once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SortController controller = new SortController();
        SortRequestDTO requestDTO = new SortRequestDTO();

        // Function 1: the size typed by the user, carried to the controller by the request
        requestDTO.setSize(inputSize(sc));

        // Function 2: generate, sort and display - the controller is called once
        controller.sortArray(requestDTO);
    }

    // Asks for the size of the array until the user types a legal number.
    private static int inputSize(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_SIZE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getSize(line);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }
}
