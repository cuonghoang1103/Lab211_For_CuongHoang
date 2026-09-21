package main;

import constants.Constants;
import constants.Message;
import controller.EquationController;
import dto.EquationRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop. Every keyboard read and every
 * validation happen here; each menu option then calls the controller exactly once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EquationController controller = new EquationController();
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // run the function the user picked
            switch (choice) {
                // option 1: ax + b = 0 - read A and B, then one call to the controller
                case Constants.MENU_SUPERLATIVE:
                    controller.calculateEquation(inputSuperlative(sc));
                    break;

                // option 2: ax^2 + bx + c = 0 - read A, B and C, then one call
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please input number" or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for one coefficient until the line is a number.
    private static float inputCoefficient(Scanner sc, String prompt) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // a wrong line prints "Please input number" and loops again
            try {
                return Validation.getFloat(line);
            } catch (Exception e) {
                // the brief's message
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: prints the title and reads A and B into the request.
    private static EquationRequestDTO inputSuperlative(Scanner sc) {
        EquationRequestDTO requestDTO = new EquationRequestDTO();

        // the title of the brief's first screen, then A and B
        System.out.println(Message.TITLE_SUPERLATIVE);
        requestDTO.setCoefficientA(inputCoefficient(sc, Message.INPUT_A));
        requestDTO.setCoefficientB(inputCoefficient(sc, Message.INPUT_B));
        return requestDTO;
    }

    // Option 2: prints the title and reads A, B and C into the request.
    private static EquationRequestDTO inputQuadratic(Scanner sc) {
        EquationRequestDTO requestDTO = new EquationRequestDTO();

        // the title of the brief's second screen, then A, B and C
        System.out.println(Message.TITLE_QUADRATIC);
        requestDTO.setCoefficientA(inputCoefficient(sc, Message.INPUT_A));
        requestDTO.setCoefficientB(inputCoefficient(sc, Message.INPUT_B));
        requestDTO.setCoefficientC(inputCoefficient(sc, Message.INPUT_C));
        return requestDTO;
    }
}
