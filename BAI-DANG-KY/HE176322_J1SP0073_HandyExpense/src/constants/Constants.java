package constants;

public final class Constants {

    // Constructor private: lop chi chua hang so, khong cho tao doi tuong
    private Constants() {
    }

    // So cua tung muc menu
    public static final int MENU_ADD = 1;
    public static final int MENU_DISPLAY = 2;
    public static final int MENU_DELETE = 3;
    public static final int MENU_EXIT = 4;

    // Ten file luu du lieu (nam trong thu muc project)
    public static final String FILE_NAME = "expenses.txt";

    // Dau ngan cach cac cot trong file (va regex de tach)
    public static final String FILE_SEPARATOR = "|";
    public static final String FILE_SEPARATOR_REGEX = "\\|";

    // So cot cua 1 dong trong file: id|date|amount|content
    public static final int FILE_FIELDS = 4;

    // Vi tri tung cot trong 1 dong cua file
    public static final int FIELD_ID = 0;
    public static final int FIELD_DATE = 1;
    public static final int FIELD_AMOUNT = 2;
    public static final int FIELD_CONTENT = 3;

    // Dinh dang ngay cua de va regex kiem hinh dang ngay
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    public static final String DATE_REGEX = "\\d{1,2}-[A-Za-z]{3}-\\d{4}";

    // Dinh dang tien: so tron khong co .0 (100), so le giu phan le (100.1)
    public static final String MONEY_FORMAT = "0.##";
}
