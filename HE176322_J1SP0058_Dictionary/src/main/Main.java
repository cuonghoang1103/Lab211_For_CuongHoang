package main;

import constants.Constants;
import constants.Message;
import controller.DictionaryController;
import dto.WordRequestDTO;
import java.util.ArrayList;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - reading the data file, the menu loop and the
 * keyboard. Every keyboard read, every validation and the file reading happen here; each
 * menu option then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: loads the dictionary file, then shows the menu until the user
    // chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DictionaryController controller = new DictionaryController();
        WordRequestDTO requestDTO = new WordRequestDTO();
        boolean running = true;
        int choice = 0;

        // the brief's loadData: main reads the lines of the data file, the controller fills
        // the dictionary; an unreadable data file is reported, and the program starts empty
        try {
            requestDTO.setLineList(readDataFile());
            controller.loadData(requestDTO);
        } catch (Exception e) {
            // "Can't read the dictionary file."
            System.out.println(e.getMessage());
        }

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read a pair of words (and Y/N for a known word), then add it
                    case Constants.MENU_ADD:
                        requestDTO = inputAdd(sc, controller);
                        controller.addWord(requestDTO);
                        break;

                    // option 2: read an English word, then remove its pair
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.removeWord(requestDTO);
                        break;

                    // option 3: read an English word, then show its meaning
                    case Constants.MENU_TRANSLATE:
                        requestDTO = inputTranslate(sc);
                        controller.translate(requestDTO);
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

    // The file half of the brief's loadData (reading files happens in main): the lines of
    // the data file, or no line at all when the file does not exist yet.
    private static ArrayList<String> readDataFile() throws Exception {
        // the brief: no data file yet -> the dictionary starts empty
        if (!FileUtils.isFileExist(Constants.DATA_FILE)) {
            return new ArrayList<>();
        }

        return FileUtils.readLines(Constants.DATA_FILE);
    }

    // Asks for a menu choice until the user types a number from 1 to 4.
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
                // "You must input a number." or "Value must be between 1 and 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for an English word until it is not blank and has no "=".
    private static String inputEnglish(Scanner sc) {
        String line = "";

        // keep asking until the word is legal
        while (true) {
            System.out.print(Message.INPUT_ENGLISH);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the meaning is not blank
        while (true) {
            System.out.print(Message.INPUT_VIETNAMESE);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(Message.ASK_UPDATE);
            line = sc.nextLine();

            // any other answer prints the reason and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // "Please input Y or N."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: the title, then the pair of words into a new request. The brief asks the
    // user before changing a known word, so the English word goes through a check-only
    // call to the controller (it answers, it prints nothing) before the Y/N question.
    private static WordRequestDTO inputAdd(Scanner sc, DictionaryController controller) {
        WordRequestDTO requestDTO = new WordRequestDTO();

        // the title of the add form, then the English word and its meaning
        System.out.println(Message.TITLE_ADD);
        requestDTO.setEnglish(inputEnglish(sc));
        requestDTO.setVietnamese(inputVietnamese(sc));

        // check only: a known word needs the answer to "update its meaning (Y/N)?"
        if (controller.checkExistWord(requestDTO)) {
            requestDTO.setOverwrite(inputYesNo(sc));
        }

        return requestDTO;
    }

    // Option 2: the title, then the English word to remove into a new request.
    private static WordRequestDTO inputDelete(Scanner sc) {
        WordRequestDTO requestDTO = new WordRequestDTO();

        // the title of the delete form, then the English word
        System.out.println(Message.TITLE_DELETE);
        requestDTO.setEnglish(inputEnglish(sc));
        return requestDTO;
    }

    // Option 3: the title, then the English word to translate into a new request.
    private static WordRequestDTO inputTranslate(Scanner sc) {
        WordRequestDTO requestDTO = new WordRequestDTO();

        // the title of the translate form, then the English word
        System.out.println(Message.TITLE_TRANSLATE);
        requestDTO.setEnglish(inputEnglish(sc));
        return requestDTO;
    }
}
