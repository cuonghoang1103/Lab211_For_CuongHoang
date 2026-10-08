package utils;

import constants.Base;
import constants.Constants;
import constants.Message;

public final class Validation {

    // Constructor private: lop chi co ham static
    private Validation() {
    }

    // Kiem lua chon menu la so nguyen trong [min, max]
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // Doi chuoi sang so nguyen
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // Go chu thay vi so
            throw new Exception(Message.INVALID_NUMBER);
        }

        // Ngoai khoang cho phep
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // Kiem gia tri khong duoc rong
    public static String getValue(String input) throws Exception {
        // Rong sau khi bo dau cach
        if (input.trim().isEmpty()) {
            throw new Exception(Message.EMPTY_VALUE);
        }
        return input.trim();
    }

    // Kiem moi chu so deu dung he (vd he 2 chi co 0 va 1)
    public static void checkValue(String value, Base base) throws Exception {
        String digits = value.toUpperCase();

        // Bo dau - hoac + o dau
        if (digits.startsWith(Constants.MINUS) || digits.startsWith(Constants.PLUS)) {
            digits = digits.substring(1);
        }

        // Chi co dau ma khong co chu so
        if (digits.isEmpty()) {
            throw new Exception(String.format(Message.INVALID_VALUE, value, base.getLabel()));
        }

        // Kiem tung chu so
        for (int i = 0; i < digits.length(); i++) {
            int digit = Constants.DIGITS.indexOf(digits.charAt(i));

            // Khong phai chu so, hoac lon hon co so cho phep
            if ((digit < 0) || (digit >= base.getRadix())) {
                throw new Exception(String.format(Message.INVALID_VALUE, value,
                        base.getLabel()));
            }
        }
    }
}
