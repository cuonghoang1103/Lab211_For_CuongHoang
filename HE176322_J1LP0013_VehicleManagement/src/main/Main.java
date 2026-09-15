package main;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import constants.VehicleType;
import controller.VehicleController;
import dto.VehicleRequestDTO;
import dto.VehicleResponseDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menus and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the main menu until the user chooses Quit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VehicleController controller = new VehicleController();
        boolean running = true;
        // show the main menu again after every function, until Quit
        while (running) {
            System.out.println(Message.MAIN_MENU);
            int choice = inputChoice(sc, Constants.MENU_QUIT);
            // any business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // Function 1
                    case Constants.MENU_LOAD:
                        System.out.println(Message.TITLE_LOAD);
                        controller.loadFromFile();
                        break;
                    // Function 2
                    case Constants.MENU_ADD:
                        addVehicles(sc, controller);
                        break;
                    // Function 3
                    case Constants.MENU_UPDATE:
                        updateVehicle(sc, controller);
                        break;
                    // Function 4
                    case Constants.MENU_DELETE:
                        deleteVehicle(sc, controller);
                        break;
                    // Function 5
                    case Constants.MENU_SEARCH:
                        searchVehicles(sc, controller);
                        break;
                    // Function 6
                    case Constants.MENU_SHOW:
                        showVehicles(sc, controller);
                        break;
                    // Function 7
                    case Constants.MENU_STORE:
                        System.out.println(Message.TITLE_STORE);
                        controller.storeToFile();
                        break;
                    // Quit (the loop stops even if storing fails)
                    case Constants.MENU_QUIT:
                        running = false;
                        quit(sc, controller);
                        break;
                    // unreachable: inputChoice only returns 1..8
                    default:
                        break;
                }
            } catch (Exception e) {
                // the reason written in Message, like "Vehicle does not exist"
                System.out.println(e.getMessage());
            }
        }
    }

    // Function 2: the add menu, one vehicle per round, until Back or N.
    private static void addVehicles(Scanner sc, VehicleController controller) {
        // one vehicle per round
        while (true) {
            System.out.println(Message.ADD_MENU);
            int choice = inputChoice(sc, Constants.SUB_BACK);
            // Back to main menu
            if (choice == Constants.SUB_BACK) {
                return;
            }
            VehicleRequestDTO dto = new VehicleRequestDTO();
            dto.setVehicleType(VehicleType.fromMenuChoice(choice));
            System.out.println(String.format(Message.TITLE_ADD, dto.getVehicleType().getLabel()));
            dto.setId(inputNewId(sc, controller));
            inputFields(sc, dto);
            submitVehicle(controller, dto);
            // the brief: continue creating, or go back to the main menu
            if (!inputYesNo(sc, Message.ASK_ADD_ANOTHER)) {
                return;
            }
        }
    }

    // Reads every field of a new vehicle: the common ones, then those of its kind.
    private static void inputFields(Scanner sc, VehicleRequestDTO dto) {
        dto.setName(inputText(sc, TextField.NAME));
        dto.setColor(inputText(sc, TextField.COLOR));
        dto.setPrice(inputNumber(sc, NumberField.PRICE));
        dto.setBrand(inputText(sc, TextField.BRAND));
        // each kind has its own questions
        switch (dto.getVehicleType()) {
            // car: type, year
            case CAR:
                dto.setCarType(inputText(sc, TextField.CAR_TYPE));
                dto.setYearOfManufacture((int) inputNumber(sc, NumberField.YEAR));
                break;
            // motorbike: speed, license
            case MOTORBIKE:
                dto.setSpeed(inputNumber(sc, NumberField.SPEED));
                dto.setRequireLicense(inputYesNo(sc, Message.INPUT_LICENSE));
                break;
            // unreachable: the add menu only offers the two kinds
            default:
                break;
        }
    }

    // Sends one new vehicle to the controller.
    private static void submitVehicle(VehicleController controller, VehicleRequestDTO dto) {
        // the service may still refuse it
        try {
            controller.addVehicle(dto);
        } catch (Exception e) {
            // show why the vehicle was refused
            System.out.println(e.getMessage());
        }
    }

    // Function 3: finds the vehicle, then reads the new values (blank keeps the old one).
    private static void updateVehicle(Scanner sc, VehicleController controller)
            throws Exception {
        System.out.println(Message.TITLE_UPDATE);
        VehicleRequestDTO dto = new VehicleRequestDTO();
        dto.setId(inputText(sc, TextField.ID));
        // stops here with "Vehicle does not exist" when the id is wrong
        VehicleResponseDTO found = controller.findVehicle(dto);
        dto.setVehicleType(found.getVehicleType());
        System.out.println(Message.KEEP_HINT);
        inputChanges(sc, dto);
        controller.updateVehicle(dto);
    }

    // Reads the new values of update; a blank line leaves the field null (= keep).
    private static void inputChanges(Scanner sc, VehicleRequestDTO dto) {
        dto.setName(inputNewText(sc, TextField.NAME));
        dto.setColor(inputNewText(sc, TextField.COLOR));
        dto.setPrice(inputNewNumber(sc, NumberField.PRICE));
        dto.setBrand(inputNewText(sc, TextField.BRAND));
        // each kind has its own questions
        switch (dto.getVehicleType()) {
            // car: type, year
            case CAR:
                dto.setCarType(inputNewText(sc, TextField.CAR_TYPE));
                Double year = inputNewNumber(sc, NumberField.YEAR);
                dto.setYearOfManufacture(year == null ? null : year.intValue());
                break;
            // motorbike: speed, license
            case MOTORBIKE:
                dto.setSpeed(inputNewNumber(sc, NumberField.SPEED));
                dto.setRequireLicense(inputNewYesNo(sc, Message.INPUT_NEW_LICENSE));
                break;
            // unreachable: every stored vehicle is one of the two kinds
            default:
                break;
        }
    }

    // Function 4: finds the vehicle, shows it, asks for confirmation, then deletes.
    private static void deleteVehicle(Scanner sc, VehicleController controller)
            throws Exception {
        System.out.println(Message.TITLE_DELETE);
        VehicleRequestDTO dto = new VehicleRequestDTO();
        dto.setId(inputText(sc, TextField.ID));
        controller.findVehicle(dto);
        // N: nothing is deleted
        if (!inputYesNo(sc, Message.ASK_DELETE)) {
            System.out.println(Message.DELETE_CANCELLED);
            return;
        }
        controller.deleteVehicle(dto);
    }

    // Function 5: the search menu, until Back.
    private static void searchVehicles(Scanner sc, VehicleController controller) {
        // one search per round
        while (true) {
            System.out.println(Message.SEARCH_MENU);
            int choice = inputChoice(sc, Constants.SUB_BACK);
            // Back to main menu
            if (choice == Constants.SUB_BACK) {
                return;
            }
            VehicleRequestDTO dto = new VehicleRequestDTO();
            // "No vehicle found." or "Vehicle does not exist" is shown, the menu stays
            try {
                // 5.1 by name, 5.2 by id
                if (choice == Constants.SUB_FIRST) {
                    System.out.println(Message.TITLE_SEARCH_NAME);
                    dto.setKeyword(inputText(sc, TextField.KEYWORD));
                    controller.searchByName(dto);
                } else {
                    // Function 5.2
                    System.out.println(Message.TITLE_SEARCH_ID);
                    dto.setId(inputText(sc, TextField.ID));
                    controller.findVehicle(dto);
                }
            } catch (Exception e) {
                // show the reason
                System.out.println(e.getMessage());
            }
        }
    }

    // Function 6: the show menu, until Back.
    private static void showVehicles(Scanner sc, VehicleController controller) {
        // one list per round
        while (true) {
            System.out.println(Message.SHOW_MENU);
            int choice = inputChoice(sc, Constants.SUB_BACK);
            // Back to main menu
            if (choice == Constants.SUB_BACK) {
                return;
            }
            // "The show room is empty." is shown, the menu stays
            try {
                // 6.1 all, 6.2 by price descending
                if (choice == Constants.SUB_FIRST) {
                    System.out.println(Message.TITLE_SHOW_ALL);
                    controller.showAll();
                } else {
                    // Function 6.2
                    System.out.println(Message.TITLE_SHOW_PRICE);
                    controller.showAllByPriceDescending();
                }
            } catch (Exception e) {
                // show the reason
                System.out.println(e.getMessage());
            }
        }
    }

    // Quit: offers to store when there are changes, then says goodbye.
    private static void quit(Scanner sc, VehicleController controller) throws Exception {
        // something would be lost: ask first
        if (controller.hasUnsavedChanges() && inputYesNo(sc, Message.ASK_STORE)) {
            System.out.println(Message.TITLE_STORE);
            controller.storeToFile();
        }
        System.out.println(Message.GOODBYE);
    }

    // Asks for a menu choice until it is a number from 1 to max.
    private static int inputChoice(Scanner sc, int max) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, max);
            } catch (Exception e) {
                // "Please choose from 1 to 8."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the id of a new vehicle until it has the right form and is not used.
    private static String inputNewId(Scanner sc, VehicleController controller) {
        VehicleRequestDTO dto = new VehicleRequestDTO();
        // keep asking until the id is free
        while (true) {
            dto.setId(inputText(sc, TextField.ID));
            // a taken id prints the reason and loops again
            try {
                controller.checkNewId(dto);
                return dto.getId();
            } catch (Exception e) {
                // "Vehicle ID C001 already exists."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text field until it matches its pattern.
    private static String inputText(Scanner sc, TextField field) {
        // keep asking until the text is legal
        while (true) {
            System.out.print(field.getPrompt());
            String line = sc.nextLine();
            // a wrong text prints the field's rule and loops again
            try {
                return Validation.checkText(line, field);
            } catch (Exception e) {
                // the field's own error message
                System.out.println(e.getMessage());
            }
        }
    }

    // Update: like inputText, but a blank line returns null (= keep the old value).
    private static String inputNewText(Scanner sc, TextField field) {
        // keep asking until the text is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            String line = sc.nextLine();
            // blank: keep
            if (line.trim().isEmpty()) {
                return null;
            }
            // a wrong text prints the field's rule and loops again
            try {
                return Validation.checkText(line, field);
            } catch (Exception e) {
                // the field's own error message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a number field until it is in its range.
    private static double inputNumber(Scanner sc, NumberField field) {
        // keep asking until the number is legal
        while (true) {
            System.out.print(field.getPrompt());
            String line = sc.nextLine();
            // a wrong number prints the field's rule and loops again
            try {
                return Validation.checkNumber(line, field);
            } catch (Exception e) {
                // the field's own error message
                System.out.println(e.getMessage());
            }
        }
    }

    // Update: like inputNumber, but a blank line returns null (= keep the old value).
    private static Double inputNewNumber(Scanner sc, NumberField field) {
        // keep asking until the number is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            String line = sc.nextLine();
            // blank: keep
            if (line.trim().isEmpty()) {
                return null;
            }
            // a wrong number prints the field's rule and loops again
            try {
                return Validation.checkNumber(line, field);
            } catch (Exception e) {
                // the field's own error message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a (Y/N) question until the answer is Y or N.
    private static boolean inputYesNo(Scanner sc, String question) {
        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(question);
            String line = sc.nextLine();
            // anything else prints "Please enter Y or N." and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }

    // Update: like inputYesNo, but a blank line returns null (= keep the old value).
    private static Boolean inputNewYesNo(Scanner sc, String question) {
        // keep asking until the answer is blank, Y or N
        while (true) {
            System.out.print(question);
            String line = sc.nextLine();
            // blank: keep
            if (line.trim().isEmpty()) {
                return null;
            }
            // anything else prints "Please enter Y or N." and loops again
            try {
                return Validation.getYesNo(line);
            } catch (Exception e) {
                // show which letters are allowed
                System.out.println(e.getMessage());
            }
        }
    }
}
