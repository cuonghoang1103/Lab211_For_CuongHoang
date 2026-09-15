package main;

import constants.Constants;
import constants.Message;
import controller.EquationController;
import dto.EquationRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EquationController controller = new EquationController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // run the function the user picked
            switch (choice) {
                // option 1: ax + b = 0
                case Constants.MENU_SUPERLATIVE:
                    controller.calculateEquation(inputSuperlative(sc));
                    break;
                // option 2: ax^2 + bx + c = 0
                case Constants.MENU_QUADRATIC:
                    controller.calculateQuadraticEquation(inputQuadratic(sc));
                    break;
                // option 3: stop the loop
                case Constants.MENU_EXIT:
                    running = false;
                    System.out.println(Message.GOODBYE);
                    break;
                // unreachable: inputChoice only returns 1..3
                default:
                    break;
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 3.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please input number" or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for one coefficient until the line is a number.
    private static float inputCoefficient(Scanner sc, String prompt) {
        // keep asking until checkin accepts the line
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // a wrong line prints "Please input number" and loops again
            try {
                return Validation.checkin(line);
            } catch (Exception e) {
                // the brief's message
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: prints the title and reads A and B.
    private static EquationRequestDTO inputSuperlative(Scanner sc) {
        System.out.println(Message.TITLE_SUPERLATIVE);
        EquationRequestDTO dto = new EquationRequestDTO();
        dto.setA(inputCoefficient(sc, Message.INPUT_A));
        dto.setB(inputCoefficient(sc, Message.INPUT_B));
        return dto;
    }

    // Option 2: prints the title and reads A, B and C.
    private static EquationRequestDTO inputQuadratic(Scanner sc) {
        System.out.println(Message.TITLE_QUADRATIC);
        EquationRequestDTO dto = new EquationRequestDTO();
        dto.setA(inputCoefficient(sc, Message.INPUT_A));
        dto.setB(inputCoefficient(sc, Message.INPUT_B));
        dto.setC(inputCoefficient(sc, Message.INPUT_C));
        return dto;
    }
}
