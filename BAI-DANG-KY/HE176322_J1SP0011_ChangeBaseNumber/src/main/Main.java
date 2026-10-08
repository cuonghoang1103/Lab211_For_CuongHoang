package main;

import constants.Base;
import constants.Constants;
import constants.Message;
import controller.ConvertController;
import dto.ConvertRequestDTO;
import java.util.Scanner;
import utils.Validation;

public final class Main {

    // Constructor private: lop chi co ham static
    private Main() {
    }

    // Hoi he vao (0 den 3, 0 la thoat) den khi hop le
    private static int promptInputBase(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_BASE_IN);
                return Validation.getChoice(sc.nextLine(), Constants.MENU_EXIT, Constants.BASE_MAX);
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Hoi he ra (1 den 3) den khi hop le
    private static int promptOutputBase(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_BASE_OUT);
                return Validation.getChoice(sc.nextLine(), Constants.BASE_MIN, Constants.BASE_MAX);
            } catch (Exception e) {
                // In loi roi hoi lai
                System.out.println(e.getMessage());
            }
        }
    }

    // Hoi gia tri den khi khong rong
    private static String promptValue(Scanner sc) {
        // Lap den khi nhap dung
        while (true) {
            // Nhap va kiem tra
            try {
                System.out.print(Message.INPUT_VALUE);
                return Validation.getValue(sc.nextLine());
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
        ConvertController convertController = new ConvertController();

        // Lap lai cho den khi nguoi dung chon 0
        while (true) {
            System.out.println(Message.MENU);
            int inputChoice = promptInputBase(sc);

            // Chon 0 thi thoat
            if (inputChoice == Constants.MENU_EXIT) {
                System.out.println(Message.GOODBYE);
                return;
            }

            // Nhap he ra va gia tri vao RequestDTO
            ConvertRequestDTO convertRequestDTO = new ConvertRequestDTO();
            convertRequestDTO.setInputBase(Base.fromChoice(inputChoice));
            convertRequestDTO.setOutputBase(Base.fromChoice(promptOutputBase(sc)));
            convertRequestDTO.setValue(promptValue(sc));

            // Kiem chu so dung he roi doi; sai thi in loi va ve menu
            try {
                Validation.checkValue(convertRequestDTO.getValue(),
                        convertRequestDTO.getInputBase());
                convertController.convert(convertRequestDTO);
            } catch (Exception e) {
                // In loi (sai chu so hoac so qua lon)
                System.out.println(e.getMessage());
            }
        }
    }
}
