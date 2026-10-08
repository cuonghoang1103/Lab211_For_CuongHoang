package constants;

public final class Constants {

    // Constructor private: lop chi chua hang so, khong cho tao object
    private Constants() {
    }

    // So thu tu cac chuc nang tren menu
    public static final int MENU_ADD = 1;
    public static final int MENU_DISPLAY = 2;
    public static final int MENU_DELETE = 3;
    public static final int MENU_EXIT = 4;

    // ID cua contact dau tien (de: "the first contact has ID: 1")
    public static final int FIRST_ID = 1;

    // 7 dang phone cua de, moi dang mot nhanh noi bang dau |
    public static final String PHONE_PATTERN = "\\d{10}"
            + "|\\d{3}-\\d{3}-\\d{4}( (x|ext)\\d{4})?"
            + "|\\(\\d{3}\\)-\\d{3}-\\d{4}"
            + "|\\d{3}\\.\\d{3}\\.\\d{4}"
            + "|\\d{3} \\d{3} \\d{4}";
}
