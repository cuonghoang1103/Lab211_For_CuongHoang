package main;

import constants.Constants;
import constants.Message;
import controller.ZipController;
import dto.ZipRequestDTO;
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
        ZipController controller = new ZipController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // run the function the user picked
            switch (choice) {
                // option 1: zip a folder
                case Constants.MENU_COMPRESS:
                    compress(sc, controller);
                    break;
                // option 2: unzip a file
                case Constants.MENU_EXTRACT:
                    extract(sc, controller);
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

    // Asks for a menu choice until it is a number from 1 to 3.
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
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Shows a prompt and asks again until the answer is not blank.
    private static String inputValue(Scanner sc, String prompt) {
        // keep asking until the line is not blank
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            // a blank line prints "You must input a value." and loops again
            try {
                return Validation.getNonBlank(line);
            } catch (Exception e) {
                // show why the line was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads source folder, destination folder and zip name, then calls the
    // controller once.
    private static void compress(Scanner sc, ZipController controller) {
        System.out.println(Message.TITLE_COMPRESSION);
        ZipRequestDTO dto = new ZipRequestDTO();
        dto.setSourcePath(inputValue(sc, Message.INPUT_SOURCE_FOLDER));
        dto.setDestinationPath(inputValue(sc, Message.INPUT_DESTINATION));
        dto.setZipName(inputValue(sc, Message.INPUT_NAME));
        controller.compress(dto);
    }

    // Option 2: reads the zip file and the destination folder, then calls the controller
    // once.
    private static void extract(Scanner sc, ZipController controller) {
        System.out.println(Message.TITLE_EXTRACTION);
        ZipRequestDTO dto = new ZipRequestDTO();
        dto.setSourcePath(inputValue(sc, Message.INPUT_SOURCE_FILE));
        dto.setDestinationPath(inputValue(sc, Message.INPUT_DESTINATION));
        controller.extract(dto);
    }
}
