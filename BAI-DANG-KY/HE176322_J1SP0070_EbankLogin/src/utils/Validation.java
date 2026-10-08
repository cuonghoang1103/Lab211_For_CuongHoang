package utils;

import constants.Message;

/**
 * Kiem tra du lieu nhap o menu.
 *
 * @author HE176322
 */
public final class Validation {

    // constructor private: chi goi ham static
    private Validation() {
    }

    // lua chon menu phai la so trong khoang min - max
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;
        // lua chon phai la so
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // go chu hoac de trong: bao phai nhap so
            throw new Exception(Message.INVALID_NUMBER);
        }
        // lua chon phai nam trong khoang min - max
        if ((choice < min) || (choice > max)) {
            throw new Exception(Message.INVALID_RANGE);
        }
        return choice;
    }
}
