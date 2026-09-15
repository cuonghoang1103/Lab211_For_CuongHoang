package view;

import constants.Constants;
import constants.Message;
import dto.SalaryHistoryResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class WorkerView {

    // The rows to display, handed over by the controller.
    private ArrayList<SalaryHistoryResponseDTO> historyList;

    // Receives the rows the next display() call will print.
    public void setHistoryList(ArrayList<SalaryHistoryResponseDTO> historyList) {
        this.historyList = historyList;
    }

    // Prints option 4: a title, then either a "nothing yet" line or a header and one row
    // per adjustment.
    public void display() {
        System.out.println(Message.TITLE_DISPLAY);
        // no salary has been adjusted yet: say so instead of a bare header
        if (historyList == null || historyList.isEmpty()) {
            System.out.println(Message.NO_HISTORY);
            return;
        }
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_CODE,
                Message.LABEL_NAME, Message.LABEL_AGE, Message.LABEL_SALARY,
                Message.LABEL_STATUS, Message.LABEL_DATE));
        // one line per adjustment; toString() of the DTO is already padded
        for (SalaryHistoryResponseDTO history : historyList) {
            System.out.println(history);
        }
    }

    // Prints a one-line result such as "Salary has been adjusted.".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
