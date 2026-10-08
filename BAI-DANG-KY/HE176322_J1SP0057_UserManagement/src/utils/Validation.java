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
            throw new Exception(Message.INVALID_NUMBER);
        }

        // So nam ngoai menu thi bao loi
        if (choice < min || choice > max) {
            throw new Exception(Message.INVALID_RANGE);
        }
        return choice;
    }

    // Kiem tra username: it nhat 5 ky tu va khong co dau cach
    public static String getUsername(String input) throws Exception {
        // Ngan qua hoac co dau cach thi bao loi
        if (input.length() < Constants.USERNAME_MIN_LENGTH || input.contains(" ")) {
            throw new Exception(Message.INVALID_USERNAME);
        }
        return input;
    }

    // Kiem tra password: it nhat 6 ky tu va khong co dau cach
    public static String getPassword(String input) throws Exception {
        // Ngan qua hoac co dau cach thi bao loi
        if (input.length() < Constants.PASSWORD_MIN_LENGTH || input.contains(" ")) {
            throw new Exception(Message.INVALID_PASSWORD);
        }
        return input;
    }
}
