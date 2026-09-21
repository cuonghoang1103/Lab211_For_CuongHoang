package main;

import constants.Message;
import controller.AnalysisController;
import dto.AnalysisRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - Function 1 (read and check the string), then one call to the
 * controller for Function 2 (analyse and display).
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
        AnalysisController controller = new AnalysisController();
        AnalysisRequestDTO requestDTO = new AnalysisRequestDTO();

        // Function 1: the title, then the string typed by the user, carried by the request
        System.out.println(Message.TITLE);
        requestDTO.setInput(inputString(sc));

        // Function 2 (auto next): analyse and display - the controller is called once
        controller.analyzeString(requestDTO);
    }

    // Asks for the string until Validation accepts it.
    private static String inputString(Scanner sc) {
        String line = "";

        // keep asking until the string is accepted
        while (true) {
            System.out.print(Message.INPUT_STRING);
            line = sc.nextLine();

            // a blank line or a too-big number prints the reason and loops
            try {
                return Validation.getInput(line);
            } catch (Exception e) {
                // "Input must not be empty." or the too-big message
                System.out.println(e.getMessage());
            }
        }
    }
}
