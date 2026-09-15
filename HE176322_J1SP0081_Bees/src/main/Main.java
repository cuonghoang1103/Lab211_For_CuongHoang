package main;

import constants.Constants;
import constants.Message;
import controller.BeeController;
import java.util.Scanner;
import utils.Validation;

/**
 * MAIN: the work flow of the program - the menu loop and the keyboard.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program: shows the menu until the user chooses 0.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BeeController controller = new BeeController();
        boolean running = true;
        // show the menu again after every function, until Exit is chosen
        while (running) {
            System.out.println(Message.MENU);
            int choice = inputChoice(sc);
            // "There is no bee list yet. Choose 1 first." is shown here
            try {
                // run the function the user picked
                switch (choice) {
                    // option 1: create a new bee list
                    case Constants.MENU_CREATE:
                        controller.createBees();
                        break;
                    // option 2: attack the current bee list
                    case Constants.MENU_ATTACK:
                        controller.attackBees();
                        break;
                    // option 0: stop the loop
                    case Constants.MENU_EXIT:
                        running = false;
                        System.out.println(Message.GOODBYE);
                        break;
                    // unreachable: inputChoice only returns 0..2
                    default:
                        break;
                }
            } catch (Exception e) {
                // the message was written in Message and thrown by the service
                System.out.println(e.getMessage());
            }
        }
    }

    // Asks for a menu choice until the user types 0, 1 or 2.
    private static int inputChoice(Scanner sc) {
        // keep asking until Validation accepts the line
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String line = sc.nextLine();
            // a wrong line prints the reason and loops again
            try {
                return Validation.getChoice(line, Constants.MENU_EXIT,
                        Constants.MENU_ATTACK);
            } catch (Exception e) {
                // "You must input a number." or "Please choose from 0 to 2."
                System.out.println(e.getMessage());
            }
        }
    }
}
