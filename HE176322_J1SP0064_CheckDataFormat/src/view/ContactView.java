package view;

import constants.Message;
import dto.ContactResponseDTO;

/**
 * VIEW: prints the three accepted values under a "Result" title. It receives the data
 * through its attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ContactView {

    // The result to display, handed over by the controller.
    private ContactResponseDTO responseDTO;

    // Creates the view; the result arrives later through setResponseDTO.
    public ContactView() {
    }

    // Receives the result the next display() call will print.
    public void setResponseDTO(ContactResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the title and the three accepted values.
    public void display() {
        System.out.println(Message.TITLE_RESULT);
        System.out.println(String.format(Message.RESULT_PHONE, responseDTO.getPhone()));
        System.out.println(String.format(Message.RESULT_EMAIL, responseDTO.getEmail()));
        System.out.println(String.format(Message.RESULT_DATE, responseDTO.getDate()));
    }
}
