/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constants;

/**
 *
 * @author pc
 */
public final class Message {

    private Message() {

    }

    //hien thi MENU
    public static final String MENU
            = "\n================ TASK MANAGEMENT ===============\n"
            + "1. Add Task\n"
            + "2. Delete task\n"
            + "3. Display task\n"
            + "4. Exit\n";

//Hien thi nhap lieu
    public static final String INPUT_CHOICE = "Enter your choice: ";
    public static final String INPUT__REQUIREMENT = "Requirement Name: ";
    public static final String INPUT_TASK_TYPE = "Task Type (1-Code, 2-Test, 3-Design, 4-Review): ";
    public static final String INPUT_DATE = "Date (dd-MM-yyyy): ";
    public static final String INPUT_FROM = "From (8.0 - 17.5): ";
    public static final String INPUT_TO = "To (8.0 - 17.5): ";
    public static final String INPUT_ASSIGNEE = "Assignee: ";
    public static final String INPUT_REVIEWER = "Reviewer: ";
    public static final String INPUT_ID = "Enter ID: ";

//Hien thi bao loi: moi cau = SAI GI + PHAI NHAP THE NAO
    public static final String EMPTY_INPUT = "This field cannot be empty. Please enter again.";
    public static final String INVALID_NUMBER = "Invalid input. Please enter a number.";
    public static final String INVALID_RANGE = "Value must be greater than 0. Please enter again.";
    public static final String INVALID_DATE = "Invalid date. Please enter a real date in format dd-MM-yyyy (e.g. 26-06-2015).";
    public static final String INVALID_TASK_TYPE = "Task type must be a number from 1 to 4 (1-Code, 2-Test, 3-Design, 4-Review).";
    public static final String INVALID_MENU = "Invalid choice. Please choose a number from 1 to 4.";
    public static final String TASK_NOT_EXIST = "No task has this ID. Please choose option 3 to see the task IDs.";
    public static final String INVALID_PLAN_TIME = "Time must be from 8.0 to 17.5. Please enter again.";
    public static final String INVALID_FROM = "From must be from 8.0 to 17.5 (e.g. 8.0, 8.5, 9.0). Please enter again.";
    public static final String INVALID_TO = "To must be from 8.0 to 17.5 (e.g. 8.0, 8.5, 9.0). Please enter again.";
    public static final String INVALID_TIME = "From must be less than To. Please enter From and To again.";
    public static final String INVALID_STEP_TIME = "Time must be a whole or half hour (e.g. 8.0, 8.5). Please enter again.";

}
