package view;

import constants.Message;
import dto.BillResponseDTO;

/**
 * VIEW: prints the total of the bills and whether the user can buy.
 *
 * @author HE176322
 */
public class BillView {

    // The result to display, handed over by the controller.
    private BillResponseDTO response;

    // Receives the result the next display() call will print.
    public void setResponse(BillResponseDTO response) {
        this.response = response;
    }

    // Prints the two lines of the brief's screen.
    public void display() {
        System.out.println(Message.LABEL_TOTAL + response.getTotal());
        // the wallet holds at least the total
        if (response.isCanBuy()) {
            System.out.println(Message.CAN_BUY);
        } else {
            // the wallet holds less than the total
            System.out.println(Message.CANNOT_BUY);
        }
    }
}
