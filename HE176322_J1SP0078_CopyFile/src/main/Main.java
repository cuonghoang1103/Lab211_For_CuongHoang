package main;

import constants.Constants;
import constants.Message;
import controller.CopyController;
import dto.ConfigRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow - the menu loop. Every keyboard read, every validation and the
 * reading of config.properties happen here; the check and the copy then take one call of
 * the controller.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu until Exit, or until a config error stops the
    // program (the brief: "show error message and stop program").
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CopyController controller = new CopyController();
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until the program stops
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

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
            } catch (Exception e) {
                // one line of the brief's error box, then stop
                System.out.println(e.getMessage());
                System.out.println(Message.SYSTEM_SHUTDOWN);
                running = false;
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
                // "You must input a number." or "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Reads the three settings of the brief's form (box 2) into a new request.
    private static ConfigRequestDTO inputConfigForm(Scanner sc) {
        ConfigRequestDTO requestDTO = new ConfigRequestDTO();

        // the brief's form: its title, then the three settings
        System.out.println(Message.TITLE_INPUT);
        System.out.print(Message.INPUT_COPY_FOLDER);
        requestDTO.setCopyFolder(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_DATA_TYPE);
        requestDTO.setDataType(Validation.getText(sc.nextLine()));
        System.out.print(Message.INPUT_PATH);
        requestDTO.setPath(Validation.getText(sc.nextLine()));
        return requestDTO;
    }

    // Option 2, and box 2 of option 1: reads a new config and asks the controller to save
    // it into config.properties ("File Configure cannot create" when it cannot).
    private static void inputConfig(Scanner sc, CopyController controller) throws Exception {
        controller.createFileConfig(inputConfigForm(sc));
    }

    // Option 1: when config.properties is missing, the user types it and it is created
    // first (boxes 2 and 3); then main reads the file and calls the controller once to
    // check it and copy (boxes 4 and 5).
    private static void copyFile(Scanner sc, CopyController controller) throws Exception {
        ConfigRequestDTO requestDTO = new ConfigRequestDTO();

        // the brief: "If file config is not exist, prompt user to input file config"
        if (!FileUtils.isFile(Constants.CONFIG_FILE)) {
            System.out.println(Message.CONFIG_NOT_FOUND);
            inputConfig(sc, controller);
        }

        // box 4 works on the FILE ("perform next steps with existed file"), read here in
        // main (checklist 1.1): "Can't read File Configure" when it cannot be read
        System.out.println(Message.TITLE_CHECK);
        requestDTO.setLineList(FileUtils.readLines(Constants.CONFIG_FILE));
        controller.copyFile(requestDTO);
    }
}
