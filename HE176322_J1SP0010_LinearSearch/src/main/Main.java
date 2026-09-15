package main;

import constants.Message;
import controller.SearchController;
import dto.SearchRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow - read the size and the search value, then call the controller
 * once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SearchController controller = new SearchController();
        SearchRequestDTO dto = new SearchRequestDTO();
        dto.setSize(inputSize(sc));
        dto.setSearchValue(inputSearchValue(sc));
        controller.searchArray(dto);
    }

    // Asks for the size of the array until the user types a legal number.
    private static int inputSize(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_SIZE);
            String line = sc.nextLine();
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
        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_SEARCH);
            String line = sc.nextLine();
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
