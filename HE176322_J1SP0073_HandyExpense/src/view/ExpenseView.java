package view;

import constants.Constants;
import constants.Message;
import dto.ExpenseResponseDTO;
import java.util.ArrayList;
import utils.FormatUtils;

/**
 * VIEW: prints the expense table (the brief's displayAll) and one-line results.
 *
 * @author HE176322
 */
public class ExpenseView {

    // The rows to display, handed over by the controller.
    private ArrayList<ExpenseResponseDTO> expenseList;
    // The total of the amounts, computed by the service.
    private double total;

    // Receives the rows the next displayAll() call will print.
    public void setExpenseList(ArrayList<ExpenseResponseDTO> expenseList) {
        this.expenseList = expenseList;
    }

    // Receives the total the next displayAll() call will print.
    public void setTotal(double total) {
        this.total = total;
    }

    // The brief's displayAll: title, header, one row per expense, total.
    public void displayAll() {
        System.out.println(Message.TITLE_DISPLAY);
        // an empty book: say so instead of a table with a total of 0
        if (expenseList == null || expenseList.isEmpty()) {
            System.out.println(Message.NO_EXPENSE);
            return;
        }
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_ID,
                Message.LABEL_DATE, Message.LABEL_AMOUNT, Message.LABEL_CONTENT));
        // one line per expense; toString() of the DTO is already padded
        for (ExpenseResponseDTO expense : expenseList) {
            System.out.println(expense);
        }
        // "Total: 550" - whole totals without decimals, like the brief
        System.out.println(Message.LABEL_TOTAL + FormatUtils.formatMoney(total));
    }

    // Prints a one-line result such as "Add an expense successful".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
