package constants;

public enum Base {

    // Nhi phan: chon 1, co so 2
    BINARY(1, 2, "BIN"),

    // Thap phan: chon 2, co so 10
    DECIMAL(2, 10, "DEC"),

    // Thap luc phan: chon 3, co so 16
    HEXADECIMAL(3, 16, "HEX");

    // So nguoi dung chon o menu
    private int choice;

    // Co so (2, 10, 16)
    private int radix;

    // Nhan in ra man hinh (BIN, DEC, HEX)
    private String label;

    // Constructor cua enum
    private Base(int choice, int radix, String label) {
        this.choice = choice;
        this.radix = radix;
        this.label = label;
    }

    // Lay co so
    public int getRadix() {
        return radix;
    }

    // Lay nhan
    public String getLabel() {
        return label;
    }

    // Doi so nguoi dung chon sang he co so tuong ung
    public static Base fromChoice(int choice) {
        // Duyet tung he co so
        for (Base base : values()) {
            // Trung so chon thi tra ve
            if (base.choice == choice) {
                return base;
            }
        }
        return null;
    }
}
