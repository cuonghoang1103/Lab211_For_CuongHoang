package main;

import constants.Constants;
import constants.Message;
import controller.CsvController;
import dto.CsvRequestDTO;
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
        CsvController controller = new CsvController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // any error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: import
                    case Constants.MENU_IMPORT:
                        importCSV(sc, controller);
                        break;
                    // option 2: format the addresses (nothing to type)
                    case Constants.MENU_FORMAT_ADDRESS:
                        System.out.println(Message.TITLE_FORMAT_ADDRESS);
                        controller.formatAddress();
                        break;
                    // option 3: format the names (nothing to type)
                    case Constants.MENU_FORMAT_NAME:
                        System.out.println(Message.TITLE_FORMAT_NAME);
                        controller.formatName();
                        break;
                    // option 4: export
                    case Constants.MENU_EXPORT:
                        exportCSV(sc, controller);
                        break;
                    // option 5: stop the loop
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

    // Asks for a menu choice until it is a number from 1 to 5.
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
                // not a number, or not from 1 to 5
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads the path of the CSV file and imports it.
    private static void importCSV(Scanner sc, CsvController controller)
            throws Exception {
        System.out.println(Message.TITLE_IMPORT);
        CsvRequestDTO dto = new CsvRequestDTO();
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
        controller.importCSV(dto);
    }

    // Option 4: reads the new file's path and exports.
    private static void exportCSV(Scanner sc, CsvController controller)
            throws Exception {
        System.out.println(Message.TITLE_EXPORT);
        CsvRequestDTO dto = new CsvRequestDTO();
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
        controller.exportCSV(dto);
    }
}
