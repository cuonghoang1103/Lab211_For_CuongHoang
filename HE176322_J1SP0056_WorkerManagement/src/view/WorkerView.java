package view;

import constants.Constants;
import constants.Message;
import dto.WorkerResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class WorkerView {

    // The answer to print, handed over by the controller.
    private WorkerResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(WorkerResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the one-line result, or the header and one line per
    // salary adjustment.
    public void display() {
        // a one-line result: "Worker [W 1] has been added.", "Salary has been adjusted." or
        // "No salary has been adjusted yet."
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the table of option 4
        if (responseDTO.getRowList() != null) {
            System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_CODE,
                    Message.LABEL_NAME, Message.LABEL_AGE, Message.LABEL_SALARY,
                    Message.LABEL_STATUS, Message.LABEL_DATE));

            // one line per adjustment, text built by the model's toString()
            for (String row : responseDTO.getRowList()) {
                System.out.println(row);
            }
        }
    }
}
