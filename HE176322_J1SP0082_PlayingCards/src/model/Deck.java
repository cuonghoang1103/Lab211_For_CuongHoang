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
    private ArrayList<Card> cardList;

    // JavaBean constructor that builds the full deck with two nested loops, exactly as
    // the brief asks: 4 suits x 13 ranks = 52 cards.
    public Deck() {
        cardList = new ArrayList<>();

        // outer loop: the four suits, Clubs first, stops after Spades
        for (Suit suit : Suit.values()) {
            // inner loop: the thirteen ranks of this suit, 2 up to Ace
            for (Rank rank : Rank.values()) {
                cardList.add(new Card(rank, suit));
            }
        }
    }

    // Returns how many cards are still in the deck.
    public int countCards() {
        return cardList.size();
    }

    // Returns the card at one position without removing it.
    public Card getCard(int index) {
        return cardList.get(index);
    }

    // Returns a COPY of the cards, bottom first.
    public ArrayList<Card> getCardList() {
        return new ArrayList<>(cardList);
    }

    // Exchanges the cards at two positions (used by the shuffle of the service).
    public void swap(int first, int second) {
        Card temp = cardList.get(first);

        // the card at first goes to second, and the one at second takes its place
        cardList.set(first, cardList.get(second));
        cardList.set(second, temp);
    }

    // The brief's optional deal(): removes and returns the top card.
    public Card deal() throws Exception {
        // nothing to deal: report it instead of crashing
        if (cardList.isEmpty()) {
            throw new Exception(Message.EMPTY_DECK);
        }

        // the top card is the last element: removing it moves no other card
        return cardList.remove(cardList.size() - 1);
    }
}
