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
 * MAIN: the work flow - the menu loop, the calculator loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CalculatorController controller = new CalculatorController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            // run the function the user picked
            switch (inputChoice(sc)) {
                // option 1: normal calculator
                case Constants.MENU_NORMAL:
                    normalCalculator(sc, controller);
                    break;
                // option 2: BMI calculator
                case Constants.MENU_BMI:
                    bmiCalculator(sc, controller);
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
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_NORMAL,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please input a number from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a number until it is numeric data.
    private static double inputNumber(Scanner sc) {
        // keep asking until checkin finds a number
        while (true) {
            System.out.print(Message.INPUT_NUMBER);
            String line = sc.nextLine();
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
        // keep asking until checkOperator finds an operator
        while (true) {
            System.out.print(Message.INPUT_OPERATOR);
            String line = sc.nextLine();
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
        // keep asking until the value is a positive number
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getBodyValue(line);
            } catch (Exception e) {
                // "BMI is digit"
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the memory calculator.
    private static void normalCalculator(Scanner sc, CalculatorController controller) {
        System.out.println(Message.TITLE_NORMAL);
        CalculatorRequestDTO dto = new CalculatorRequestDTO();
        dto.setNumber(inputNumber(sc));
        controller.startCalculation(dto);
        boolean calculating = true;
        // one step per operator, until "=" is typed
        while (calculating) {
            Operator operator = inputOperator(sc);
            // "=": show the result and leave the loop
            if (operator == Operator.EQUAL) {
                controller.showResult();
                calculating = false;
            } else {
                // any other operator needs a second number
                dto.setOperator(operator);
                dto.setNumber(inputNumber(sc));
                // division by zero must not end the calculation
                try {
                    controller.calculate(dto);
                } catch (ArithmeticException e) {
                    // "Can not divide by zero"; the memory keeps its value
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    // Option 2: reads weight and height and calls the controller once.
    private static void bmiCalculator(Scanner sc, CalculatorController controller) {
        System.out.println(Message.TITLE_BMI);
        BMIRequestDTO dto = new BMIRequestDTO();
        dto.setWeight(inputBodyValue(sc, Message.INPUT_WEIGHT));
        dto.setHeight(inputBodyValue(sc, Message.INPUT_HEIGHT));
        controller.calculateBMI(dto);
    }
}
