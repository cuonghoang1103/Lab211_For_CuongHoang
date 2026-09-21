package main;

import constants.Message;
import controller.ShapeController;
import dto.ShapeRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - Function 1 (input the data: every keyboard read and every
 * validation happen here), then Function 2 (calculate and display) through ONE call to the
 * controller.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads the six lengths, then calls the controller once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShapeController controller = new ShapeController();
        ShapeRequestDTO requestDTO = new ShapeRequestDTO();

        // Function 1: the title, then the six lengths (each asked again until it is valid)
        System.out.println(Message.TITLE);
        requestDTO.setWidth(inputLength(sc, Message.INPUT_WIDTH));
        requestDTO.setLength(inputLength(sc, Message.INPUT_LENGTH));
        requestDTO.setRadius(inputLength(sc, Message.INPUT_RADIUS));
        inputTriangle(sc, requestDTO);

        // Function 2: one call calculates and displays, then the program ends
        controller.calculate(requestDTO);
    }

    // Shows a prompt and asks until the user types a length greater than 0.
    private static double inputLength(Scanner sc, String prompt) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(prompt);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getPositiveDouble(line);
            } catch (Exception e) {
                // "You must input a number." or "Value must be greater than zero."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the three sides until they form a triangle, then stores them in the
    // request.
    private static void inputTriangle(Scanner sc, ShapeRequestDTO requestDTO) {
        double sideA = 0;
        double sideB = 0;
        double sideC = 0;

        // keep asking for three sides until they form a triangle
        while (true) {
            sideA = inputLength(sc, Message.INPUT_SIDE_A);
            sideB = inputLength(sc, Message.INPUT_SIDE_B);
            sideC = inputLength(sc, Message.INPUT_SIDE_C);

            // an impossible triangle prints the reason and loops again
            try {
                Validation.checkTriangle(sideA, sideB, sideC);
                requestDTO.setSideA(sideA);
                requestDTO.setSideB(sideB);
                requestDTO.setSideC(sideC);
                return;
            } catch (Exception e) {
                // "These three sides cannot form a triangle. Please input again."
                System.out.println(e.getMessage());
            }
        }
    }
}
