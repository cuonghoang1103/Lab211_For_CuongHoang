package main;

import constants.Constants;
import constants.Message;
import controller.CsvController;
import dto.CsvRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow. Every keyboard read, every validation and the reading of the CSV
 * file happen here; each menu option then calls the controller exactly once.
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
        CsvController controller = new CsvController();
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // any error of the chosen function is printed here, then the menu comes back
            try {
                // one option = one flow = one call of the controller
                switch (choice) {
                    // option 1: main reads the file, the controller keeps its lines
                    case Constants.MENU_IMPORT:
                        System.out.println(Message.TITLE_IMPORT);
                        controller.importCSV(inputImport(sc));
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

                    // option 4: ask for the new file's path, then export
                    case Constants.MENU_EXPORT:
                        System.out.println(Message.TITLE_EXPORT);
                        controller.exportCSV(inputExport(sc));
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // not a number, or not from 1 to 5
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: asks for the path of the CSV file, then reads it - the brief's "Check
    // file exist or not" included - into the request.
    private static CsvRequestDTO inputImport(Scanner sc) throws Exception {
        CsvRequestDTO requestDTO = new CsvRequestDTO();

        // the path of the file to import
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));

        // FileUtils refuses a missing file or a folder, else gives every line of it
        requestDTO.setLineList(FileUtils.readLines(requestDTO.getPath()));
        return requestDTO;
    }

    // Option 4: asks for the path of the new file.
    private static CsvRequestDTO inputExport(Scanner sc) {
        CsvRequestDTO requestDTO = new CsvRequestDTO();

        // the path the data is exported to
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));
        return requestDTO;
    }
}
