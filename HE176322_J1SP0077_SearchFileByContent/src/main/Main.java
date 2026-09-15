package main;

import constants.Constants;
import constants.Message;
import controller.WordController;
import dto.WordRequestDTO;
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
        WordController controller = new WordController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: count a word in a file
                    case Constants.MENU_COUNT_WORD:
                        System.out.println(Message.TITLE_COUNT_WORD);
                        controller.countWord(inputRequest(sc));
                        break;
                    // option 2: find the files containing a word
                    case Constants.MENU_FIND_FILE:
                        System.out.println(Message.TITLE_FIND_FILE);
                        controller.findFile(inputRequest(sc));
                        break;
                    // option 3: stop the loop
                    default:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the service
                System.out.println(e.getMessage());
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
                // not a number, or not from 1 to 3
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the path and the word - the same two prompts for both options.
    private static WordRequestDTO inputRequest(Scanner sc) {
        WordRequestDTO dto = new WordRequestDTO();
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_WORD);
        dto.setWord(Validation.getText(sc.nextLine()));
        return dto;
    }
}
