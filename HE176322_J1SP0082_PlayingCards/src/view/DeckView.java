package view;

import constants.Message;
import dto.DeckResponseDTO;

/**
 * VIEW: prints the deck, then the dealt hand.
 *
 * @author HE176322
 */
public class DeckView {

    // The result to display, handed over by the controller.
    private DeckResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(DeckResponseDTO response) {
        this.response = response;
    }

    // Prints the brief's screen (title, 52 numbered cards, total), then the optional
    // shuffle/deal part.
    public void display() {
        System.out.println(Message.TITLE);
        System.out.println(String.format(Message.DECK_CREATED, response.getCards().size()));
        int number = 1;
        // one numbered line per card, 1 to 52
        for (String card : response.getCards()) {
            System.out.println(String.format(Message.CARD_LINE, number, card));
            number++;
        }
        System.out.println(Message.FOOTER);
        System.out.println(String.format(Message.TOTAL, response.getCards().size()));
        System.out.println(String.format(Message.DEAL_TITLE, response.getHand().size()));
        // one line per dealt card
        for (String card : response.getHand()) {
            System.out.println(String.format(Message.DEALT_CARD, card));
        }
        System.out.println(String.format(Message.CARDS_LEFT, response.getRemaining()));
    }
}
