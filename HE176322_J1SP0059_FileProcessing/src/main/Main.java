package main;

import constants.Constants;
import constants.Message;
import controller.FileController;
import dto.FileRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop. Every keyboard read, every validation
 * and every file reading happen here; each menu option then calls the controller once.
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

            // any file error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: find person info
                    case Constants.MENU_FIND:
                        findPerson(sc, controller);
                        break;

                    // option 2: copy the words into a new file
                    case Constants.MENU_COPY:
                        copyText(sc, controller);
                        break;

                    // option 3: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;

                    // unreachable: inputChoice only returns 1..3
                    default:
                        break;
                }
            } catch (Exception e) {
                // "Path doesn't exist", "Can't read file" or "Can't write file"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 3.
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

    // Shows a prompt and asks until the answer is not blank (a path or a file name).
    private static String inputText(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the text is not blank
        while (true) {
            System.out.print(prompt);
            line = sc.nextLine();

            // a blank line prints the reason and loops again
            try {
                return Validation.getNonBlank(line);
            } catch (Exception e) {
                // "You must input something."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the minimum money until it is a number not less than 0.
    private static double inputMoney(Scanner sc) {
        String line = "";

        // keep asking until the money is legal
        while (true) {
            System.out.print(Message.INPUT_MONEY);
            line = sc.nextLine();

            // letters or a negative number print the reason and loop again
            try {
                return Validation.getMoney(line);
            } catch (Exception e) {
                // "You must input a number." or "Money must not be less than 0."
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads the path and the minimum money, then the file itself, and calls the
    // controller once with all of it in the request.
    private static void findPerson(Scanner sc, FileController controller) throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        System.out.println(Message.TITLE_PERSON);
        requestDTO.setPath(inputText(sc, Message.INPUT_PATH));
        requestDTO.setMoney(inputMoney(sc));

        // the file is read here in main: "Path doesn't exist" / "Can't read file"
        requestDTO.setLineList(FileUtils.readLines(requestDTO.getPath()));
        controller.findPerson(requestDTO);
    }

    // Option 2: reads the source and the new file name, then the source file itself, and
    // calls the controller once with all of it in the request.
    private static void copyText(Scanner sc, FileController controller) throws Exception {
        FileRequestDTO requestDTO = new FileRequestDTO();

        System.out.println(Message.TITLE_COPY);
        requestDTO.setSource(inputText(sc, Message.INPUT_SOURCE));
        requestDTO.setDestination(inputText(sc, Message.INPUT_DESTINATION));

        // the source is read here in main: "Path doesn't exist" / "Can't read file"
        requestDTO.setLineList(FileUtils.readLines(requestDTO.getSource()));
        controller.copyText(requestDTO);
    }
}
