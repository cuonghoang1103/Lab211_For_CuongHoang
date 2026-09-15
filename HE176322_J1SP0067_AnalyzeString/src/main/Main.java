package main;

import constants.Message;
import controller.AnalysisController;
import dto.AnalysisRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - Function 1 (read the string), then one call to the controller for
 * Function 2 (analyse and display).
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AnalysisController controller = new AnalysisController();
        System.out.println(Message.TITLE);
        AnalysisRequestDTO dto = new AnalysisRequestDTO();
        dto.setInput(inputString(sc));
        controller.analyzeString(dto);
    }

    // Asks for the string until Validation accepts it.
    private static String inputString(Scanner sc) {
        // keep asking until the string is accepted
        while (true) {
            System.out.print(Message.INPUT_STRING);
            String line = sc.nextLine();
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
