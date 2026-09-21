package repository;

import model.Contact;

/**
 * REPOSITORY: holds the data of the program - the contact the user finally entered - and
 * only simple CRUD on it. No check, no print.
 *
 * @author HE176322
 */
public class ContactRepository {

    // The contact the program works on (the model).
    private Contact contact;

    // Creates the store with an empty contact.
    public ContactRepository() {
        contact = new Contact();
    }

    // Create: keeps the contact built from what the user typed.
    public void saveContact(Contact typedContact) {
        contact = typedContact;
    }

    // Read: returns the contact kept by the last save.
    public Contact getContact() {
        return contact;
    }
}
