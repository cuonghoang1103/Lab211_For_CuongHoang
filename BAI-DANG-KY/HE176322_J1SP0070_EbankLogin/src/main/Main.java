package main;

import constants.Constants;
import constants.Message;
import controller.EbankController;
import dto.LoginRequestDTO;
import java.util.Locale;
import java.util.Scanner;
import utils.Validation;

/**
 * Chay chuong trinh: chon ngon ngu roi dang nhap 1 lan.
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
                return Validation.getChoice(input, Constants.MENU_MIN, Constants.MENU_EXIT);
            } catch (Exception e) {
                // in cau loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // hoi so tai khoan den khi dung 10 chu so
    private static String promptAccountNumber(Scanner sc, EbankController ebankController) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(ebankController.getText(Message.KEY_ACCOUNT_PROMPT));
            String accountNumber = sc.nextLine();
            String error = ebankController.checkAccountNumber(accountNumber);
            // khong co loi thi tra ve
            if (error.isEmpty()) {
                return accountNumber;
            }
            System.out.println(error);
        }
    }

    // hoi mat khau den khi dung luat
    private static String promptPassword(Scanner sc, EbankController ebankController) {
        // lap lai den khi hop le
        while (true) {
            System.out.print(ebankController.getText(Message.KEY_PASSWORD_PROMPT));
            String password = sc.nextLine();
            String error = ebankController.checkPassword(password);
            // khong co loi thi tra ve
            if (error.isEmpty()) {
                return password;
            }
            System.out.println(error);
        }
    }

    // in captcha 1 lan, hoi den khi nhap dung
    private static void promptCaptcha(Scanner sc, EbankController ebankController) {
        String captchaGenerate = ebankController.generateCaptcha();
        System.out.print(ebankController.getText(Message.KEY_CAPTCHA_LABEL));
        System.out.println(captchaGenerate);
        // lap lai den khi captcha dung
        while (true) {
            System.out.print(ebankController.getText(Message.KEY_CAPTCHA_PROMPT));
            String captchaInput = sc.nextLine();
            String error = ebankController.checkCaptcha(captchaInput, captchaGenerate);
            // khong co loi thi xong
            if (error.isEmpty()) {
                return;
            }
            System.out.println(error);
        }
    }

    // luong dang nhap: doi ngon ngu, nhap so tai khoan, mat khau, captcha
    private static void login(Scanner sc, EbankController ebankController, Locale locale) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        ebankController.setLocate(locale);
        loginRequestDTO.setAccountNumber(promptAccountNumber(sc, ebankController));
        loginRequestDTO.setPassword(promptPassword(sc, ebankController));
        promptCaptcha(sc, ebankController);
        ebankController.login(loginRequestDTO);
    }

    // ham main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EbankController ebankController = new EbankController();
        System.out.println(Message.MENU);
        int choice = promptChoice(sc);
        // chon ngon ngu roi dang nhap 1 lan, khong lap menu
        switch (choice) {
            case Constants.MENU_VIETNAMESE:
                // tieng Viet
                login(sc, ebankController, new Locale(Constants.LANGUAGE_VI));
                break;
            case Constants.MENU_ENGLISH:
                // tieng Anh
                login(sc, ebankController, new Locale(Constants.LANGUAGE_EN));
                break;
            default:
                // thoat chuong trinh
                break;
        }
    }
}
