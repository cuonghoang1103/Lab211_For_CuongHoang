package model;

import constants.Message;
import constants.Rank;
import constants.Suit;
import java.util.ArrayList;

/**
 * MODEL: a full deck of 52 cards and what a deck itself can do (count its cards, give
 * one, swap two, deal the top one).
 *
 * @author HE176322
 */
public class Deck {

    // The cards; index 0 is the bottom, the last element is the top.
    private ArrayList<Card> cards;

    // JavaBean constructor that builds the full deck with two nested loops, exactly as
    // the brief asks: 4 suits x 13 ranks = 52 cards.
    public Deck() {
        cards = new ArrayList<>();
        // outer loop: the four suits, Clubs first, stops after Spades
        for (Suit suit : Suit.values()) {
            // inner loop: the thirteen ranks of this suit, 2 up to Ace
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Returns how many cards are still in the deck.
    public int size() {
        return cards.size();
    }

    // Returns the card at one position without removing it.
    public Card getCard(int index) {
        return cards.get(index);
    }

    // Returns a COPY of the cards, bottom first.
    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    // Exchanges the cards at two positions (used by the shuffle strategy).
    public void swap(int first, int second) {
        Card temp = cards.get(first);
        cards.set(first, cards.get(second));
        cards.set(second, temp);
    }

    // The brief's optional deal(): removes and returns the top card.
    public Card deal() throws Exception {
        // nothing to deal: report it instead of crashing
        if (cards.isEmpty()) {
            throw new Exception(Message.EMPTY_DECK);
        }
        return cards.remove(cards.size() - 1);
    }
}
