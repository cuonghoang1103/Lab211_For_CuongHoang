package main;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import constants.VehicleType;
import controller.VehicleController;
import dto.VehicleRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menus and the keyboard. Every keyboard read,
 * every validation and the reading of vehicles.txt happen here; each function of a menu
 * then calls the controller once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the main menu until the user types a number that is not a
    // function (the brief's "Others- Quit").
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VehicleController controller = new VehicleController();
        VehicleRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the main menu again after every function, until Quit
        while (running) {
            System.out.println(Message.MAIN_MENU);
            choice = inputMainChoice(sc);

            // any business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // Function 1: the lines of vehicles.txt, then load them
                    case Constants.MENU_LOAD:
                        requestDTO = readDataFile();
                        controller.loadData(requestDTO);
                        break;

                    // Function 2: the add sub menu (one call to the controller per vehicle)
                    case Constants.MENU_ADD:
                        addVehicles(sc, controller);
                        break;

                    // Function 3: the id (checked at once) and the new values, then update
                    case Constants.MENU_UPDATE:
                        requestDTO = inputUpdate(sc, controller);
                        controller.updateVehicle(requestDTO);
                        break;

                    // Function 4: the id and the answer to the confirm message, then delete
                    case Constants.MENU_DELETE:
                        requestDTO = inputDelete(sc);
                        controller.deleteVehicle(requestDTO);
                        break;

                    // Function 5: the search sub menu (one call to the controller per search)
                    case Constants.MENU_SEARCH:
                        searchVehicles(sc, controller);
                        break;

                    // Function 6: the show sub menu (one call to the controller per list)
                    case Constants.MENU_SHOW:
                        showVehicles(sc, controller);
                        break;

                    // Function 7: the title, then store the show room
                    case Constants.MENU_STORE:
                        System.out.println(Message.TITLE_STORE);
                        controller.storeData();
                        break;

                    // the brief's "Others- Quit": any other number stops the loop
                    default:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                }
            } catch (Exception e) {
                // the reason written in Message, like "Vehicle does not exist"
                System.out.println(e.getMessage());
            }
        }
    }

    // Function 1: the title, then every line of vehicles.txt (read through FileUtils) into
    // a new request; a missing file throws "Data file vehicles.txt does not exist.".
    private static VehicleRequestDTO readDataFile() throws Exception {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // the title of Function 1, then the lines of the file
        System.out.println(Message.TITLE_LOAD);
        requestDTO.setLineList(FileUtils.readLines(Constants.DATA_FILE));
        return requestDTO;
    }

    // Function 2: the add menu, one vehicle per round, until Back or N.
    private static void addVehicles(Scanner sc, VehicleController controller) {
        VehicleRequestDTO requestDTO = null;
        boolean adding = true;
        int choice = 0;

        // one vehicle per round
        while (adding) {
            System.out.println(Message.ADD_MENU);
            choice = inputSubChoice(sc);

            // Back to main menu
            if (choice == Constants.SUB_BACK) {
                return;
            }

            // every field of the kind chosen, then one call to the controller
            requestDTO = inputVehicle(sc, VehicleType.findByMenuChoice(choice));
            addVehicle(controller, requestDTO);

            // the brief: continue creating, or go back to the main menu
            adding = inputYesNo(sc, Message.ASK_ADD_ANOTHER);
        }
    }

    // Reads every field of a new vehicle: the title, the common ones, then those of its
    // kind.
    private static VehicleRequestDTO inputVehicle(Scanner sc, VehicleType vehicleType) {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // the title names the kind: "--- Add new Car ---"
        requestDTO.setVehicleType(vehicleType);
        System.out.println(String.format(Message.TITLE_ADD, vehicleType.getLabel()));

        // the common fields, each asked again at once until it is legal
        requestDTO.setId(inputText(sc, TextField.ID));
        requestDTO.setName(inputText(sc, TextField.NAME));
        requestDTO.setColor(inputText(sc, TextField.COLOR));
        requestDTO.setPrice(inputNumber(sc, NumberField.PRICE));
        requestDTO.setBrand(inputText(sc, TextField.BRAND));

        // each kind has its own questions
        switch (vehicleType) {
            // car: type, year
            case CAR:
                requestDTO.setCarType(inputText(sc, TextField.CAR_TYPE));
                requestDTO.setYearOfManufacture((int) inputNumber(sc, NumberField.YEAR));
                break;

            // motorbike: speed, license
            case MOTORBIKE:
                requestDTO.setSpeed(inputNumber(sc, NumberField.SPEED));
                requestDTO.setRequireLicense(inputYesNo(sc, Message.INPUT_LICENSE));
                break;

            // unreachable: the add menu only offers the two kinds
            default:
                break;
        }

        return requestDTO;
    }

    // Sends one new vehicle to the controller; a vehicle refused (its id is already used)
    // is reported and the add menu goes on.
    private static void addVehicle(VehicleController controller, VehicleRequestDTO requestDTO) {
        // the service may still refuse it
        try {
            controller.addVehicle(requestDTO);
        } catch (Exception e) {
            // show why the vehicle was refused
            System.out.println(e.getMessage());
        }
    }

    // Function 3: the title and the id, then the new values. The brief stops at once with
    // "Vehicle does not exist" when the id is unknown, so the id goes through a check-only
    // call to the controller (it throws, it prints nothing) before the other questions.
    private static VehicleRequestDTO inputUpdate(Scanner sc, VehicleController controller)
            throws Exception {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // the title of Function 3, then the id
        System.out.println(Message.TITLE_UPDATE);
        requestDTO.setId(inputText(sc, TextField.ID));

        // check only: an unknown id stops here; a known one tells which questions to ask
        requestDTO.setVehicleType(controller.findVehicleType(requestDTO));

        // the new values; a blank line keeps the old one
        System.out.println(Message.KEEP_HINT);
        inputChanges(sc, requestDTO);
        return requestDTO;
    }

    // Reads the new values of update; a blank line leaves the field null (= keep).
    private static void inputChanges(Scanner sc, VehicleRequestDTO requestDTO) {
        Double year = null;

        // the common fields
        requestDTO.setName(inputNewText(sc, TextField.NAME));
        requestDTO.setColor(inputNewText(sc, TextField.COLOR));
        requestDTO.setPrice(inputNewNumber(sc, NumberField.PRICE));
        requestDTO.setBrand(inputNewText(sc, TextField.BRAND));

        // each kind has its own questions
        switch (requestDTO.getVehicleType()) {
            // car: type, year
            case CAR:
                requestDTO.setCarType(inputNewText(sc, TextField.CAR_TYPE));
                year = inputNewNumber(sc, NumberField.YEAR);
                requestDTO.setYearOfManufacture((year == null) ? null : year.intValue());
                break;

            // motorbike: speed, license
            case MOTORBIKE:
                requestDTO.setSpeed(inputNewNumber(sc, NumberField.SPEED));
                requestDTO.setRequireLicense(inputNewYesNo(sc, Message.INPUT_NEW_LICENSE));
                break;

            // unreachable: every stored vehicle is one of the two kinds
            default:
                break;
        }
    }

    // Function 4: the title, the id and the brief's confirm message into a new request.
    private static VehicleRequestDTO inputDelete(Scanner sc) {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // the title of Function 4, then the id
        System.out.println(Message.TITLE_DELETE);
        requestDTO.setId(inputText(sc, TextField.ID));

        // the brief: "Before the delete system must show confirm message"
        requestDTO.setConfirmed(inputYesNo(sc, String.format(Message.ASK_DELETE,
                requestDTO.getId())));
        return requestDTO;
    }

    // Function 5: the search menu, until Back.
    private static void searchVehicles(Scanner sc, VehicleController controller) {
        boolean searching = true;
        int choice = 0;

        // one search per round
        while (searching) {
            System.out.println(Message.SEARCH_MENU);
            choice = inputSubChoice(sc);

            // 5.1 by name, 5.2 by id, 3 back to the main menu
            switch (choice) {
                // Function 5.1
                case Constants.SUB_FIRST:
                    searchByName(sc, controller);
                    break;

                // Function 5.2
                case Constants.SUB_SECOND:
                    searchById(sc, controller);
                    break;

                // Back to main menu (inputSubChoice only returns 1..3)
                default:
                    searching = false;
                    break;
            }
        }
    }

    // Function 5.1: the title and the text, then one call to the controller.
    private static void searchByName(Scanner sc, VehicleController controller) {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // the title of Function 5.1, then the text
        System.out.println(Message.TITLE_SEARCH_NAME);
        requestDTO.setKeyword(inputText(sc, TextField.KEYWORD));

        // "No vehicle found." is shown, the menu stays
        try {
            controller.searchByName(requestDTO);
        } catch (Exception e) {
            // show the reason
            System.out.println(e.getMessage());
        }
    }

    // Function 5.2: the title and the id, then one call to the controller.
    private static void searchById(Scanner sc, VehicleController controller) {
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // the title of Function 5.2, then the id
        System.out.println(Message.TITLE_SEARCH_ID);
        requestDTO.setId(inputText(sc, TextField.ID));

        // "Vehicle does not exist" is shown, the menu stays
        try {
            controller.searchById(requestDTO);
        } catch (Exception e) {
            // show the reason
            System.out.println(e.getMessage());
        }
    }

    // Function 6: the show menu, until Back.
    private static void showVehicles(Scanner sc, VehicleController controller) {
        boolean showing = true;
        int choice = 0;

        // one list per round
        while (showing) {
            System.out.println(Message.SHOW_MENU);
            choice = inputSubChoice(sc);

            // 6.1 all, 6.2 by price descending, 3 back to the main menu
            switch (choice) {
                // Function 6.1
                case Constants.SUB_FIRST:
                    showAll(controller);
                    break;

                // Function 6.2
                case Constants.SUB_SECOND:
                    showAllByPriceDescending(controller);
                    break;

                // Back to main menu (inputSubChoice only returns 1..3)
                default:
                    showing = false;
                    break;
            }
        }
    }

    // Function 6.1: the title, then one call to the controller.
    private static void showAll(VehicleController controller) {
        // the title of Function 6.1
        System.out.println(Message.TITLE_SHOW_ALL);

        // "The show room is empty." is shown, the menu stays
        try {
            controller.showAll();
        } catch (Exception e) {
            // show the reason
            System.out.println(e.getMessage());
        }
    }

    // Function 6.2: the title, then one call to the controller.
    private static void showAllByPriceDescending(VehicleController controller) {
        // the title of Function 6.2
        System.out.println(Message.TITLE_SHOW_PRICE);

        // "The show room is empty." is shown, the menu stays
        try {
            controller.showAllByPriceDescending();
        } catch (Exception e) {
            // show the reason
            System.out.println(e.getMessage());
        }
    }

    // Asks for the main menu choice until it is a whole number (a number that is not a
    // function means Quit).
    private static int inputMainChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a line that is not a number prints the reason and loops again
            try {
                return Validation.getInt(line);
            } catch (Exception e) {
                // "Please enter a number."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a sub menu choice until it is a number from 1 to 3.
    private static int inputSubChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN, Constants.SUB_BACK);
            } catch (Exception e) {
                // "Please choose from 1 to 3."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a text field until it matches its pattern.
    private static String inputText(Scanner sc, TextField field) {
        String line = "";

        // keep asking until the text is legal
        while (true) {
            System.out.print(field.getPrompt());
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the text is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            line = sc.nextLine();

            // blank: keep
            if (Validation.isBlank(line)) {
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
        String line = "";

        // keep asking until the number is legal
        while (true) {
            System.out.print(field.getPrompt());
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the number is blank or legal
        while (true) {
            System.out.print(field.getNewPrompt());
            line = sc.nextLine();

            // blank: keep
            if (Validation.isBlank(line)) {
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
        String line = "";

        // keep asking until the answer is Y or N
        while (true) {
            System.out.print(question);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the answer is blank, Y or N
        while (true) {
            System.out.print(question);
            line = sc.nextLine();

            // blank: keep
            if (Validation.isBlank(line)) {
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
