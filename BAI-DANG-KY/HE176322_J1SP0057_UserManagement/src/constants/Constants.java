package constants;

public final class Constants {

    // Constructor private: lop chi chua hang so, khong cho tao object
    private Constants() {
    }

    // So thu tu cac chuc nang tren menu
    public static final int MENU_CREATE = 1;
    public static final int MENU_LOGIN = 2;
    public static final int MENU_EXIT = 3;

    // Do dai toi thieu cua username va password
    public static final int USERNAME_MIN_LENGTH = 5;
    public static final int PASSWORD_MIN_LENGTH = 6;

    // Ten file luu tai khoan (o thu muc goc project)
    public static final String DATA_FILE = "user.dat";

    // Dau cach ngan username va password tren 1 dong file
    public static final String SEPARATOR = " ";
}
