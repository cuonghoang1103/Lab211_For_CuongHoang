package utils;

import constants.Constants;
import constants.Message;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Kiem tra du lieu nhap, sai thi nem Exception kem cau loi.
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

    // chuoi khong duoc rong, rong thi nem cau loi truyen vao
    public static String getString(String input, String errorMessage) throws Exception {
        // go toan dau cach cung tinh la rong
        if (input.trim().isEmpty()) {
            throw new Exception(errorMessage);
        }
        return input.trim();
    }

    // so dien thoai 10 hoac 11 chu so
    public static String getPhone(String input) throws Exception {
        String phone = getString(input, Message.PHONE_EMPTY);
        // co chu, thieu hoac thua chu so
        if (!phone.matches(Constants.PHONE_REGEX)) {
            throw new Exception(Message.PHONE_INVALID);
        }
        return phone;
    }

    // email dung dinh dang
    public static String getEmail(String input) throws Exception {
        String email = getString(input, Message.EMAIL_EMPTY);
        // thieu @ hoac thieu duoi ten mien
        if (!email.matches(Constants.EMAIL_REGEX)) {
            throw new Exception(Message.EMAIL_INVALID);
        }
        return email;
    }

    // ngay sinh la ngay that dang dd/MM/yyyy
    public static Date getDate(String input) throws Exception {
        String text = getString(input, Message.DOB_EMPTY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        // khong cho 31/02 tu doi thanh 03/03
        dateFormat.setLenient(false);
        Date dob = null;
        // chuoi khong doc duoc thanh ngay
        try {
            dob = dateFormat.parse(text);
        } catch (Exception e) {
            // chuoi khong doc duoc thanh ngay
            throw new Exception(Message.DOB_INVALID);
        }
        // doi nguoc lai phai ra dung chuoi da go (chan 1/2/2015, 26/06/2016xyz)
        if (!dateFormat.format(dob).equals(text)) {
            throw new Exception(Message.DOB_INVALID);
        }
        return dob;
    }

    // mat khau moi khong rong va nhap lai phai khop
    public static void checkNewPassword(String newPassword, String renewPassword) throws Exception {
        // mat khau moi rong
        if (newPassword.trim().isEmpty()) {
            throw new Exception(Message.NEW_PASSWORD_EMPTY);
        }
        // hai lan nhap khong giong nhau
        if (!newPassword.equals(renewPassword)) {
            throw new Exception(Message.PASSWORD_MISMATCH);
        }
    }
}
