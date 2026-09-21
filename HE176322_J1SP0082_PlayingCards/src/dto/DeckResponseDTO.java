package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the deck as text, the
 * dealt hand as text, and how many cards are left.
 *
 * @author HE176322
 */
public class DeckResponseDTO {

    // The 52 cards of the new deck, in order.
    private ArrayList<String> cardList;

    // The cards dealt after shuffling, in the order they were dealt.
    private ArrayList<String> handList;

    // How many cards stay in the deck after dealing.
    private int remaining;

    // JavaBean constructor: an empty response, filled through the setters.
    public DeckResponseDTO() {
        cardList = new ArrayList<>();
        handList = new ArrayList<>();
    }

    // Returns the cards of the new deck.
    public ArrayList<String> getCardList() {
        return cardList;
    }

    // Sets the cards of the new deck.
    public void setCardList(ArrayList<String> cardList) {
        this.cardList = cardList;
    }

    // Returns the dealt cards.
    public ArrayList<String> getHandList() {
        return handList;
    }

    // Sets the dealt cards.
    public void setHandList(ArrayList<String> handList) {
        this.handList = handList;
    }

    // Returns how many cards are left.
    public int getRemaining() {
        return remaining;
    }

    // Sets how many cards are left.
    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
