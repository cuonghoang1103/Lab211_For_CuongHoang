package constants;

public final class Message {

    // Constructor private: lop chi chua hang so, khong cho tao doi tuong
    private Message() {
    }

    // Menu chinh
    public static final String MENU
            = "=======Handy Expense program======\n"
            + "1. Add an expense\n"
            + "2. Display all expenses\n"
            + "3. Delete an expense\n"
            + "4. Quit";

    // Cau nhac nhap lieu
    public static final String INPUT_CHOICE = "Your choice: ";
    public static final String INPUT_DATE = "Enter Date: ";
    public static final String INPUT_AMOUNT = "Enter Amount: ";
    public static final String INPUT_CONTENT = "Enter Content: ";
    public static final String INPUT_ID = "Enter ID: ";

    // Tieu de tung chuc nang
    public static final String TITLE_ADD = "-------- Add an expense--------";
    public static final String TITLE_DISPLAY = "---------Display all expenses------------";
    public static final String TITLE_DELETE = "--------Delete an expense------";

    // Thong bao ket qua (chep dung chu cua de)
    public static final String ADD_SUCCESS = "Add an expense successful";
    public static final String DELETE_SUCCESS = "Delete an expense successful";
    public static final String DELETE_FAIL = "Delete an expense fail";
    public static final String NO_EXPENSE = "There is no expense to display.";
    public static final String GOODBYE = "Bye.";

    // Thong bao loi: moi cau = SAI GI + PHAI NHAP THE NAO
    public static final String INVALID_NUMBER = "You must input a number.";
    public static final String INVALID_RANGE = "Please input a number in [1, 4].";
    public static final String INVALID_DATE
            = "Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009.";
    public static final String INVALID_AMOUNT = "Amount must be a number.";
    public static final String AMOUNT_POSITIVE = "Amount must be greater than 0.";
    public static final String EMPTY_FIELD = "This field must not be empty.";

    // Thong bao loi doc/ghi file
    public static final String READ_FAIL
            = "Cannot read file expenses.txt. The program starts with an empty list.";
    public static final String WRITE_FAIL
            = "Cannot write file expenses.txt. Please check the file is not read-only.";
}
