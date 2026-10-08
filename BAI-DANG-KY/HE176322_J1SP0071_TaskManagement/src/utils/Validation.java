/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import constants.Message;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pc
 */
public final class Validation {

    //private constructor
    private Validation() {

    }

    //chuoi khong duoc rong
    public static String getString(String input) throws Exception {
        //bo dau cach truoc roi moi kiem rong (go toan dau cach cung tinh la rong)
        if (input == null || input.trim().isEmpty()) {
            throw new Exception(Message.EMPTY_INPUT);

        }
        return input.trim();

    }

    //so nguyen duong
    public static int getPositiveInt(String input) throws Exception {
        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                throw new Exception(Message.INVALID_RANGE);

            }
            return value;

        } catch (NumberFormatException e) {
            throw new Exception(Message.INVALID_NUMBER);

        }
    }

    //so thuc duong
    public static double getPositiveDouble(String input) throws Exception {
        try {
            double value = Double.parseDouble(input
            );
            if (value <= 0) {
                throw new Exception(Message.INVALID_RANGE);

            }
            return value;

        } catch (NumberFormatException e) {
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    //validate date
    public static Date validateDate(String input) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            return sdf.parse(input);

        } catch (Exception e) {
            throw new Exception(Message.INVALID_DATE);

        }
    }
    //validate working time

    public static void validatePlantime(double from, double to) throws Exception {
        //from thuoc tu 8.0 - 17.5

        if ((from < 8.0) || (from > 17.5)) {
            throw new Exception(Message.INVALID_FROM);
        }
//to thuoc tu 8.0 - 17.5
        if ((to < 8.0) || (to > 17.5)) {
            throw new Exception(Message.INVALID_TO);

        }
        //from be hon to
        if ((from >= to)) {
            throw new Exception(Message.INVALID_TIME);
        }
        //from la boi so cua 0.5
        if ((from * 2) % 1 != 0) {
            throw new Exception(Message.INVALID_STEP_TIME);
        }
        //to la boi so cua 0.5
        if ((to * 2) % 1 != 0) {
            throw new Exception(Message.INVALID_STEP_TIME);
        }
    }

    public static int validateTaskType(String input) throws Exception {
        try {
            int id = Integer.parseInt(input);
            //tasktype trong khoang 1-4
            if ((id < 1) || (id > 4)) {
                throw new Exception(Message.INVALID_TASK_TYPE);

            }
            return id;
        } catch (NumberFormatException e) {
            throw new Exception(Message.INVALID_NUMBER);
        }

    }

    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;
        //lua chon phai la so
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new Exception(Message.INVALID_MENU);
        }
        //choice thuoc khoang 1-4
        if ((choice < min) || (choice > max)) {
            throw new Exception(Message.INVALID_MENU);
        }
        return choice;
    }
}
