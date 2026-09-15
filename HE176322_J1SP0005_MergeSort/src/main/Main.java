package main;

import constants.Message;
import controller.SortController;
import dto.SortRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the size, then call the controller once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SortController controller = new SortController();
        SortRequestDTO dto = new SortRequestDTO();
        dto.setSize(inputSize(sc));
        controller.sortArray(dto);
    }

    // Asks for the size of the array until the user types a legal number.
    private static int inputSize(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_SIZE);
            String line = sc.nextLine();
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
