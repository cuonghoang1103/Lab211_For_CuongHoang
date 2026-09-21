package main;

import constants.Constants;
import constants.Message;
import constants.Operator;
import controller.CalculatorController;
import dto.BMIRequestDTO;
import dto.CalculatorRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop, the calculator loop and the keyboard. Every keyboard
 * read and every validation happen here; the controller only gets checked DTOs.
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
        CalculatorController controller = new CalculatorController();
        CalculatorRequestDTO calculatorRequestDTO = null;
        BMIRequestDTO bmiRequestDTO = null;
        boolean running = true;
        boolean calculating = false;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // run the function the user picked
            switch (choice) {
                // option 1: the first number goes into the temporary memory, then one flow
                // per operator (runStep) until "=" is typed
                case Constants.MENU_NORMAL:
                    calculatorRequestDTO = inputFirstNumber(sc);
                    controller.startCalculation(calculatorRequestDTO);
                    calculating = true;

                    // each turn: read the operator (and its number), then run its flow
                    while (calculating) {
                        calculatorRequestDTO = inputStep(sc);
                        calculating = runStep(controller, calculatorRequestDTO);
                    }
                    break;

                // option 2: read weight and height, then show the BMI number and status
                case Constants.MENU_BMI:
                    bmiRequestDTO = inputBMI(sc);
                    controller.calculateBMI(bmiRequestDTO);
                    break;

                // option 3: stop the loop
                case Constants.MENU_EXIT:
                    running = false;
                    break;

                // unreachable: inputChoice only returns 1..3
                default:
                    break;
            }
        }
    }

    // Asks for a menu choice until the user types 1, 2 or 3.
    private static int inputChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_NORMAL, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please input a number from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a number until it is numeric data.
    private static double inputNumber(Scanner sc) {
        String line = "";

        // keep asking until checkin finds a number
        while (true) {
            System.out.print(Message.INPUT_NUMBER);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getNumber(line);
            } catch (Exception e) {
                // "Number is digit"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an operator until it is one of + - * / ^ =.
    private static Operator inputOperator(Scanner sc) {
        String line = "";

        // keep asking until checkOperator finds an operator
        while (true) {
            System.out.print(Message.INPUT_OPERATOR);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getOperator(line);
            } catch (Exception e) {
                // "Please input (+, -, *, /, ^)"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a weight or height until it is a positive number.
    private static double inputBodyValue(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the value is a positive number
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getBodyValue(line);
            } catch (Exception e) {
                // "BMI is digit"
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1, start: the title, then the first number into a new request.
    private static CalculatorRequestDTO inputFirstNumber(Scanner sc) {
        CalculatorRequestDTO requestDTO = new CalculatorRequestDTO();

        // the title of the normal calculator, then the number the memory starts with
        System.out.println(Message.TITLE_NORMAL);
        requestDTO.setNumber(inputNumber(sc));
        return requestDTO;
    }

    // Option 1, one turn: the operator and, unless it is "=", the number it applies to -
    // into a new request.
    private static CalculatorRequestDTO inputStep(Scanner sc) {
        CalculatorRequestDTO requestDTO = new CalculatorRequestDTO();

        // the operator first: the brief's screen asks it before the number
        requestDTO.setOperator(inputOperator(sc));

        // + - * / ^ need a second number, "=" does not
        if (requestDTO.getOperator() != Operator.EQUAL) {
            requestDTO.setNumber(inputNumber(sc));
        }

        return requestDTO;
    }

    // Option 1, one flow chosen by the operator (the brief: "Use case switch to switch
    // (enum)"): one case, one call to the controller, one line printed. Answers false
    // after "=", which ends the calculator.
    private static boolean runStep(CalculatorController controller,
            CalculatorRequestDTO requestDTO) {
        boolean calculating = true;

        // the operator typed decides the flow
        switch (requestDTO.getOperator()) {
            // "=": "Result:" with the value in memory, then the calculator ends
            case EQUAL:
                controller.showResult();
                calculating = false;
                break;

            // + - * / ^: "memory operator number", then "Memory:"
            default:
                // division by zero must not end the calculation
                try {
                    controller.calculate(requestDTO);
                } catch (ArithmeticException e) {
                    // "Can not divide by zero"; the memory keeps its value
                    System.out.println(e.getMessage());
                }
                break;
        }

        return calculating;
    }

    // Option 2: the title, then weight and height into a new request.
    private static BMIRequestDTO inputBMI(Scanner sc) {
        BMIRequestDTO requestDTO = new BMIRequestDTO();

        // the title of the BMI calculator, then its two questions
        System.out.println(Message.TITLE_BMI);
        requestDTO.setWeight(inputBodyValue(sc, Message.INPUT_WEIGHT));
        requestDTO.setHeight(inputBodyValue(sc, Message.INPUT_HEIGHT));
        return requestDTO;
    }
}
