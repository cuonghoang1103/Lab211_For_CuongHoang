package main;

import constants.Message;
import controller.SearchController;
import dto.SearchRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow. The keyboard is read and checked here (the brief's Function 1);
 * the search (Function 2) is one call to the controller.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: reads a legal size and a search value, then calls the controller
    // once.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SearchController controller = new SearchController();
        SearchRequestDTO requestDTO = new SearchRequestDTO();

        // Function 1: the two numbers typed by the user, carried to the controller by the
        // request
        requestDTO.setSize(inputSize(sc));
        requestDTO.setSearchValue(inputSearchValue(sc));

        // Function 2: generate, search and display - the controller is called once
        controller.searchArray(requestDTO);
    }

    // Asks for the size of the array until the user types a legal number.
    private static int inputSize(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_SIZE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getSize(line);
            } catch (Exception e) {
                // "You must input a number." or the range message
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the value to search until the user types a whole number.
    private static int inputSearchValue(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_SEARCH);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getInt(line);
            } catch (Exception e) {
                // "You must input a number."
                System.out.println(e.getMessage());
            }
        }
    }
}
