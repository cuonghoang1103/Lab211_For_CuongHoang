package main;

import constants.Constants;
import constants.Message;
import controller.DictionaryController;
import dto.WordRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: loads the dictionary file, then shows the menu until the user
    // chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DictionaryController controller = new DictionaryController();
        // an unreadable data file is reported, and the program starts empty
        try {
            controller.loadData();
        } catch (Exception e) {
            // "Can't read the dictionary file."
            System.out.println(e.getMessage());
        }
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: add a word
                    case Constants.MENU_ADD:
                        addWord(sc, controller);
                        break;
                    // option 2: delete a word
                    case Constants.MENU_DELETE:
                        removeWord(sc, controller);
                        break;
                    // option 3: translate a word
                    case Constants.MENU_TRANSLATE:
                        translate(sc, controller);
                        break;
                    // option 4: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 1..4
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the controller
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 4.
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
                // "You must input a number." or "Value must be between 1 and 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an English word until it is not blank and has no "=".
    private static String inputEnglish(Scanner sc) {
        // keep asking until the word is legal
        while (true) {
            System.out.print(Message.INPUT_ENGLISH);
            String line = sc.nextLine();
            // a blank word or a word with "=" prints the reason and loops again
            try {
                return Validation.getEnglish(line);
            } catch (Exception e) {
                // show why the word was refused
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a Vietnamese meaning until it is not blank.
    private static String inputVietnamese(Scanner sc) {
        // keep asking until the meaning is not blank
        while (true) {
            System.out.print(Message.INPUT_VIETNAMESE);
            String line = sc.nextLine();
            // a blank meaning prints the reason and loops again
            try {
                return Validation.getNonBlank(line, Message.VIETNAMESE_BLANK);
            } catch (Exception e) {
                // "Vietnamese word must not be empty."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks "update its meaning (Y/N)?" until the answer is Y or N.
    private static boolean inputYesNo(Scanner sc) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.ASK_UPDATE);
            String line = sc.nextLine();
            // any other answer prints the reason and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // "Please input Y or N."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads a pair of words, asks before replacing an existing meaning (the
    // brief's suggestion), and calls addWord once.
    private static void addWord(Scanner sc, DictionaryController controller)
            throws Exception {
        System.out.println(Message.TITLE_ADD);
        WordRequestDTO dto = new WordRequestDTO();
        dto.setEnglish(inputEnglish(sc));
        dto.setVietnamese(inputVietnamese(sc));
        // pre-check: the brief asks the user before changing a known word
        if (controller.checkExistWord(dto)) {
            dto.setOverwrite(inputYesNo(sc));
        }
        controller.addWord(dto);
    }

    // Option 2: reads an English word and asks the controller to remove it.
    private static void removeWord(Scanner sc, DictionaryController controller)
            throws Exception {
        System.out.println(Message.TITLE_DELETE);
        WordRequestDTO dto = new WordRequestDTO();
        dto.setEnglish(inputEnglish(sc));
        controller.removeWord(dto);
    }

    // Option 3: reads an English word and asks the controller to translate.
    private static void translate(Scanner sc, DictionaryController controller) {
        System.out.println(Message.TITLE_TRANSLATE);
        WordRequestDTO dto = new WordRequestDTO();
        dto.setEnglish(inputEnglish(sc));
        controller.translate(dto);
    }
}
