package utils;

import constants.Message;

public final class Validation {
    
    // Ngan khong cho tao object
    private Validation() {}

    // Kiem tra chuoi dau vao khong duoc de trong
    public static String getString(String input) throws Exception {
       
        if (input.equals(null) || input.trim().isEmpty()) {
            throw new Exception(Message.EMPTY_INPUT);
        }
        return input.trim();
    }
    
    // Kiem tra do tuoi hop le (18 den 50)
    public static int getAge(String input) throws Exception {
        try {
            int age = Integer.parseInt(input);
            // Kiem tra rang buoc
            if(age < 18 || age > 50) {
                throw new Exception(Message.AGE_ERROR);
            }
            return age;
        } catch (NumberFormatException e) {
            // Bat loi khi nguoi dung go chu cai thay vi so
            throw new Exception(Message.INVALID_NUMBER);
        }
    }
    
    // Kiem tra so tien nhap vao phai duong
    public static double getPositiveDouble(String input) throws Exception {
        try {
            double number = Double.parseDouble(input);
            // Kiem tra so am hoac bang 0
            if (number <= 0) {
                throw new Exception(Message.POSITIVE_NUMBER);
            }
            return number;
        } catch (NumberFormatException e) {
            throw new Exception(Message.INVALID_NUMBER);
        }
    }
    
    // Kiem tra lua chon Menu nam trong khoang cho phep (1-5)
    public static int getChoice(String input, int min, int max) throws Exception {
        try {
            int choice = Integer.parseInt(input);
            // Kiem tra ranh gioi
            if (choice < min || choice > max) {
                // Ghep so min max vao thong bao loi
                throw new Exception(String.format(Message.INVALID_RANGE + " (%d - %d)", min, max));
            }
            return choice;
        } catch (NumberFormatException e) {
            throw new Exception(Message.INVALID_NUMBER);
        }
    }
}