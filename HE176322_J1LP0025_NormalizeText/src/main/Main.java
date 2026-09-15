package main;

import constants.Constants;
import constants.Message;
import controller.NormalizeController;
import dto.NormalizeRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NormalizeController controller = new NormalizeController();
        boolean running = true;
        // show the menu again after every function, until Exit
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // file not found / cannot read / cannot write is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: write the sample input.txt
                    case Constants.MENU_SAMPLE:
                        controller.createSample();
                        break;
                    // option 2: the brief's job, input.txt -> output.txt
                    case Constants.MENU_NORMALIZE:
                        controller.normalizeFile();
                        break;
                    // option 3: show output.txt from the disk
                    case Constants.MENU_SHOW_OUTPUT:
                        controller.showOutput();
                        break;
                    // option 4: normalize one typed line
                    case Constants.MENU_TYPED_LINE:
                        normalizeTypedLine(sc, controller);
                        break;
                    // option 5: the rules on sample cases
                    case Constants.MENU_CASES:
                        controller.showCases();
                        break;
                    // option 0: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 0..5
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by FileUtils
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 0 to 5.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_EXIT,
                        Constants.MENU_CASES);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 4: reads one line exactly as typed and calls the controller once.
    private static void normalizeTypedLine(Scanner sc, NormalizeController controller) {
        System.out.println(Message.INPUT_LINE);
        NormalizeRequestDTO dto = new NormalizeRequestDTO();
        dto.setText(Validation.getRawText(sc.nextLine()));
        controller.normalizeText(dto);
    }
}
