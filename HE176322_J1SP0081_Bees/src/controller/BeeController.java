package controller;

import dto.ColonyResponseDTO;
import service.BeeService;
import view.BeeView;

/**
 * CONTROLLER: receives the menu choice's work from main, asks the service to do it, and
 * hands the result to the view.
 *
 * @author HE176322
 */
public class BeeController {

    // Builds and attacks the colony.
    private BeeService beeService;
    // Prints the tables.
    private BeeView beeView;

    // Creates the controller with its service and view.
    public BeeController() {
        beeService = new BeeService();
        beeView = new BeeView();
    }

    // Option 1: creates a new bee list and displays it.
    public void createBees() {
        ColonyResponseDTO colony = beeService.createBees();
        beeView.setColony(colony);
        beeView.displayNewColony();
    }

    // Option 2: attacks the current bee list and displays the result.
    public void attackBees() throws Exception {
        ColonyResponseDTO colony = beeService.attackBees();
        beeView.setColony(colony);
        beeView.displayAttack();
    }
}
