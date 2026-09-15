package controller;

import constants.Message;
import dto.ContactRequestDTO;
import repository.ContactRepository;
import view.ContactView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the result to the view.
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

    // Option 1 (addContact): stores a new contact, then "Successful".
    public void addContact(ContactRequestDTO requestDTO) {
        // the repository answers true when the contact was stored
        if (contactRepository.addContact(requestDTO)) {
            contactView.showMessage(Message.SUCCESSFUL);
        }
    }

    // Option 2 (displayAll): the repository gives the rows, the view prints them.
    public void displayAll() {
        contactView.setContactList(contactRepository.displayAll());
        contactView.displayAll();
    }

    // Option 3 (deleteContact): removes the contact with the typed ID.
    public void deleteContact(ContactRequestDTO requestDTO) throws Exception {
        // the brief: "if ID does not exist ... No found contact"
        if (!contactRepository.deleteContact(requestDTO)) {
            throw new Exception(Message.NOT_FOUND);
        }
        contactView.showMessage(Message.SUCCESSFUL);
    }
}
