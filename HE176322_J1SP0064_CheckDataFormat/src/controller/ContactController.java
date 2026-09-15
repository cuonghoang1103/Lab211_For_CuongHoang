package controller;

import dto.ContactRequestDTO;
import dto.ContactResponseDTO;
import service.ContactService;
import view.ContactView;

/**
 * CONTROLLER (and FACADE for main): takes the request from main, asks the service for the
 * result, and hands that result to the view.
 *
 * @author HE176322
 */
public class ContactController {

    // Builds the contact from the accepted texts.
    private ContactService contactService;
    // Prints the result.
    private ContactView contactView;

    // Creates the controller with its service and view.
    public ContactController() {
        contactService = new ContactService();
        contactView = new ContactView();
    }

    // Function 2 of the brief (Perform function): the service builds the contact, the
    // view shows the accepted values.
    public void saveContact(ContactRequestDTO requestDTO) throws Exception {
        ContactResponseDTO response = contactService.createContact(requestDTO);
        contactView.setResponse(response);
        contactView.display();
    }
}
