package service;

import constants.Constants;
import dto.DeckResponseDTO;
import java.util.ArrayList;
import java.util.Random;
import model.Card;
import model.Deck;

/**
 * Service: builds the deck, shuffles it and deals a hand.
 *
 * @author HE176322
 */
public class DeckService {

    // source of random positions for the shuffle
    private Random random = new Random();

    // create the 52 cards, shuffle, deal HAND_SIZE cards, pack it for the view
    public DeckResponseDTO createDeck() throws Exception {
        Deck deck = new Deck();
        DeckResponseDTO response = new DeckResponseDTO();
        response.setCards(toTexts(deck.getCards()));
        shuffle(deck);
        ArrayList<String> hand = new ArrayList<>();
        // take the top card HAND_SIZE times
        for (int i = 0; i < Constants.HAND_SIZE; i++) {
            hand.add(deck.deal().toString());
        }
        response.setHand(hand);
        response.setRemaining(deck.size());
        return response;
    }

    // Fisher-Yates shuffle: swap each card with a random card at or before it
    private void shuffle(Deck deck) {
        // from the top card down to index 1; index 0 has nothing left to swap
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            deck.swap(i, j);
        }
    }

    // one text per card, e.g. "Ace of Spades"
    private ArrayList<String> toTexts(ArrayList<Card> cards) {
        ArrayList<String> texts = new ArrayList<>();
        // convert every card in order
        for (Card card : cards) {
            texts.add(card.toString());
        }
        return texts;
    }
}
