package view;

import constants.Message;
import dto.BillResponseDTO;

/**
 * VIEW: prints the total of the bills and whether the user can buy. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class BillView {

    // The result to print, handed over by the controller.
    private BillResponseDTO responseDTO;

    // Receives the result the next display() call will print.
    public void setResponseDTO(BillResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the two lines of the brief's screen: the total, then the answer of the wallet.
    public void display() {
        System.out.println(String.format(Message.LABEL_TOTAL, responseDTO.getTotal()));

        // the wallet holds at least the total
        if (responseDTO.isCanBuy()) {
            System.out.println(Message.CAN_BUY);
        } else {
            // the wallet holds less than the total
            System.out.println(Message.CANNOT_BUY);
        }
    }
}
