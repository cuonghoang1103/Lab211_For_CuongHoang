package main;

import constants.Constants;
import constants.Message;
import controller.CountryController;
import dto.CountryRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses Exit.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CountryController controller = new CountryController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // a business error of the chosen function is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: input one country
                    case Constants.MENU_INPUT:
                        inputCountry(sc, controller);
                        break;
                    // option 2: the country entered last
                    case Constants.MENU_RECENT:
                        controller.getRecentlyEnteredInformation();
                        break;
                    // option 3: search by name
                    case Constants.MENU_SEARCH:
                        searchCountry(sc, controller);
                        break;
                    // option 4: every country sorted by name
                    case Constants.MENU_SORT:
                        controller.sortInformationByAscendingOrder();
                        break;
                    // option 5: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        break;
                    // unreachable: inputChoice only returns 1..5
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown below main
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 1 to 5.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_INPUT,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please choose an option from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a question until the answer is not blank.
    private static String inputText(Scanner sc, String prompt) {
        // keep asking until the answer is not blank
        while (true) {
            System.out.println(prompt);
            String line = sc.nextLine();
            // a blank line prints the reason and loops again
            try {
                return Validation.getNonBlank(line);
            } catch (Exception e) {
                // "This field must not be blank."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the total area until it is a number greater than 0.
    private static float inputArea(Scanner sc) {
        // keep asking until the area is legal
        while (true) {
            System.out.println(Message.INPUT_AREA);
            String line = sc.nextLine();
            // a letter or a non-positive number prints the reason and loops
            try {
                return Validation.getTotalArea(line);
            } catch (Exception e) {
                // "You must input a number." or the "greater than 0" message
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: refuses at once when the list is full, otherwise reads one country and
    // calls the controller to store it.
    private static void inputCountry(Scanner sc, CountryController controller)
            throws Exception {
        // pre-check: do not ask four questions when there is no room
        controller.checkFull();
        CountryRequestDTO dto = new CountryRequestDTO();
        dto.setCountryCode(inputText(sc, Message.INPUT_CODE));
        dto.setCountryName(inputText(sc, Message.INPUT_NAME));
        dto.setTotalArea(inputArea(sc));
        dto.setCountryTerrain(inputText(sc, Message.INPUT_TERRAIN));
        controller.addCountryInformation(dto);
    }

    // Option 3: reads the name and asks the controller to search.
    private static void searchCountry(Scanner sc, CountryController controller)
            throws Exception {
        CountryRequestDTO dto = new CountryRequestDTO();
        dto.setSearchName(inputText(sc, Message.INPUT_SEARCH));
        controller.searchInformationByName(dto);
    }
}
