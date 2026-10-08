package constants;

public final class Message {

    // Constructor private: lop chi chua hang so, khong cho tao doi tuong
    private Message() {
    }

    // Menu chinh
    public static final String MENU
            = "======= CHANGE BASE NUMBER SYSTEM =======\n"
            + "1. Binary (base 2)\n"
            + "2. Decimal (base 10)\n"
            + "3. Hexadecimal (base 16)\n"
            + "0. Exit\n"
            + "=========================================";

    // Cau nhac nhap lieu
    public static final String INPUT_BASE_IN = "Choose the INPUT base: ";
    public static final String INPUT_BASE_OUT = "Choose the OUTPUT base: ";
    public static final String INPUT_VALUE = "Enter the input value: ";

    // Thong bao loi: noi ro sai gi va phai nhap the nao
    public static final String INVALID_NUMBER = "You must input a number.";
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    public static final String EMPTY_VALUE = "You must input something.";
    public static final String INVALID_VALUE = "%s is not a valid %s number.";
    public static final String TOO_BIG = "The value is too big for this program.";

    // Dinh dang 1 dong ket qua: 535 (DEC) = 217 (HEX)
    public static final String RESULT_FORMAT = "%s (%s) = %s (%s)";

    // Loi chao khi thoat
    public static final String GOODBYE = "Goodbye.";
}
