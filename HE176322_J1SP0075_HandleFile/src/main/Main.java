package main;

import constants.Constants;
import constants.Message;
import controller.FileController;
import dto.FileRequestDTO;
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
        FileController controller = new FileController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any error of the chosen function is shown here
            try {
                // run the function the user picked
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
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // not a number, or not from 1 to 6
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a path until something exists there (options 3 and 4: the brief says "Path
    // doesn't exist", Please Try again).
    private static String inputExistPath(Scanner sc) {
        // keep asking until the path exists
        while (true) {
            System.out.print(Message.INPUT_PATH);
            String line = sc.nextLine();
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
        // keep asking until the size is a number
        while (true) {
            System.out.print(Message.INPUT_SIZE);
            String line = sc.nextLine();
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
        System.out.println(Message.TITLE_CHECK_PATH);
        FileRequestDTO dto = new FileRequestDTO();
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
        controller.checkPath(dto);
    }

    // Option 2: reads a directory path and lists its .java files.
    private static void getJavaFiles(Scanner sc, FileController controller)
            throws Exception {
        System.out.println(Message.TITLE_JAVA_FILES);
        FileRequestDTO dto = new FileRequestDTO();
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
        controller.getJavaFiles(dto);
    }

    // Option 3: reads n FIRST, then the directory (the brief's screen order).
    private static void getBigFiles(Scanner sc, FileController controller)
            throws Exception {
        System.out.println(Message.TITLE_BIG_FILES);
        FileRequestDTO dto = new FileRequestDTO();
        dto.setSize(inputSize(sc));
        dto.setPath(inputExistPath(sc));
        controller.getBigFiles(dto);
    }

    // Option 4: reads the content FIRST, then the file (the brief's screen).
    private static void appendContent(Scanner sc, FileController controller)
            throws Exception {
        System.out.println(Message.TITLE_APPEND);
        FileRequestDTO dto = new FileRequestDTO();
        System.out.print(Message.INPUT_CONTENT);
        dto.setContent(sc.nextLine());
        dto.setPath(inputExistPath(sc));
        controller.appendContent(dto);
    }

    // Option 5: reads a file path and shows its number of words.
    private static void countWords(Scanner sc, FileController controller)
            throws Exception {
        System.out.println(Message.TITLE_COUNT);
        FileRequestDTO dto = new FileRequestDTO();
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
        controller.countWords(dto);
    }
}
