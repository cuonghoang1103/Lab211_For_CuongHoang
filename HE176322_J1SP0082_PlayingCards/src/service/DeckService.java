package service;

import constants.Constants;
import dto.DeckResponseDTO;
import java.util.ArrayList;
import java.util.Random;
import model.Card;
import model.Deck;
import repository.DeckRepository;

/**
 * Service: takes the deck from the repository, lists it, shuffles it and deals a hand.
 *
 * @author HE176322
 */
public class DeckService {

    // source of random positions for the shuffle
    private Random random;

    // keeps the deck of cards (Service -> Repository -> Model)
    private DeckRepository deckRepository;

    // creates the service with its random source and its store
    public DeckService() {
        random = new Random();
        deckRepository = new DeckRepository();
    }

    // the brief's small test program: list the 52 cards, shuffle, deal HAND_SIZE cards,
    // pack it for the view
    public DeckResponseDTO testDeck() throws Exception {
        Deck deck = deckRepository.getDeck();
        DeckResponseDTO responseDTO = new DeckResponseDTO();
        ArrayList<String> handList = new ArrayList<>();

        // the 52 cards as text, taken BEFORE the shuffle (the brief's screen), then shuffle
        responseDTO.setCardList(toTextList(deck.getCardList()));
        shuffle(deck);

        // take the top card HAND_SIZE times
        for (int i = 0; i < Constants.HAND_SIZE; i++) {
            handList.add(deck.deal().toString());
        }

        // the dealt hand and what is left of the stored deck
        responseDTO.setHandList(handList);
        responseDTO.setRemaining(deck.countCards());
        return responseDTO;
    }

    // Fisher-Yates shuffle: swap each card with a random card at or before it
    private void shuffle(Deck deck) {
        int randomIndex = 0;

        // from the top card down to index 1; index 0 has nothing left to swap
        for (int i = deck.countCards() - 1; i > 0; i--) {
            randomIndex = random.nextInt(i + 1);
            deck.swap(i, randomIndex);
        }
    }

    // one text per card, e.g. "Ace of Spades"
    private ArrayList<String> toTextList(ArrayList<Card> cardList) {
        ArrayList<String> textList = new ArrayList<>();

        // convert every card in order
        for (Card card : cardList) {
            textList.add(card.toString());
        }

        return textList;
    }
}
