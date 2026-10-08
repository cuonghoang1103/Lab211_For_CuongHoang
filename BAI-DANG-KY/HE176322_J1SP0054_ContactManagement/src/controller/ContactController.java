package controller;

import constants.Message;
import dto.ContactRequestDTO;
import repository.ContactRepository;
import view.ContactView;

public class ContactController {

    // Khai bao repository luu contact
    private ContactRepository contactRepository = new ContactRepository();

    // Khai bao view in ket qua
    private ContactView contactView = new ContactView();

    // Constructor
    public ContactController() {
    }

    // Function 1: them contact
    public void addContact(ContactRequestDTO dto) {
        this.contactRepository.addContact(dto);
    }

    // Function 2: hien thi tat ca contact
    public void displayAll() throws Exception {
        // Chua co contact nao thi bao loi
        if (this.contactRepository.isEmpty()) {
            throw new Exception(Message.NOT_FOUND);
        }

        // Dua du lieu vao view va in ra man hinh
        this.contactView.setContactList(this.contactRepository.getContactList());
        this.contactView.display();
    }

    // Function 3: xoa contact theo id
    public void deleteContact(int contactId) throws Exception {
        // Khong tim thay id thi bao loi
        if (!this.contactRepository.deleteContact(contactId)) {
            throw new Exception(Message.NOT_FOUND);
        }
    }
}
