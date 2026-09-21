package controller;

import constants.Message;
import dto.ContactRequestDTO;
import dto.ContactResponseDTO;
import java.util.ArrayList;
import repository.ContactRepository;
import view.ContactView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the answer to the view - one render per menu option. No Scanner, no print, no
 * model.
 *
 * @author HE176322
 */
public class ContactController {

    // Where the contacts are stored.
    private ContactRepository contactRepository;

    // Where the results are printed.
    private ContactView contactView;

    // Creates the controller together with its repository and view.
    public ContactController() {
        contactRepository = new ContactRepository();
        contactView = new ContactView();
    }

    // Option 1 (addContact): stores a new contact, then the view prints "Successful" -
    // once.
    public void addContact(ContactRequestDTO requestDTO) {
        ContactResponseDTO responseDTO = new ContactResponseDTO();

        // the repository answers true when the contact was stored
        if (contactRepository.addContact(requestDTO)) {
            responseDTO.setMessage(Message.SUCCESSFUL);
        }

        // hand the answer to the view, then render it - once for the whole flow
        contactView.setResponseDTO(responseDTO);
        contactView.display();
    }

    // Option 2 (displayAll): the repository gives the rows, the view prints them - once.
    public void displayAll() {
        ContactResponseDTO responseDTO = new ContactResponseDTO();
        ArrayList<String> rowList = contactRepository.displayAll();

        // nothing stored yet (or everything deleted): "No found contact" instead of a table
        if (rowList.isEmpty()) {
            responseDTO.setMessage(Message.NOT_FOUND);
        } else {
            // at least one contact: the header and one line per contact
            responseDTO.setRowList(rowList);
        }

        // hand the answer to the view, then render it - once for the whole flow
        contactView.setResponseDTO(responseDTO);
        contactView.display();
    }

    // Option 3 (deleteContact): removes the contact with the typed ID, then the view prints
    // "Successful" - once.
    public void deleteContact(ContactRequestDTO requestDTO) throws Exception {
        ContactResponseDTO responseDTO = new ContactResponseDTO();

        // the brief: "if ID does not exist ... No found contact"
        if (!contactRepository.deleteContact(requestDTO)) {
            throw new Exception(Message.NOT_FOUND);
        }

        // removed: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.SUCCESSFUL);
        contactView.setResponseDTO(responseDTO);
        contactView.display();
    }
}
