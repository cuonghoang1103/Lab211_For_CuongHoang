package main;

import constants.Constants;
import constants.Message;
import controller.ExpenseController;
import dto.ExpenseRequestDTO;
import java.util.Scanner;
import utils.FileUtils;
import utils.Validation;

public final class Main {

    // Constructor private: lop chi co ham static
    private Main() {
    }

    // Hoi lua chon menu den khi hop le
    private static int promptChoice(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_CHOICE);
                return Validation.getChoice(sc.nextLine(), Constants.MENU_ADD, Constants.MENU_EXIT);
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Hoi ngay den khi hop le
    private static String promptDate(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_DATE);
                return Validation.getDate(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Hoi so tien den khi hop le
    private static double promptAmount(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_AMOUNT);
                return Validation.getAmount(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Hoi noi dung den khi khong rong
    private static String promptContent(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_CONTENT);
                return Validation.getContent(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Hoi ID den khi la so nguyen
    private static int promptId(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_ID);
                return Validation.getInt(sc.nextLine());
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Ham chay chuong trinh
    public static void main(String[] args) {
        // Khai bao dau vao nhap lieu
        Scanner sc = new Scanner(System.in);

        // Khai bao controller
        ExpenseController expenseController = new ExpenseController();

        // Doc file du lieu luc mo chuong trinh
        try {
            expenseController.loadExpenses(FileUtils.readLines(Constants.FILE_NAME));
        } catch (Exception e) {
            // Doc loi thi bat dau voi danh sach rong
            System.out.println(Message.READ_FAIL);
        }

        // Vong lap menu
        while (true) {
            System.out.println(Message.MENU);
            int choice = promptChoice(sc);

            // Bat loi cua tung chuc nang
            try {
                // Chon chuc nang
                switch (choice) {
                    // Chuc nang 1: nhap tung o, sai o nao hoi lai o do
                    case Constants.MENU_ADD:
                        System.out.println(Message.TITLE_ADD);
                        ExpenseRequestDTO expenseRequestDTO = new ExpenseRequestDTO();
                        expenseRequestDTO.setDate(promptDate(sc));
                        expenseRequestDTO.setAmount(promptAmount(sc));
                        expenseRequestDTO.setContent(promptContent(sc));
                        expenseController.addExpense(expenseRequestDTO);
                        System.out.println(Message.ADD_SUCCESS);
                        break;

                    // Chuc nang 2: hien thi bang chi tieu
                    case Constants.MENU_DISPLAY:
                        System.out.println(Message.TITLE_DISPLAY);
                        expenseController.displayAll();
                        break;

                    // Chuc nang 3: nhap ID roi xoa
                    case Constants.MENU_DELETE:
                        System.out.println(Message.TITLE_DELETE);
                        expenseController.deleteExpense(promptId(sc));
                        System.out.println(Message.DELETE_SUCCESS);
                        break;

                    // Chuc nang 4: thoat chuong trinh
                    case Constants.MENU_EXIT:
                        System.out.println(Message.GOODBYE);
                        return;

                    // Khong xay ra vi promptChoice da chan ngoai [1, 4]
                    default:
                        break;
                }
            } catch (Exception e) {
                // In loi cua chuc nang
                System.out.println(e.getMessage());
            }
        }
    }
}
