package main;

import constants.Constants;
import constants.Message;
import controller.ContactController;
import dto.ContactRequestDTO;
import java.util.Scanner;
import utils.Validation;

public class Main {

    // Constructor private: Main chi co ham static
    private Main() {
    }

    // Lap lai cho den khi nhap lua chon menu hop le
    private static int promptChoice(Scanner sc) {
        // Sai thi in loi va hoi lai
        while (true) {
            // Doc va kiem tra lua chon
            try {
                System.out.print(Message.ENTER_CHOICE);
                return Validation.getChoice(sc.nextLine(), Constants.MENU_ADD,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap chuoi khong rong, fieldName la ten o dang nhap
    private static String promptString(Scanner sc, String fieldName) {
        // Sai thi in loi va hoi lai dung o do
        while (true) {
            // Doc va kiem tra chuoi
            try {
                System.out.print(String.format(Message.ENTER_FIELD, fieldName));
                return Validation.getString(sc.nextLine(), fieldName);
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap phone dung 1 trong 7 dang
    private static String promptPhone(Scanner sc) {
        // Sai thi in 7 dang dung va hoi lai
        while (true) {
            // Doc va kiem tra phone
            try {
                System.out.print(Message.ENTER_PHONE);
                return Validation.getPhone(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap ID la so nguyen duong
    private static int promptId(Scanner sc) {
        // Sai thi in "ID is digit" va hoi lai
        while (true) {
            // Doc va kiem tra ID
            try {
                System.out.print(Message.ENTER_ID);
                return Validation.getId(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Ham main: hien menu va goi controller theo lua chon
    public static void main(String[] args) {

        // Khai bao dau vao nhap lieu
        Scanner sc = new Scanner(System.in);

        // Khai bao controller
        ContactController contactController = new ContactController();

        // Hien menu lai sau moi chuc nang cho den khi chon Exit
        while (true) {
            System.out.println(Message.MENU);
            int choice = promptChoice(sc);

            // Bat loi nghiep vu cua chuc nang vua chon
            try {
                // Chay chuc nang nguoi dung chon
                switch (choice) {
                    // Chuc nang 1: them contact
                    case Constants.MENU_ADD:
                        ContactRequestDTO addDto = new ContactRequestDTO();
                        System.out.println(Message.TITLE_ADD);
                        // Nhap tung field, sai field nao hoi lai field do
                        addDto.setFullName(promptString(sc, Message.FIELD_NAME));
                        addDto.setGroup(promptString(sc, Message.FIELD_GROUP));
                        addDto.setAddress(promptString(sc, Message.FIELD_ADDRESS));
                        addDto.setPhone(promptPhone(sc));

                        // Controller xu ly them moi
                        contactController.addContact(addDto);
                        System.out.println(Message.SUCCESSFUL);
                        break;

                    // Chuc nang 2: hien thi tat ca contact
                    case Constants.MENU_DISPLAY:
                        System.out.println(Message.TITLE_DISPLAY);
                        // Controller in bang ra man hinh
                        contactController.displayAll();
                        break;

                    // Chuc nang 3: xoa contact theo ID
                    case Constants.MENU_DELETE:
                        System.out.println(Message.TITLE_DELETE);
                        int contactId = promptId(sc);

                        // Controller xu ly xoa
                        contactController.deleteContact(contactId);
                        System.out.println(Message.SUCCESSFUL);
                        break;

                    // Chuc nang 4: thoat chuong trinh
                    case Constants.MENU_EXIT:
                        return;

                    // Khong xay ra: promptChoice chi tra ve 1 den 4
                    default:
                        break;
                }
            } catch (Exception e) {
                // In loi "No found contact"
                System.out.println(e.getMessage());
            }
        }
    }
}
