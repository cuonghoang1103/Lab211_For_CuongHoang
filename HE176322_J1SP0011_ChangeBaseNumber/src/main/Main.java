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
 * (choice 0). Every keyboard read and every validation happen here; each round then calls
 * the controller exactly once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: one conversion per round, until 0 is chosen.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConvertController controller = new ConvertController();
        ConvertRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // one conversion per round, until the user chooses 0
        while (running) {
            System.out.println(Message.MENU);
            choice = inputBaseIn(sc);

            // 0: close the program
            if (choice == Constants.MENU_EXIT) {
                System.out.println(Message.GOODBYE);
                running = false;
            } else {
                // a base was chosen: the output base and the value go into a new request
                requestDTO = inputRequest(sc, choice);

                // digits wrong for the base, or a value too big: the reason is printed and
                // the menu comes back
                try {
                    Validation.checkValue(requestDTO.getValue(), requestDTO.getInputBase());
                    controller.convert(requestDTO);
                } catch (Exception e) {
                    // "1G is not a valid HEX number." or "too big"
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    // Asks for the input base until the user types 0..3.
    private static int inputBaseIn(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_BASE_IN);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_EXIT, Constants.BASE_MAX);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the rest of one conversion into a new request: the input base already chosen,
    // then the output base and the value.
    private static ConvertRequestDTO inputRequest(Scanner sc, int inputChoice) {
        ConvertRequestDTO requestDTO = new ConvertRequestDTO();

        // the bases as the brief numbers them (1 binary, 2 decimal, 3 hexadecimal)
        requestDTO.setInputBase(Base.findByChoice(inputChoice));
        requestDTO.setOutputBase(Base.findByChoice(inputBaseOut(sc)));

        // then the value, asked again until it is not empty
        requestDTO.setValue(inputValue(sc));
        return requestDTO;
    }

    // Asks for the output base until the user types 1..3.
    private static int inputBaseOut(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_BASE_OUT);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.BASE_MIN, Constants.BASE_MAX);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the value to convert until it is not empty.
    private static String inputValue(Scanner sc) {
        String line = "";

        // keep asking until the line is not empty
        while (true) {
            System.out.print(Message.INPUT_VALUE);
            line = sc.nextLine();

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
