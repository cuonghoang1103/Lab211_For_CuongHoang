package main;

import constants.Constants;
import constants.Message;
import controller.GraphController;
import dto.GraphRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read and validate the two points, then call the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: the only flow reads the two points, then calls the controller
    // exactly once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GraphController controller = new GraphController();
        GraphRequestDTO requestDTO = new GraphRequestDTO();

        requestDTO.setStart(inputPoint(sc, Message.INPUT_START));
        requestDTO.setEnd(inputPoint(sc, Message.INPUT_END));
        controller.checkEdge(requestDTO);
    }

    // Asks for one point until the user types the label of a vertex.
    private static int inputPoint(Scanner sc, String prompt) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(prompt);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line, Constants.FIRST_VERTEX, Constants.VERTICES);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }
}
