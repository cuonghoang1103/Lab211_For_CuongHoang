package main;

import constants.Constants;
import constants.Message;
import controller.ArrayController;
import dto.ArrayRequestDTO;
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
        ArrayController controller = new ArrayController();
        boolean running = true;
        // back to the home screen after every option, until Exit
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // "please input first" from options 2 and 3 is shown here
            try {
                // run the option the user picked
                switch (choice) {
                    // option 1: input the elements
                    case Constants.MENU_INPUT:
                        inputArray(sc, controller);
                        break;
                    // option 2: sort ascending
                    case Constants.MENU_ASCENDING:
                        controller.sortAscending();
                        break;
                    // option 3: sort descending
                    case Constants.MENU_DESCENDING:
                        controller.sortDescending();
                        break;
                    // option 4: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        break;
                    // unreachable: inputChoice only returns 1..4
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown below main
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until it is a number from 1 to 4.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.println(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_MIN,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // "Please input number" or "Please choose from 1 to 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the length until it is a number from 1 to the cap.
    private static int inputLength(Scanner sc) {
        // keep asking until the length is legal
        while (true) {
            System.out.print(Message.INPUT_LENGTH);
            String line = sc.nextLine();
            // a wrong length prints the brief's message and loops again
            try {
                return Validation.getLength(line);
            } catch (Exception e) {
                // "Please input numberand number is greater than zero"
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for one element until it is an integer.
    private static int inputElement(Scanner sc, int position) {
        // keep asking until the element is an integer
        while (true) {
            System.out.print(String.format(Message.INPUT_ELEMENT, position));
            String line = sc.nextLine();
            // a wrong element prints the reason and loops again
            try {
                return Validation.getElement(line);
            } catch (Exception e) {
                // "Please input number"
                System.out.println(e.getMessage());
            }
        }
    }

    // Option 1: reads the length and the elements, then calls the controller once.
    private static void inputArray(Scanner sc, ArrayController controller) {
        System.out.println(Message.TITLE_INPUT);
        System.out.println(Message.TITLE_LENGTH);
        int[] elements = new int[inputLength(sc)];
        // ask for every element, numbered from 1
        for (int i = 0; i < elements.length; i++) {
            elements[i] = inputElement(sc, i + 1);
        }
        ArrayRequestDTO dto = new ArrayRequestDTO();
        dto.setElements(elements);
        controller.inputArray(dto);
    }
}
