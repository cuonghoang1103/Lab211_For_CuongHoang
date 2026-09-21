package main;

import constants.Constants;
import constants.Message;
import controller.NormalizeController;
import dto.NormalizeRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop. Every keyboard read, every validation and the
 * reading of input.txt / output.txt happen here; each menu option then calls the
 * controller exactly once.
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
        NormalizeController controller = new NormalizeController();
        NormalizeRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // file not found / cannot read / cannot write is shown here
            try {
                // one option = one flow = one call of the controller
                switch (choice) {
                    // option 1: write the sample input.txt
                    case Constants.MENU_SAMPLE:
                        controller.createSample();
                        break;

                    // option 2, the brief's job: main reads input.txt, the controller
                    // normalizes it into output.txt
                    case Constants.MENU_NORMALIZE:
                        requestDTO = readFile(Constants.INPUT_FILE);
                        controller.normalizeFile(requestDTO);
                        break;

                    // option 3: main reads output.txt back from the disk, the view shows it
                    case Constants.MENU_SHOW_OUTPUT:
                        requestDTO = readFile(Constants.OUTPUT_FILE);
                        controller.showOutput(requestDTO);
                        break;

                    // option 4: normalize one typed line
                    case Constants.MENU_TYPED_LINE:
                        requestDTO = inputLine(sc);
                        controller.normalizeText(requestDTO);
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_EXIT, Constants.MENU_CASES);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Options 2 and 3: main reads the file (checklist 1.1) into a new request; FileUtils
    // throws "Error: File not found: input.txt", "Error: Not a file: ..." or "Error:
    // Cannot read the file: ...".
    private static NormalizeRequestDTO readFile(String path) throws Exception {
        NormalizeRequestDTO requestDTO = new NormalizeRequestDTO();

        // every line of the file, exactly as it is on the disk
        requestDTO.setLineList(FileUtils.readLines(path));
        return requestDTO;
    }

    // Option 4: the prompt, then one line exactly as typed (not trimmed) into a new request.
    private static NormalizeRequestDTO inputLine(Scanner sc) {
        NormalizeRequestDTO requestDTO = new NormalizeRequestDTO();

        // the prompt of option 4, then the line with its spaces kept
        System.out.println(Message.INPUT_LINE);
        requestDTO.setText(Validation.getRawText(sc.nextLine()));
        return requestDTO;
    }
}
