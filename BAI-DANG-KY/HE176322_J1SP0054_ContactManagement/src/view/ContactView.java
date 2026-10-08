package view;

import dto.ContactResponseDTO;
import java.util.ArrayList;

public class ContactView {

    // Danh sach contact da duoc xu ly, controller dua vao
    private ArrayList<ContactResponseDTO> contactList;

    // Constructor
    public ContactView() {
    }

    // Nhan du lieu tu controller
    public void setContactList(ArrayList<ContactResponseDTO> contactList) {
        this.contactList = contactList;
    }

    // In bang contact theo format cua de
    public void display() {
        System.out.printf("%-4s%-18s%-12s%-12s%-8s%-12s%s\n",
                "ID", "Name", "First Name", "Last Name", "Group", "Address", "Phone");

        // Duyet tung contact roi in ra 1 dong
        for (ContactResponseDTO contact : this.contactList) {
            System.out.println(contact);
        }
    }
}
