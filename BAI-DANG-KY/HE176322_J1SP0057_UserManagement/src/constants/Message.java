package constants;

public final class Message {

    // Constructor private: lop chi chua hang so, khong cho tao object
    private Message() {
    }

    // Menu chinh theo dung man hinh cua de
    public static final String MENU
            = "====== USER MANAGEMENT SYSTEM ======\n"
            + "1. Create a new account\n"
            + "2. Login system\n"
            + "3. Exit";

    // Cau nhac nhap lieu
    public static final String ENTER_CHOICE = "> Choose: ";
    public static final String ENTER_USERNAME = "Enter Username: ";
    public static final String ENTER_PASSWORD = "Enter Password: ";

    // Thong bao loi menu: sai gi + phai nhap the nao
    public static final String INVALID_NUMBER = "You must input a number.";
    public static final String INVALID_RANGE = "Please choose from 1 to 3.";

    // Thong bao loi username, password (chu cua de)
    public static final String INVALID_USERNAME
            = "You must enter least at 5 character, and no space!";
    public static final String INVALID_PASSWORD
            = "You must enter least at 6 character, and no space!";

    // Username da co trong file, %s la username vua nhap
    public static final String DUPLICATE_USERNAME = "Username [%s] already exists.";

    // Dang nhap sai (chu cua de)
    public static final String LOGIN_FAIL = "Invalid user name or password";

    // Loi doc ghi file
    public static final String CANNOT_READ
            = "Can't read file user.dat. Please check the file in the project folder.";
    public static final String CANNOT_WRITE
            = "Can't write file user.dat. Please check the file is not read-only.";

    // Thong bao thanh cong
    public static final String CREATE_SUCCESS = "Create account successfully!";
    public static final String LOGIN_SUCCESS = "Login successful!";

    // Thong bao khi thoat
    public static final String GOODBYE = "Goodbye.";
}
