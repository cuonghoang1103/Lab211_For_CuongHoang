package view;

import constants.Constants;
import constants.Message;
import dto.ContactResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: the only place (with main) allowed to print results - the printing half of the
 * brief's displayAll, and the one-line results.
 *
 * @author HE176322
 */
public class ContactView {

    // The rows to display, handed over by the controller.
    private ArrayList<ContactResponseDTO> contactList;

    // Receives the rows the next displayAll() call will print.
    public void setContactList(ArrayList<ContactResponseDTO> contactList) {
        this.contactList = contactList;
    }

    // The brief's displayAll: prints the header and one line per contact, or "No found
    // contact" when the list is empty.
    public void displayAll() {
        // nothing stored yet (or everything deleted)
        if (contactList == null || contactList.isEmpty()) {
            System.out.println(Message.NOT_FOUND);
            return;
        }
        System.out.println(String.format(Constants.ROW_FORMAT, Message.LABEL_ID,
                Message.LABEL_NAME, Message.LABEL_FIRST_NAME, Message.LABEL_LAST_NAME,
                Message.LABEL_GROUP, Message.LABEL_ADDRESS, Message.LABEL_PHONE));
        // one line per contact; toString() of the DTO is already padded
        for (ContactResponseDTO contact : contactList) {
            System.out.println(contact);
        }
    }

    // Prints a one-line result such as "Successful".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
