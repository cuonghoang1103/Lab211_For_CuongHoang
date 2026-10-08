package constants;

/**
 * Chua moi cau chu hien ra man hinh (cau doi theo ngon ngu thi chua KHOA cua no).
 *
 * @author HE176322
 */
public final class Message {

    // constructor private: lop chi chua hang so
    private Message() {
    }

    // menu chinh (luon tieng Anh vi chua chon ngon ngu)
    public static final String MENU = "-------Login Program-------\n"
            + "1. Vietnamese\n"
            + "2. English\n"
            + "3. Exit";

    // cau hoi chon menu (chep dung chu cua de)
    public static final String INPUT_CHOICE = "Please choice one option: ";

    // loi: go chu thay vi so o menu
    public static final String INVALID_NUMBER = "You must input a number.";

    // loi: so ngoai menu, phai chon tu 1 den 3
    public static final String INVALID_RANGE = "Please choose from 1 to 3.";

    // khoa cau hoi so tai khoan trong file .properties
    public static final String KEY_ACCOUNT_PROMPT = "account.prompt";

    // khoa cau loi so tai khoan (sai gi + phai nhap 10 chu so)
    public static final String KEY_ACCOUNT_ERROR = "account.error";

    // khoa cau hoi mat khau
    public static final String KEY_PASSWORD_PROMPT = "password.prompt";

    // khoa cau loi mat khau (phai 8-31 ky tu, co chu va so)
    public static final String KEY_PASSWORD_ERROR = "password.error";

    // khoa nhan dung truoc captcha
    public static final String KEY_CAPTCHA_LABEL = "captcha.label";

    // khoa cau hoi nhap captcha
    public static final String KEY_CAPTCHA_PROMPT = "captcha.prompt";

    // khoa cau loi captcha
    public static final String KEY_CAPTCHA_ERROR = "captcha.error";

    // khoa cau dang nhap thanh cong
    public static final String KEY_LOGIN_SUCCESS = "login.success";
}
