package view;

import constants.Message;
import dto.ContactResponseDTO;

/**
 * VIEW: prints the three accepted values under a "Result" title.
 *
 * @author HE176322
 */
public class ContactView {

    // The result to display, handed over by the controller.
    private ContactResponseDTO response;

    // Creates the view; the result arrives later through setResponse.
    public ContactView() {
    }

    // Receives the result the next display() call will print.
    public void setResponse(ContactResponseDTO response) {
        this.response = response;
    }

    // Prints the title and the three accepted values.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(Message.LABEL_PHONE + response.getPhone());
        System.out.println(Message.LABEL_EMAIL + response.getEmail());
        System.out.println(Message.LABEL_DATE + response.getDate());
    }
}
