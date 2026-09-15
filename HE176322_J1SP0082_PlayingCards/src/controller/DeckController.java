package controller;

import dto.DeckResponseDTO;
import service.DeckService;
import view.DeckView;

/**
 * Controller: asks the service for the deck and hands it to the view (no Scanner, no
 * printing, no static).
 *
 * @author HE176322
 */
public class DeckController {

    // builds, shuffles and deals the deck
    private DeckService deckService;
    // prints the deck
    private DeckView deckView;

    // creates the controller with its service and view
    public DeckController() {
        deckService = new DeckService();
        deckView = new DeckView();
    }

    // the only workflow: build the deck, then display it
    public void showDeck() throws Exception {
        DeckResponseDTO response = deckService.createDeck();
        deckView.setResponse(response);
        deckView.display();
    }
}
