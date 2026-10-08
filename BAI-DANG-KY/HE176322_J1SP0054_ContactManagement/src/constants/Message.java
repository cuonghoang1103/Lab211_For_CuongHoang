package constants;

public final class Message {

    // Constructor private: lop chi chua hang so, khong cho tao object
    private Message() {
    }

    // Menu chinh theo dung man hinh cua de
    public static final String MENU
            = "========= Contact program =========\n"
            + "1. Add a Contact\n"
            + "2. Display all Contact\n"
            + "3. Delete a Contact\n"
            + "4. Exit";

    // Cau nhac nhap lua chon menu
    public static final String ENTER_CHOICE = "Please choice one option: Your choice: ";

    // Cau nhac nhap thong tin contact
    public static final String ENTER_FIELD = "Enter %s: ";
    public static final String ENTER_PHONE = "Enter Phone: ";
    public static final String ENTER_ID = "Enter ID: ";

    // Ten cac o nhap, dien vao cho %s cua ENTER_FIELD va FIELD_BLANK
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_GROUP = "Group";
    public static final String FIELD_ADDRESS = "Address";

    // Tieu de cua tung chuc nang
    public static final String TITLE_ADD = "-------- Add a Contact --------";
    public static final String TITLE_DISPLAY = "--------------------------------- "
            + "Display all Contact ----------------------------";
    public static final String TITLE_DELETE = "------- Delete a Contact -------";

    // Thong bao loi: sai gi + phai nhap the nao
    public static final String INVALID_CHOICE = "Please choice one option from 1 to 4.";
    public static final String FIELD_BLANK = "%s must not be blank.";

    // Phone sai dinh dang: in ra 7 dang dung cua de (• la dau cham tron)
    public static final String INVALID_PHONE = "Please input Phone flow\n"
            + "• 1234567890\n"
            + "• 123-456-7890\n"
            + "• 123-456-7890 x1234\n"
            + "• 123-456-7890 ext1234\n"
            + "• (123)-456-7890\n"
            + "• 123.456.7890\n"
            + "• 123 456 7890";

    // ID khong phai so nguyen duong (chu cua de)
    public static final String ID_DIGIT = "ID is digit";

    // Khong tim thay contact (chu cua de)
    public static final String NOT_FOUND = "No found contact";

    // Thong bao thanh cong (chu cua de)
    public static final String SUCCESSFUL = "Successful";
}
