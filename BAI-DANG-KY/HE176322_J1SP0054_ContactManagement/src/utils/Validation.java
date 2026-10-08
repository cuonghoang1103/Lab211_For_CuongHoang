package utils;

import constants.Constants;
import constants.Message;

public final class Validation {

    // Ngan khong cho tao object
    private Validation() {
    }

    // Kiem tra lua chon menu la so trong khoang min - max
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // Chuyen chuoi sang so
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // Go chu hoac de trong thi bao loi
            throw new Exception(Message.INVALID_CHOICE);
        }

        // So nam ngoai menu thi bao loi
        if (choice < min || choice > max) {
            throw new Exception(Message.INVALID_CHOICE);
        }
        return choice;
    }

    // Kiem tra chuoi khong duoc de trong, fieldName la ten o dang nhap
    public static String getString(String input, String fieldName) throws Exception {
        // Rong hoac toan dau cach thi bao loi, vi du "Name must not be blank."
        if (input == null || input.trim().isEmpty()) {
            throw new Exception(String.format(Message.FIELD_BLANK, fieldName));
        }
        return input.trim();
    }

    // Kiem tra phone khop 1 trong 7 dang cua de
    public static String getPhone(String input) throws Exception {
        String phone = input.trim();

        // Khong khop dang nao thi bao loi kem danh sach 7 dang
        if (!phone.matches(Constants.PHONE_PATTERN)) {
            throw new Exception(Message.INVALID_PHONE);
        }
        return phone;
    }

    // Kiem tra ID la so nguyen duong
    public static int getId(String input) throws Exception {
        int id = 0;

        // Chuyen chuoi sang so
        try {
            id = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // Go chu hoac de trong thi bao loi
            throw new Exception(Message.ID_DIGIT);
        }

        // ID bang 0 hoac am thi bao loi
        if (id < Constants.FIRST_ID) {
            throw new Exception(Message.ID_DIGIT);
        }
        return id;
    }
}
