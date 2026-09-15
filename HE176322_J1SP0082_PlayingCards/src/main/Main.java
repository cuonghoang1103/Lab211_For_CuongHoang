package main;

import controller.DeckController;

/**
 * MAIN: the brief's "small program to test your deck and card classes" - it calls the
 * controller once.
 *
 * @author HE176322
 */
public class Main {

    // Starts the program.
    public static void main(String[] args) {
        DeckController controller = new DeckController();
        // dealing is the only step that can fail (an empty deck)
        try {
            controller.showDeck();
        } catch (Exception e) {
            // "The deck is empty." - written in Message, thrown by the model
            System.out.println(e.getMessage());
        }
    }
}
