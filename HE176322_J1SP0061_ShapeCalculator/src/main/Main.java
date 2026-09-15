package main;

import constants.Message;
import controller.ShapeController;
import dto.ShapeRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - Function 1 (input the data), then Function 2 (calculate and
 * display) through ONE call to the controller.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: reads the six lengths, then calls the controller.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShapeController controller = new ShapeController();
        System.out.println(Message.TITLE);
        ShapeRequestDTO dto = new ShapeRequestDTO();
        dto.setWidth(inputLength(sc, Message.INPUT_WIDTH));
        dto.setLength(inputLength(sc, Message.INPUT_LENGTH));
        dto.setRadius(inputLength(sc, Message.INPUT_RADIUS));
        inputTriangle(sc, dto);
        controller.calculate(dto);
    }

    // Shows a prompt and asks until the user types a length greater than 0.
    private static double inputLength(Scanner sc, String prompt) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(prompt);
            String line = sc.nextLine();
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
    private static void inputTriangle(Scanner sc, ShapeRequestDTO dto) {
        // keep asking for three sides until they form a triangle
        while (true) {
            double sideA = inputLength(sc, Message.INPUT_SIDE_A);
            double sideB = inputLength(sc, Message.INPUT_SIDE_B);
            double sideC = inputLength(sc, Message.INPUT_SIDE_C);
            // an impossible triangle prints the reason and loops again
            try {
                Validation.checkTriangle(sideA, sideB, sideC);
                dto.setSideA(sideA);
                dto.setSideB(sideB);
                dto.setSideC(sideC);
                return;
            } catch (Exception e) {
                // "These three sides cannot form a triangle. Please input again."
                System.out.println(e.getMessage());
            }
        }
    }
}
