package utils;

import constants.Constants;
import constants.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Validation {

    // Constructor private: lop chi co ham static
    private Validation() {
    }

    // Kiem chuoi la so nguyen (dung cho ID)
    public static int getInt(String input) throws Exception {
        // Doi chuoi sang so nguyen
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // Go chu thay vi so
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Kiem lua chon menu nam trong [min, max]
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = getInt(input);

        // Ngoai khoang menu
        if ((choice < min) || (choice > max)) {
            throw new Exception(Message.INVALID_RANGE);
        }
        return choice;
    }

    // Kiem ngay dung dang dd-MMM-yyyy va co that (31-Feb bi tu choi)
    public static String getDate(String input) throws Exception {
        String text = input.trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
        dateFormat.setLenient(false);

        // Sai hinh dang (vd 2009-04-20, 11-Apr-09)
        if (!text.matches(Constants.DATE_REGEX)) {
            throw new Exception(Message.INVALID_DATE);
        }

        // Doi sang Date roi doi lai chuoi de chuan hoa (11-apr-2009 -> 11-Apr-2009)
        try {
            Date date = dateFormat.parse(text);
            return dateFormat.format(date);
        } catch (ParseException e) {
            // Ngay khong co that
            throw new Exception(Message.INVALID_DATE);
        }
    }

    // Kiem so tien la so thuc lon hon 0
    public static double getAmount(String input) throws Exception {
        double amount = 0;

        // Doi chuoi sang so thuc
        try {
            amount = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            // Go chu thay vi so
            throw new Exception(Message.INVALID_AMOUNT);
        }

        // Chan NaN, Infinity
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new Exception(Message.INVALID_AMOUNT);
        }

        // So tien phai duong
        if (amount <= 0) {
            throw new Exception(Message.AMOUNT_POSITIVE);
        }
        return amount;
    }

    // Kiem chuoi khong rong (go toan dau cach cung tinh la rong)
    public static String getContent(String input) throws Exception {
        // Rong sau khi bo dau cach
        if (input.trim().isEmpty()) {
            throw new Exception(Message.EMPTY_FIELD);
        }
        return input.trim();
    }
}
