package constants;

/**
 * Cac so va luat co dinh cua chuong trinh.
 *
 * @author HE176322
 */
public final class Constants {

    // constructor private: lop chi chua hang so
    private Constants() {
    }

    // lua chon menu: them user
    public static final int MENU_ADD = 1;

    // lua chon menu: dang nhap
    public static final int MENU_LOGIN = 2;

    // lua chon menu: thoat, cung la lua chon lon nhat
    public static final int MENU_EXIT = 3;

    // dinh dang ngay sinh
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // so dien thoai: chi gom 10 hoac 11 chu so
    public static final String PHONE_REGEX = "\\d{10,11}";

    // email dang ten@mien.duoi
    public static final String EMAIL_REGEX = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";

    // ten thuat toan bam mat khau
    public static final String HASH_ALGORITHM = "MD5";

    // tra loi Y thi doi mat khau
    public static final String YES = "Y";
}
