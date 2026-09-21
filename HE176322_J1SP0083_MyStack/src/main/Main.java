package main;

import constants.Constants;
import constants.Message;
import controller.StackController;
import dto.StackRequestDTO;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard. Every keyboard read
 * and every validation happen here; each menu option calls the controller exactly once.
 *
 * @author HE176322
 */
public final class Main {

    // Private constructor: Main only has static methods (checklist 3.4).
    private Main() {
    }

    // Starts the program: shows the menu once (as in the brief's sample run), then keeps
    // asking for a choice until the user chooses 0.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StackController controller = new StackController();
        boolean running = true;
        int choice = 0;

        // the menu is printed once, before the first choice
        System.out.println(Message.MENU);

        // one choice per turn, until Exit is chosen
        while (running) {
            choice = inputChoice(sc);

            // "Stack is empty." thrown by pop/get is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: push a value
                    case Constants.MENU_PUSH:
                        push(sc, controller);
                        break;

                    // option 2: pop the top value
                    case Constants.MENU_POP:
                        controller.pop();
                        break;

                    // option 3: get (peek) the top value
                    case Constants.MENU_GET:
                        controller.get();
                        break;

                    // option 4: display the whole stack
                    case Constants.MENU_DISPLAY:
                        controller.displayStack();
                        break;

                    // option 0: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;

                    // unreachable: inputChoice only returns 0..4
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the model
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types a number from 0 to 4.
    private static int inputChoice(Scanner sc) {
        String line = "";

        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            line = sc.nextLine();

            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_EXIT, Constants.MENU_DISPLAY);
            } catch (Exception e) {
                // "You must input a number." or "Value must be between 0 and 4."
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for the value to push until it is a whole number.
    private static int inputValue(Scanner sc) {
        String line = "";

        // keep asking until the value is a whole number
        while (true) {
            System.out.print(Message.INPUT_VALUE);
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

    // Option 1: reads the value into the request, then calls the controller once.
    private static void push(Scanner sc, StackController controller) {
        StackRequestDTO requestDTO = new StackRequestDTO();

        // the value is checked here, in main, before the controller sees it
        requestDTO.setValue(inputValue(sc));
        controller.push(requestDTO);
    }
}
