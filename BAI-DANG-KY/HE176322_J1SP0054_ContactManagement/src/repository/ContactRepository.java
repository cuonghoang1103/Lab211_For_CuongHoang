package repository;

import constants.Constants;
import dto.ContactRequestDTO;
import dto.ContactResponseDTO;
import java.util.ArrayList;
import model.Contact;

public class ContactRepository {

    // Danh sach luu tru contact trong bo nho
    private ArrayList<Contact> contactList = new ArrayList<>();

    // Constructor
    public ContactRepository() {
    }

    // Them contact moi tu dto, tra ve true khi them thanh cong
    public boolean addContact(ContactRequestDTO dto) {
        Contact contact = new Contact(generateNextId(), dto.getFullName(), dto.getGroup(),
                dto.getAddress(), dto.getPhone());
        return this.contactList.add(contact);
    }

    // Xoa contact theo id, tra ve false neu khong tim thay
    public boolean deleteContact(int contactId) {
        // Duyet tung contact de tim id
        for (int i = 0; i < this.contactList.size(); i++) {
            // Trung id thi xoa theo vi tri va dung lai
            if (this.contactList.get(i).getId() == contactId) {
                this.contactList.remove(i);
                return true;
            }
        }
        return false;
    }

    // Kiem tra danh sach co dang rong khong
    public boolean isEmpty() {
        return this.contactList.isEmpty();
    }

    // Chuyen tung contact sang ResponseDTO de controller dua cho view
    public ArrayList<ContactResponseDTO> getContactList() {
        ArrayList<ContactResponseDTO> contactResponseList = new ArrayList<>();

        // Duyet tung contact, tao 1 ResponseDTO cho moi contact
        for (Contact contact : this.contactList) {
            contactResponseList.add(new ContactResponseDTO(contact.getId(),
                    contact.getFullName(), contact.getFirstName(), contact.getLastName(),
                    contact.getGroup(), contact.getAddress(), contact.getPhone()));
        }
        return contactResponseList;
    }

    // Cap ID moi = ID cua contact cuoi + 1 (contact dau tien co ID 1)
    private int generateNextId() {
        // Danh sach rong thi tra ve ID dau tien
        if (this.contactList.isEmpty()) {
            return Constants.FIRST_ID;
        }
        return this.contactList.get(this.contactList.size() - 1).getId() + 1;
    }
}
