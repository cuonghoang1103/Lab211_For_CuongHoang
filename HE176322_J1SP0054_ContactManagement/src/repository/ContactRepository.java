package repository;

import constants.Constants;
import dto.ContactRequestDTO;
import dto.ContactResponseDTO;
import java.util.ArrayList;
import model.Contact;
import model.ContactBuilder;

/**
 * REPOSITORY: holds the contacts and performs simple CRUD on them - the brief's
 * addContact, displayAll (the data part) and deleteContact.
 *
 * @author HE176322
 */
public class ContactRepository {

    // The "database" of contacts, in the order they were added.
    private ArrayList<Contact> contacts = new ArrayList<>();

    // Creates an empty repository.
    public ContactRepository() {
    }

    // The brief's addContact: stores a new contact whose ID is the last ID + 1 (1 for the
    // first contact).
    public boolean addContact(ContactRequestDTO requestDTO) {
        Contact contact = new ContactBuilder()
                .withId(nextId())
                .withFullName(requestDTO.getFullName())
                .withGroup(requestDTO.getGroup())
                .withAddress(requestDTO.getAddress())
                .withPhone(requestDTO.getPhone())
                .build();
        return contacts.add(contact);
    }

    // The data half of the brief's displayAll: every contact as a row the view can print
    // (the view does the printing half).
    public ArrayList<ContactResponseDTO> displayAll() {
        ArrayList<ContactResponseDTO> rows = new ArrayList<>();
        // copy each stored contact into a row for the view
        for (Contact contact : contacts) {
            rows.add(toResponse(contact));
        }
        return rows;
    }

    // The brief's deleteContact: removes the contact with the request's ID.
    public boolean deleteContact(ContactRequestDTO requestDTO) {
        // look at every contact until the ID matches
        for (int i = 0; i < contacts.size(); i++) {
            // found it: remove by position and stop
            if (contacts.get(i).getId() == requestDTO.getId()) {
                contacts.remove(i);
                return true;
            }
        }
        return false;
    }

    // The brief's ID rule: "new contact has ID equal to last ID contact + 1, the first
    // contact has ID: 1".
    private int nextId() {
        // empty list: the first contact gets ID 1
        if (contacts.isEmpty()) {
            return Constants.FIRST_ID;
        }
        return contacts.get(contacts.size() - 1).getId() + Constants.ID_STEP;
    }

    // Copies a model object into the DTO the view is allowed to see.
    private ContactResponseDTO toResponse(Contact contact) {
        ContactResponseDTO row = new ContactResponseDTO();
        row.setId(contact.getId());
        row.setFullName(contact.getFullName());
        row.setFirstName(contact.getFirstName());
        row.setLastName(contact.getLastName());
        row.setGroup(contact.getGroup());
        row.setAddress(contact.getAddress());
        row.setPhone(contact.getPhone());
        return row;
    }
}
