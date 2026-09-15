package controller;

import dto.BillRequestDTO;
import dto.BillResponseDTO;
import service.BillService;
import view.BillView;

/**
 * CONTROLLER: takes the request from main, asks the service for the result, and hands
 * that result to the view.
 *
 * @author HE176322
 */
public class BillController {

    // Totals the bills and checks the wallet.
    private BillService billService;
    // Prints the result.
    private BillView billView;

    // Creates the controller with its service and view.
    public BillController() {
        billService = new BillService();
        billView = new BillView();
    }

    // Function 2 of the brief (Perform function): service computes, view displays.
    public void calculateBill(BillRequestDTO requestDTO) {
        BillResponseDTO response = billService.checkBill(requestDTO);
        billView.setResponse(response);
        billView.display();
    }
}
