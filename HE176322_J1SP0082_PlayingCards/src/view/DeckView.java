package view;

import constants.Message;
import dto.DeckResponseDTO;

/**
 * VIEW: prints the deck, then the dealt hand. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class DeckView {

    // The result to display, handed over by the controller.
    private DeckResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(DeckResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the brief's screen (title, 52 numbered cards, total), then the optional
    // shuffle/deal part.
    public void display() {
        int number = 1;

        // the brief's first two lines: the title and the size of the new deck
        System.out.println(Message.TITLE);
        System.out.println(String.format(Message.DECK_CREATED, responseDTO.getCardList().size()));

        // one numbered line per card, 1 to 52
        for (String card : responseDTO.getCardList()) {
            System.out.println(String.format(Message.CARD_LINE, number, card));
            number++;
        }

        // the brief's last two lines, then the title of the optional part
        System.out.println(Message.FOOTER);
        System.out.println(String.format(Message.TOTAL, responseDTO.getCardList().size()));
        System.out.println(String.format(Message.DEAL_TITLE, responseDTO.getHandList().size()));

        // one line per dealt card
        for (String card : responseDTO.getHandList()) {
            System.out.println(String.format(Message.DEALT_CARD, card));
        }

        // how many cards the deck still has
        System.out.println(String.format(Message.CARDS_LEFT, responseDTO.getRemaining()));
    }
}
