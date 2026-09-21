package main;

import constants.Constants;
import constants.Message;
import controller.CountryController;
import dto.CountryRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every validation happen here; each menu option then calls the controller once.
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
        CountryController controller = new CountryController();
        CountryRequestDTO requestDTO = null;
        boolean running = true;
        int choice = 0;

        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            choice = inputChoice(sc);

            // a business error of the chosen function is shown here
            try {
                // run the function the user picked: one call to the controller per option
                switch (choice) {
                    // option 1: read one country (refused at once when the list is full),
                    // then store it
                    case Constants.MENU_INPUT:
                        requestDTO = inputCountry(sc, controller);
                        controller.addCountryInformation(requestDTO);
                        break;

                    // option 2: the country entered last
                    case Constants.MENU_RECENT:
                        controller.getRecentlyEnteredInformation();
                        break;

                    // option 3: read the name, then search by it
                    case Constants.MENU_SEARCH:
                        requestDTO = inputSearch(sc);
                        controller.searchInformationByName(requestDTO);
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
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_INPUT, Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please choose an option from 1 to 5."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks a question until the answer is not blank.
    private static String inputText(Scanner sc, String prompt) {
        String line = "";

        // keep asking until the answer is not blank
        while (true) {
            System.out.println(prompt);
            line = sc.nextLine();

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
        String line = "";

        // keep asking until the area is legal
        while (true) {
            System.out.println(Message.INPUT_AREA);
            line = sc.nextLine();

            // a letter or a non-positive number prints the reason and loops
            try {
                return Validation.getTotalArea(line);
            } catch (Exception e) {
                // "You must input a number." or the "greater than 0" message
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads the four answers of one country into a new request. When the list is
    // already full it stops BEFORE the first question - a check-only call to the controller
    // (it throws, it prints nothing), so nobody types four answers for nothing.
    private static CountryRequestDTO inputCountry(Scanner sc, CountryController controller)
            throws Exception {
        CountryRequestDTO requestDTO = new CountryRequestDTO();

        // check only: "The list already has 11 countries." is thrown here when it is full
        controller.checkFull();

        // the brief's four questions, each asked again until the answer is valid
        requestDTO.setCountryCode(inputText(sc, Message.INPUT_CODE));
        requestDTO.setCountryName(inputText(sc, Message.INPUT_NAME));
        requestDTO.setTotalArea(inputArea(sc));
        requestDTO.setCountryTerrain(inputText(sc, Message.INPUT_TERRAIN));
        return requestDTO;
    }

    // Option 3: reads the name to search into a new request.
    private static CountryRequestDTO inputSearch(Scanner sc) {
        CountryRequestDTO requestDTO = new CountryRequestDTO();

        // the name is asked again until it is not blank
        requestDTO.setSearchName(inputText(sc, Message.INPUT_SEARCH));
        return requestDTO;
    }
}
