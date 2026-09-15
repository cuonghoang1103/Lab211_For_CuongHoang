package main;

import constants.Base;
import constants.Constants;
import constants.Message;
import controller.ConvertController;
import dto.ConvertRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop that repeats "until users close the program"
 * (choice 0), and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: one conversion per round, until 0 is chosen.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConvertController controller = new ConvertController();
        boolean running = true;
        // one conversion per round, until the user chooses 0
        while (running) {
            System.out.println(Message.MENU);
            int input = inputBaseIn(sc);
            // 0: close the program
            if (input == Constants.MENU_EXIT) {
                System.out.println(Message.GOODBYE);
                running = false;
            } else {
                // a base was chosen: read the rest and convert
                ConvertRequestDTO dto = new ConvertRequestDTO();
                dto.setInputBase(Base.fromChoice(input));
                dto.setOutputBase(Base.fromChoice(inputBaseOut(sc)));
                dto.setValue(inputValue(sc));
                // a wrong digit is reported, then the menu comes back
                try {
                    controller.convert(dto);
                } catch (Exception e) {
                    // "1G is not a valid HEX number." or "too big"
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    // Asks for the input base until the user types 0..3.
    private static int inputBaseIn(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_BASE_IN);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_EXIT,
                        Constants.BASE_MAX);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the output base until the user types 1..3.
    private static int inputBaseOut(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_BASE_OUT);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.BASE_MIN,
                        Constants.BASE_MAX);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the value to convert until it is not empty.
    private static String inputValue(Scanner sc) {
        // keep asking until the line is not empty
        while (true) {
            System.out.print(Message.INPUT_VALUE);
            String line = sc.nextLine();
            // an empty line prints the reason and loops again
            try {
                return Validation.getValue(line);
            } catch (Exception e) {
                // "You must input something."
                System.out.println(e.getMessage());
            }
        }
    }
}
