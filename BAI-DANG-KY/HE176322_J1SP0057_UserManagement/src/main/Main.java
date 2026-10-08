package main;

import constants.Constants;
import constants.Message;
import controller.AccountController;
import dto.AccountRequestDTO;
import java.util.ArrayList;
import java.util.Scanner;
import utils.FileUtils;
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
                return Validation.getChoice(sc.nextLine(), Constants.MENU_CREATE,
                        Constants.MENU_EXIT);
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap username hop le
    private static String promptUsername(Scanner sc) {
        // Sai thi in loi va hoi lai
        while (true) {
            // Doc va kiem tra username
            try {
                System.out.print(Message.ENTER_USERNAME);
                return Validation.getUsername(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap password hop le
    private static String promptPassword(Scanner sc) {
        // Sai thi in loi va hoi lai
        while (true) {
            // Doc va kiem tra password
            try {
                System.out.print(Message.ENTER_PASSWORD);
                return Validation.getPassword(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Ham main: nap file, hien menu va goi controller theo lua chon
    public static void main(String[] args) {

        // Khai bao dau vao nhap lieu
        Scanner sc = new Scanner(System.in);

        // Khai bao controller
        AccountController accountController = new AccountController();

        // Doc file user.dat va nap vao danh sach truoc khi hien menu
        try {
            ArrayList<String> lineList = FileUtils.readLines(Constants.DATA_FILE);
            accountController.loadData(lineList);
        } catch (Exception e) {
            // In loi doc file
            System.out.println(e.getMessage());
        }

        // Hien menu lai sau moi chuc nang cho den khi chon Exit
        while (true) {
            System.out.println(Message.MENU);
            int choice = promptChoice(sc);

            // Bat loi nghiep vu cua chuc nang vua chon
            try {
                // Chay chuc nang nguoi dung chon
                switch (choice) {
                    // Chuc nang 1: tao tai khoan moi
                    case Constants.MENU_CREATE:
                        AccountRequestDTO createDto = new AccountRequestDTO();
                        createDto.setUsername(promptUsername(sc));
                        createDto.setPassword(promptPassword(sc));

                        // Controller xu ly tao tai khoan
                        accountController.addAccount(createDto);
                        break;

                    // Chuc nang 2: dang nhap
                    case Constants.MENU_LOGIN:
                        AccountRequestDTO loginDto = new AccountRequestDTO();
                        loginDto.setUsername(promptUsername(sc));
                        loginDto.setPassword(promptPassword(sc));

                        // Controller xu ly dang nhap
                        accountController.login(loginDto);
                        break;

                    // Chuc nang 3: thoat chuong trinh
                    case Constants.MENU_EXIT:
                        System.out.println(Message.GOODBYE);
                        return;

                    // Khong xay ra: promptChoice chi tra ve 1 den 3
                    default:
                        break;
                }
            } catch (Exception e) {
                // In loi: trung username hoac dang nhap sai
                System.out.println(e.getMessage());
            }
        }
    }
}
