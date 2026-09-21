package repository;

import constants.Constants;
import dto.ContactRequestDTO;
import java.util.ArrayList;
import model.Contact;
import model.ContactBuilder;

/**
 * REPOSITORY: holds the contacts and performs simple CRUD on them - the brief's
 * addContact, displayAll (the data part) and deleteContact. No rule of the screen, no
 * print.
 *
 * @author HE176322
 */
public class ContactRepository {

    // The "database" of contacts, in the order they were added.
    private ArrayList<Contact> contactList;

    // Creates an empty repository.
    public ContactRepository() {
        contactList = new ArrayList<>();
    }

    // The brief's addContact: stores a new contact whose ID is the last ID + 1 (1 for the
    // first contact).
    public boolean addContact(ContactRequestDTO requestDTO) {
        Contact contact = new ContactBuilder()
                .setId(generateNextId())
                .setFullName(requestDTO.getFullName())
                .setGroup(requestDTO.getGroup())
                .setAddress(requestDTO.getAddress())
                .setPhone(requestDTO.getPhone())
                .build();

        // keep it after the others; true = one more contact is stored
        return contactList.add(contact);
    }

    // The data half of the brief's displayAll: every contact as the table row its
    // toString() gives (the view does the printing half).
    public ArrayList<String> displayAll() {
        ArrayList<String> rowList = new ArrayList<>();

        // one row per stored contact, in the order they were added
        for (Contact contact : contactList) {
            rowList.add(contact.toString());
        }

        return rowList;
    }

    // The brief's deleteContact: removes the contact with the request's ID.
    public boolean deleteContact(ContactRequestDTO requestDTO) {
        // look at every contact until the ID matches
        for (int i = 0; i < contactList.size(); i++) {
            // found it: remove by position and stop
            if (contactList.get(i).getId() == requestDTO.getId()) {
                contactList.remove(i);
                return true;
            }
        }

        return false;
    }

    // The brief's ID rule: "new contact has ID equal to last ID contact + 1, the first
    // contact has ID: 1".
    private int generateNextId() {
        // empty list: the first contact gets ID 1
        if (contactList.isEmpty()) {
            return Constants.FIRST_ID;
        }

        return contactList.get(contactList.size() - 1).getId() + Constants.ID_STEP;
    }
}
