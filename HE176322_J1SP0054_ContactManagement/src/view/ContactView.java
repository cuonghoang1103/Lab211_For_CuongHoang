package view;

import constants.Constants;
import constants.Message;
import dto.ContactResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results - the printing half of the
 * brief's displayAll, and the one-line results. It receives the data through its attribute
 * (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ContactView {

    // The answer to print, handed over by the controller.
    private ContactResponseDTO responseDTO;

    // Receives the answer the next display() call will print.
    public void setResponseDTO(ContactResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set: the one-line result, or the header and one line per
    // contact (the printing half of the brief's displayAll).
    public void display() {
        // a one-line result: "Successful", or "No found contact" for an empty list
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // the table of option 2
        if (responseDTO.getRowList() != null) {
            System.out.println(String.format(Constants.ROW_FORMAT, Message.LABEL_ID,
                    Message.LABEL_NAME, Message.LABEL_FIRST_NAME, Message.LABEL_LAST_NAME,
                    Message.LABEL_GROUP, Message.LABEL_ADDRESS, Message.LABEL_PHONE));

            // one line per contact, text built by the model's toString()
            for (String row : responseDTO.getRowList()) {
                System.out.println(row);
            }
        }
    }
}
