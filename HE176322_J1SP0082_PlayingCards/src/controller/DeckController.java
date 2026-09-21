package controller;

import dto.DeckResponseDTO;
import service.DeckService;
import view.DeckView;

/**
 * Controller: asks the service for the deck and hands it to the view (no Scanner, no
 * printing, no static, no model).
 *
 * @author HE176322
 */
public class DeckController {

    // lists, shuffles and deals the deck (Controller -> Service -> Repository -> Model)
    private DeckService deckService;

    // prints the deck
    private DeckView deckView;

    // creates the controller with its service and view
    public DeckController() {
        deckService = new DeckService();
        deckView = new DeckView();
    }

    // the only workflow: the service prepares the deck, the view displays it ONCE
    public void showDeck() throws Exception {
        DeckResponseDTO responseDTO = deckService.testDeck();

        // hand the result to the view, then render it - once for the whole flow
        deckView.setResponseDTO(responseDTO);
        deckView.display();
    }
}
