package main;

import constants.Constants;
import constants.Message;
import controller.AccountController;
import dto.AccountRequestDTO;
import java.util.Date;
import java.util.Scanner;
import utils.MD5Utils;
import utils.Validation;

/**
 * Chay chuong trinh: menu, nhap lieu, kiem tra, bam MD5.
 *
 * @author HE176322
 */
public final class Main {

    // constructor private: chi co ham static
    private Main() {
    }

    // hoi lua chon menu den khi nhap so tu 1 den 3
    private static int promptChoice(Scanner sc) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(Message.INPUT_CHOICE);
            String input = sc.nextLine();
            // sai thi in loi va hoi lai
            try {
                return Validation.getChoice(input, Constants.MENU_ADD, Constants.MENU_EXIT);
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // hoi chuoi khong rong, sai thi hoi lai dung o do
    private static String promptString(Scanner sc, String prompt, String errorMessage) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            // sai thi in loi va hoi lai
            try {
                return Validation.getString(input, errorMessage);
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // hoi so dien thoai den khi hop le
    private static String promptPhone(Scanner sc) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(Message.INPUT_PHONE);
            String input = sc.nextLine();
            // sai thi in loi va hoi lai
            try {
                return Validation.getPhone(input);
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // hoi email den khi hop le
    private static String promptEmail(Scanner sc) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(Message.INPUT_EMAIL);
            String input = sc.nextLine();
            // sai thi in loi va hoi lai
            try {
                return Validation.getEmail(input);
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // hoi ngay sinh den khi hop le
    private static Date promptDate(Scanner sc) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(Message.INPUT_DOB);
            String input = sc.nextLine();
            // sai thi in loi va hoi lai
            try {
                return Validation.getDate(input);
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // hoi mat khau moi 2 lan den khi khong rong va khop nhau, tra ve mat khau moi
    private static String promptNewPassword(Scanner sc) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(Message.INPUT_NEW_PASSWORD);
            String newPassword = sc.nextLine().trim();
            System.out.print(Message.INPUT_RENEW_PASSWORD);
            String renewPassword = sc.nextLine().trim();
            // sai thi in loi va hoi lai ca 2 o
            try {
                Validation.checkNewPassword(newPassword, renewPassword);
                return newPassword;
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // luong them user: nhap 7 o, bam mat khau, goi controller
    private static void addAccount(Scanner sc, AccountController accountController) throws Exception {
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        System.out.println(Message.TITLE_ADD);
        accountRequestDTO.setUsername(promptString(sc, Message.INPUT_ADD_ACCOUNT, Message.USERNAME_EMPTY));
        String password = promptString(sc, Message.INPUT_ADD_PASSWORD, Message.PASSWORD_EMPTY);
        accountRequestDTO.setPassword(MD5Utils.hash(password));
        accountRequestDTO.setName(promptString(sc, Message.INPUT_NAME, Message.NAME_EMPTY));
        accountRequestDTO.setPhone(promptPhone(sc));
        accountRequestDTO.setEmail(promptEmail(sc));
        System.out.print(Message.INPUT_ADDRESS);
        accountRequestDTO.setAddress(sc.nextLine().trim());
        accountRequestDTO.setDob(promptDate(sc));
        accountController.addAccount(accountRequestDTO);
    }

    // luong dang nhap: nhap username, mat khau, dung thi hoi co doi mat khau khong
    private static void login(Scanner sc, AccountController accountController) throws Exception {
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        System.out.println(Message.TITLE_LOGIN);
        System.out.print(Message.INPUT_LOGIN_ACCOUNT);
        accountRequestDTO.setUsername(sc.nextLine().trim());
        System.out.print(Message.INPUT_LOGIN_PASSWORD);
        accountRequestDTO.setPassword(MD5Utils.hash(sc.nextLine().trim()));
        accountController.login(accountRequestDTO);
        String answer = sc.nextLine().trim();
        // tra loi Y hoac y thi doi mat khau, khac thi ve menu
        if (answer.equalsIgnoreCase(Constants.YES)) {
            System.out.print(Message.INPUT_OLD_PASSWORD);
            accountRequestDTO.setOldPassword(MD5Utils.hash(sc.nextLine().trim()));
            accountRequestDTO.setNewPassword(MD5Utils.hash(promptNewPassword(sc)));
            accountController.changePassword(accountRequestDTO);
        }
    }

    // ham main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountController accountController = new AccountController();
        // vong lap hien thi menu
        while (true) {
            System.out.println(Message.MENU);
            int choice = promptChoice(sc);
            // loi nghiep vu (trung username, login fail, sai mat khau cu) in ra roi ve menu
            try {
                // chay chuc nang theo lua chon
                switch (choice) {
                    case Constants.MENU_ADD:
                        // them user
                        addAccount(sc, accountController);
                        break;
                    case Constants.MENU_LOGIN:
                        // dang nhap
                        login(sc, accountController);
                        break;
                    default:
                        // thoat chuong trinh
                        System.out.println(Message.GOODBYE);
                        return;
                }
            } catch (Exception e) {
                // in loi nghiep vu roi quay lai menu
                System.out.println(e.getMessage());
            }
        }
    }
}
