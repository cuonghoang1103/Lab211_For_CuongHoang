package controller;

import dto.ColonyResponseDTO;
import service.BeeService;
import view.BeeView;

/**
 * CONTROLLER (Facade): receives the menu choice's work from main, asks the service to do
 * it, and hands the result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class BeeController {

    // Builds and attacks the colony (Controller -> Service -> Repository -> Model).
    private BeeService beeService;

    // Prints the tables.
    private BeeView beeView;

    // Creates the controller together with its service and its view.
    public BeeController() {
        beeService = new BeeService();
        beeView = new BeeView();
    }

    // Option 1: creates a new bee list; the view shows it ONCE.
    public void createBees() {
        ColonyResponseDTO responseDTO = beeService.createBees();

        // hand the table to the view, then render it - once for the whole flow
        beeView.setResponseDTO(responseDTO);
        beeView.display();
    }

    // Option 2: attacks the current bee list; the view shows the result ONCE.
    public void attackBees() throws Exception {
        ColonyResponseDTO responseDTO = beeService.attackBees();

        // hand the table to the view, then render it - once for the whole flow
        beeView.setResponseDTO(responseDTO);
        beeView.display();
    }
}
