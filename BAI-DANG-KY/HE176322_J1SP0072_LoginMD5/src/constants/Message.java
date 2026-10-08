package constants;

/**
 * Chua moi cau chu hien ra man hinh.
 *
 * @author HE176322
 */
public final class Message {

    // constructor private: lop chi chua hang so
    private Message() {
    }

    // menu chinh (chep dung de, ke ca "3) Exit")
    public static final String MENU = "============ Login Program =========\n"
            + "1. Add User\n"
            + "2. Login\n"
            + "3) Exit";

    // cau hoi chon menu
    public static final String INPUT_CHOICE = "Please choice one option:";

    // tieu de man them user
    public static final String TITLE_ADD = "---------- Add User --------";

    // cau hoi username o man them user (de khong co dau cach sau dau :)
    public static final String INPUT_ADD_ACCOUNT = "Account:";

    // cau hoi mat khau o man them user
    public static final String INPUT_ADD_PASSWORD = "Password:";

    // cau hoi ho ten
    public static final String INPUT_NAME = "Name:";

    // cau hoi so dien thoai
    public static final String INPUT_PHONE = "Phone:";

    // cau hoi email
    public static final String INPUT_EMAIL = "Email:";

    // cau hoi dia chi
    public static final String INPUT_ADDRESS = "Address:";

    // cau hoi ngay sinh
    public static final String INPUT_DOB = "DOB:";

    // tieu de man dang nhap
    public static final String TITLE_LOGIN = "------------- Login ----------------";

    // cau hoi username o man dang nhap (de co dau cach sau dau :)
    public static final String INPUT_LOGIN_ACCOUNT = "Account: ";

    // cau hoi mat khau o man dang nhap
    public static final String INPUT_LOGIN_PASSWORD = "Password: ";

    // man chao sau khi dang nhap dung: %s dau la username, %s sau la name
    public static final String WELCOME = "------------ Wellcome -----------\n"
            + "Hello %s\n"
            + "Hi %s, do you want change password now? Y/N:";

    // cau hoi mat khau cu
    public static final String INPUT_OLD_PASSWORD = "Old password:";

    // cau hoi mat khau moi
    public static final String INPUT_NEW_PASSWORD = "new password:";

    // cau hoi nhap lai mat khau moi
    public static final String INPUT_RENEW_PASSWORD = "renew password:";

    // loi: lua chon menu khong phai so
    public static final String INVALID_NUMBER = "You must input a number.";

    // loi: lua chon menu ngoai 1-3
    public static final String INVALID_RANGE = "Please choose from 1 to 3.";

    // loi: username rong
    public static final String USERNAME_EMPTY = "Username cannot be empty. Please enter a username.";

    // loi: mat khau rong
    public static final String PASSWORD_EMPTY = "Password cannot be empty. Please enter a password.";

    // loi: ho ten rong
    public static final String NAME_EMPTY = "Name cannot be empty. Please enter your name.";

    // loi: so dien thoai rong
    public static final String PHONE_EMPTY = "Phone number cannot be empty. Please enter 10 or 11 digits.";

    // loi: so dien thoai khong phai 10-11 chu so
    public static final String PHONE_INVALID = "Phone number must be 10 or 11 digits only (e.g. 0988666888). Please enter again.";

    // loi: email rong
    public static final String EMAIL_EMPTY = "Email cannot be empty. Please enter an email like name@domain.com.";

    // loi: email sai dinh dang
    public static final String EMAIL_INVALID = "Email is not in the correct format. Please enter like name@domain.com.";

    // loi: ngay sinh rong
    public static final String DOB_EMPTY = "Date of birth cannot be empty. Please enter in format dd/MM/yyyy.";

    // loi: ngay sinh khong phai ngay that dd/MM/yyyy
    public static final String DOB_INVALID = "Date of birth must be a real date in format dd/MM/yyyy (e.g. 26/06/2016). Please enter again.";

    // loi: username da ton tai, %s la username da luu
    public static final String USERNAME_EXIST = "Username [%s] already exists. Please choose 1 again and use another username.";

    // loi: sai username hoac mat khau
    public static final String LOGIN_FAIL = "Login fail. Account or password is not correct, please choose 2 to try again.";

    // loi: mat khau moi rong
    public static final String NEW_PASSWORD_EMPTY = "New password cannot be empty. Please enter a new password.";

    // loi: nhap lai mat khau moi khong khop
    public static final String PASSWORD_MISMATCH = "Renew password does not match new password. Please enter both again.";

    // loi: mat khau cu sai
    public static final String OLD_PASSWORD_WRONG = "Old password is not correct. Password was not changed, please login and try again.";

    // ket qua them user: %s la username, %d la id
    public static final String ADD_SUCCESS = "Account [%s] has been added with id %d.";

    // ket qua doi mat khau thanh cong
    public static final String CHANGE_SUCCESS = "Password has been changed.";

    // cau chao khi thoat
    public static final String GOODBYE = "Goodbye.";
}
