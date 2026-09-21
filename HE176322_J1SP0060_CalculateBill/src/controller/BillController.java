package controller;

import dto.BillRequestDTO;
import dto.BillResponseDTO;
import service.BillService;
import view.BillView;

/**
 * CONTROLLER (Facade): takes the request from main, asks the service for the result, and
 * hands that result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class BillController {

    // Totals the bills and checks the wallet (Controller -> Service -> Repository -> Model).
    private BillService billService;

    // Prints the result.
    private BillView billView;

    // Creates the controller with its service and view.
    public BillController() {
        billService = new BillService();
        billView = new BillView();
    }

    // Function 2 of the brief (Perform function): the service computes, the view shows the
    // result ONCE.
    public void calculateBill(BillRequestDTO requestDTO) {
        BillResponseDTO responseDTO = billService.checkBill(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        billView.setResponseDTO(responseDTO);
        billView.display();
    }
}
