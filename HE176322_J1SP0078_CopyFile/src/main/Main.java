package main;

import constants.Constants;
import constants.Message;
import controller.CopyController;
import dto.ConfigRequestDTO;
import exceptions.ExceptionHandle;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until Exit, or until a config error stops the
    // program (the brief: "show error message and stop program").
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CopyController controller = new CopyController();
        boolean running = true;
        // show the menu again after every function, until the program stops
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // a config error ends the program with "System shutdown"
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: check the config and copy
                    case Constants.MENU_COPY:
                        copyFile(sc, controller);
                        break;
                    // option 2: type a new config file
                    case Constants.MENU_INPUT:
                        inputConfig(sc, controller);
                        break;
                    // option 3: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        break;
                    // unreachable: inputChoice only returns 1..3
                    default:
                        break;
                }
            } catch (ExceptionHandle e) {
                // one line of the brief's error box, then stop
                System.out.println(e.getMessage());
                System.out.println(Message.SYSTEM_SHUTDOWN);
                running = false;
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

    // Reads the three settings of the brief's form into the DTO.
    private static void readConfig(Scanner sc, ConfigRequestDTO dto) {
        System.out.println(Message.TITLE_INPUT);
        System.out.print(Message.INPUT_COPY_FOLDER);
        dto.setCopyFolder(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_DATA_TYPE);
        dto.setDataType(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_PATH);
        dto.setPath(Validation.getText(sc.nextLine()));
    }

    // Option 1: asks for the config when the file is missing, then calls the controller
    // once to check and copy.
    private static void copyFile(Scanner sc, CopyController controller)
            throws ExceptionHandle {
        ConfigRequestDTO dto = new ConfigRequestDTO();
        // the brief: no config file -> the user types it first
        if (!controller.isConfigExist()) {
            System.out.println(Message.CONFIG_NOT_FOUND);
            readConfig(sc, dto);
            dto.setNewConfig(true);
        }
        controller.copyFile(dto);
    }

    // Option 2: reads a new config and asks the controller to save it.
    private static void inputConfig(Scanner sc, CopyController controller)
            throws ExceptionHandle {
        ConfigRequestDTO dto = new ConfigRequestDTO();
        readConfig(sc, dto);
        controller.createFileConfig(dto);
    }
}
