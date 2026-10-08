/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constants;

/**
 *
 * @author LENOVOLOQ
 */
public final class Message {

    private Message() {
    }

    //Thong bao nhap lieu
    public static final String MENU
            = """
           ======= Worker Management =======  
           1. Add Worker
           2. Up salary
           3. Down salary
           4. Display Information salary
           5. Exit
           """;

    //Thong bao nhap lieu
    public static final String ENTER_CHOICE = "Enter your choice: ";
    public static final String ENTER_CODE = "Enter Code: ";
    public static final String ENTER_NAME = "Enter Name: ";
    public static final String ENTER_AGE = "Enter Age: ";
    public static final String ENTER_SALARY = "Enter Salary: ";
    public static final String ENTER_LOCATION = "Enter Location: ";

    //Title
    public static final String TITLE_ADD_WORKER = "------ Add Worker ------ ";
    public static final String TITLE_SALARY = "------ Up/Down Salary ------ ";
    public static final String TITLE_DISPLAY = "----------------------- Display Information Salary ----------------------- ";

    //Thong bao loi
    public static final String EMPTY_INPUT = "Input cannot be empty";
    public static final String INVALID_RANGE = "Value is out of allowed range";
    public static final String POSITIVE_NUMBER = "Number must be greater than 0";
    public static final String INVALID_NUMBER = "Invalid number!";
    public static final String AGE_ERROR = "Age must be from 18 to 50";
    public static final String POSITIVE_VALUE = "Value must be greater than 0";
    public static final String WORKER_NULL = "Worker does not exist!";
    public static final String DUPLICATE = "Code is duplicated!";
    public static final String EMPTY_DATABASE = "Database is empty";
    public static final String INSUFFICIENT_SALARY = "Decrease amount must not exceed current salary";

}
