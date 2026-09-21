package main;

import constants.Constants;
import constants.Message;
import controller.WordController;
import dto.WordRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop. Every keyboard read, every validation and the
 * reading of the files happen here; each menu option then calls the controller exactly
 * once.
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
        WordController controller = new WordController();
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any error of the chosen function is shown here
            try {
                // one option = one flow = one call of the controller
                switch (choice) {
                    // option 1: count a word in a file
                    case Constants.MENU_COUNT_WORD:
                        System.out.println(Message.TITLE_COUNT_WORD);
                        controller.countWord(inputCountWord(sc));
                        break;

                    // option 2: find the files containing a word
                    case Constants.MENU_FIND_FILE:
                        System.out.println(Message.TITLE_FIND_FILE);
                        controller.findFile(inputFindFile(sc));
                        break;

                    // option 3: stop the loop
                    default:
                        running = false;
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown below main
                System.out.println(e.getMessage());
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
                // not a number, or not from 1 to 3
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the path and the word - the same two prompts for both options - and refuses a
    // blank word.
    private static WordRequestDTO inputRequest(Scanner sc) throws Exception {
        WordRequestDTO requestDTO = new WordRequestDTO();

        // the two prompts of the brief's screen
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_WORD);
        requestDTO.setWord(Validation.getText(sc.nextLine()));

        // "Word must not be blank." comes before any file is touched
        Validation.checkWord(requestDTO.getWord());
        return requestDTO;
    }

    // Option 1: the path and the word, then the file itself (checklist 1.1: main reads the
    // files).
    private static WordRequestDTO inputCountWord(Scanner sc) throws Exception {
        WordRequestDTO requestDTO = inputRequest(sc);

        // FileUtils refuses a missing path or a folder, else gives every line of the file
        requestDTO.setLineList(FileUtils.readTextFile(requestDTO.getPath()));
        return requestDTO;
    }

    // Option 2: the path and the word, then every file directly inside the folder.
    private static WordRequestDTO inputFindFile(Scanner sc) throws Exception {
        WordRequestDTO requestDTO = inputRequest(sc);

        // FileUtils refuses a missing path or a file, else gives name -> lines of each file
        requestDTO.setFileLineMap(FileUtils.readFolder(requestDTO.getPath()));
        return requestDTO;
    }
}
