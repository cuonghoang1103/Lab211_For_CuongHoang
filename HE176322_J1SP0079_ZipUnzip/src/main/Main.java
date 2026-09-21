package main;

import constants.Constants;
import constants.Message;
import controller.ZipController;
import dto.ZipRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop and the keyboard. Every keyboard read and every
 * validation happen here; each menu option then calls the controller once.
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
        ZipController controller = new ZipController();
        ZipRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // run the function the user picked: one call to the controller per option
            switch (choice) {
                // option 1: source, destination and zip name, then zip
                case Constants.MENU_COMPRESS:
                    requestDTO = inputCompress(sc);
                    controller.compress(requestDTO);
                    break;

                // option 2: zip file and destination, then unzip
                case Constants.MENU_EXTRACT:
                    requestDTO = inputExtract(sc);
                    controller.extract(requestDTO);
                    break;

                // option 3: stop the loop (the brief prints nothing)
                case Constants.MENU_EXIT:
                    running = false;
                    break;

                // unreachable: inputChoice only returns 1..3
                default:
                    break;
            }
        }
    }

    // Asks for a menu choice until it is a number from 1 to 3.
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
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Shows a prompt and asks again until the answer is not blank.
    private static String inputValue(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the line is not blank
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // a blank line prints "You must input a value." and loops again
            try {
                return Validation.getNonBlank(line);
            } catch (Exception e) {
                // show why the line was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, then source (a folder or one file), destination folder and zip
    // name into a new request.
    private static ZipRequestDTO inputCompress(Scanner sc) {
        ZipRequestDTO requestDTO = new ZipRequestDTO();

        // the title, then the three questions of the brief's screen
        System.out.println(Message.TITLE_COMPRESSION);
        requestDTO.setSourcePath(inputValue(sc, Message.INPUT_SOURCE_FOLDER));
        requestDTO.setDestinationPath(inputValue(sc, Message.INPUT_DESTINATION));
        requestDTO.setZipName(inputValue(sc, Message.INPUT_NAME));
        return requestDTO;
    }

    // Option 2: the title, then the zip file and the destination folder into a new request.
    private static ZipRequestDTO inputExtract(Scanner sc) {
        ZipRequestDTO requestDTO = new ZipRequestDTO();

        // the title, then the two questions of the brief's screen
        System.out.println(Message.TITLE_EXTRACTION);
        requestDTO.setSourcePath(inputValue(sc, Message.INPUT_SOURCE_FILE));
        requestDTO.setDestinationPath(inputValue(sc, Message.INPUT_DESTINATION));
        return requestDTO;
    }
}
