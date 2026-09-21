package repository;

import model.Deck;

/**
 * REPOSITORY: holds the data of the program - the deck of cards - and only simple CRUD on
 * it. No rule, no shuffle, no print.
 *
 * @author HE176322
 */
public class DeckRepository {

    // The deck the program works on: 52 cards when created, fewer after dealing.
    private Deck deck;

    // Creates the store with a new full deck (the Deck constructor builds the 52 cards).
    public DeckRepository() {
        deck = new Deck();
    }

    // Read: returns the stored deck itself, so shuffling and dealing change the stored deck.
    public Deck getDeck() {
        return deck;
    }
}
