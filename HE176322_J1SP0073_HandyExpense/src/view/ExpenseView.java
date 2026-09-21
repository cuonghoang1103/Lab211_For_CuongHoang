package view;

import constants.Constants;
import constants.Message;
import dto.ExpenseResponseDTO;
import utils.FormatUtils;

/**
 * VIEW: the only place (with main) allowed to print results - the printing half of the
 * brief's displayAll, and the one-line results. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ExpenseView {

    // The answer to print, handed over by the controller.
    private ExpenseResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(ExpenseResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the one-line result, or the header, one row per
    // expense and the total (the printing half of the brief's displayAll).
    public void display() {
        // a one-line result, or "There is no expense to display." for an empty book
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the table of option 2
        if (responseDTO.getRowList() != null) {
            System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                    Message.LABEL_DATE, Message.LABEL_AMOUNT, Message.LABEL_CONTENT));

            // one line per expense, text built by the model's toString()
            for (String row : responseDTO.getRowList()) {
                System.out.println(row);
            }

            // "Total: 550" - whole totals without decimals, like the brief
            System.out.println(String.format(Message.TOTAL_FORMAT,
                    FormatUtils.formatMoney(responseDTO.getTotal())));
        }
    }
}
