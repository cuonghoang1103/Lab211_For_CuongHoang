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

    // lua chon nho nhat cua menu
    public static final int MENU_MIN = 1;

    // lua chon tieng Viet
    public static final int MENU_VIETNAMESE = 1;

    // lua chon tieng Anh
    public static final int MENU_ENGLISH = 2;

    // lua chon thoat, cung la lua chon lon nhat
    public static final int MENU_EXIT = 3;

    // ma ngon ngu tieng Viet (tim file Language_vi.properties)
    public static final String LANGUAGE_VI = "vi";

    // ma ngon ngu tieng Anh (tim file Language_en.properties)
    public static final String LANGUAGE_EN = "en";

    // ten goc cua 2 file ngon ngu trong package constants
    public static final String BUNDLE_NAME = "constants.Language";

    // so tai khoan: dung 10 chu so
    public static final String ACCOUNT_REGEX = "[0-9]{10}";

    // mat khau: 8-31 ky tu chu/so, it nhat 1 chu va 1 so
    public static final String PASSWORD_REGEX = "(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,31}";

    // cac ky tu dung de sinh captcha
    public static final String CAPTCHA_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // so ky tu cua captcha
    public static final int CAPTCHA_LENGTH = 5;
}
