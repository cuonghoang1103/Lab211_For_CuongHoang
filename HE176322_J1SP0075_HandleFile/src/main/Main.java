package main;

import constants.Constants;
import constants.Message;
import controller.FileController;
import dto.FileRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop. Every keyboard read, every validation and the
 * reading of the text file happen here; each menu option then calls the controller exactly
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
        FileController controller = new FileController();
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
                    // option 1: check a path
                    case Constants.MENU_CHECK_PATH:
                        checkPath(sc, controller);
                        break;

                    // option 2: list the .java files
                    case Constants.MENU_JAVA_FILES:
                        getJavaFiles(sc, controller);
                        break;

                    // option 3: list the files bigger than n KB
                    case Constants.MENU_BIG_FILES:
                        getBigFiles(sc, controller);
                        break;

                    // option 4: add content to a file
                    case Constants.MENU_APPEND:
                        appendContent(sc, controller);
                        break;

                    // option 5: count the words of a file
                    case Constants.MENU_COUNT:
                        countWords(sc, controller);
                        break;

                    // option 6: stop the loop
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

    // Asks for a menu choice until it is a number from 1 to 6.
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
                // not a number, or not from 1 to 6
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a path until something exists there (options 3 and 4: the brief says "Path
    // doesn't exist", Please Try again).
    private static String inputExistPath(Scanner sc) {
        String line = "";

        // keep asking until the path exists
        while (true) {
            System.out.print(Message.INPUT_PATH);
            line = sc.nextLine();

            // a missing path prints "Path doesn't exist" and loops again
            try {
                return Validation.getExistPath(line);
            } catch (Exception e) {
                // the brief's message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the size n until it is a number.
    private static int inputSize(Scanner sc) {
        String line = "";

        // keep asking until the size is a number
        while (true) {
            System.out.print(Message.INPUT_SIZE);
            line = sc.nextLine();

            // "a" prints "Value of size is digit" and loops again
            try {
                return Validation.getSize(line);
            } catch (Exception e) {
                // the brief's message
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads a path and asks what it is.
    private static void checkPath(Scanner sc, FileController controller) {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the title, then the path to check
        System.out.println(Message.TITLE_CHECK_PATH);
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));
        controller.checkPath(requestDTO);
    }

    // Option 2: reads a directory path and lists its .java files.
    private static void getJavaFiles(Scanner sc, FileController controller)
            throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the title, then the directory to search
        System.out.println(Message.TITLE_JAVA_FILES);
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));
        controller.getJavaFiles(requestDTO);
    }

    // Option 3: reads n FIRST, then the directory (the brief's screen order).
    private static void getBigFiles(Scanner sc, FileController controller)
            throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the title, then n and an existing directory, each asked again until it is right
        System.out.println(Message.TITLE_BIG_FILES);
        requestDTO.setSize(inputSize(sc));
        requestDTO.setPath(inputExistPath(sc));
        controller.getBigFiles(requestDTO);
    }

    // Option 4: reads the content FIRST, then the file (the brief's screen).
    private static void appendContent(Scanner sc, FileController controller)
            throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the title, the content, then an existing file (asked again until it exists)
        System.out.println(Message.TITLE_APPEND);
        System.out.print(Message.INPUT_CONTENT);
        requestDTO.setContent(sc.nextLine());
        requestDTO.setPath(inputExistPath(sc));
        controller.appendContent(requestDTO);
    }

    // Option 5: reads a file path, then the file itself (checklist 1.1: main reads the
    // files), and shows its number of words.
    private static void countWords(Scanner sc, FileController controller)
            throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        // the title, then the path of the text file
        System.out.println(Message.TITLE_COUNT);
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));

        // FileUtils refuses a missing path ("Path doesn't exist") or a folder ("Cannot read
        // file"), else gives every line of the file
        requestDTO.setLineList(FileUtils.readLines(requestDTO.getPath()));
        controller.countWords(requestDTO);
    }
}
