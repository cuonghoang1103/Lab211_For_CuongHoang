package constants;

/**
 * The three number systems of the brief, each with its radix and the short name printed
 * in the result - a LOOKUP TABLE per base.
 *
 * @author HE176322
 */
public enum Base {

    // Binary: digits 0-1; menu number 1.
    BINARY(2, "BIN"),
    // Decimal: digits 0-9; menu number 2.
    DECIMAL(10, "DEC"),
    // Hexadecimal: digits 0-9 and A-F; menu number 3.
    HEXADECIMAL(16, "HEX");

    // How many different digits the system has (2, 10 or 16).
    private final int radix;
    // Short name printed after a value.
    private final String label;

    // Creates one base constant; private because only the three constants above may
    // exist.
    private Base(int radix, String label) {
        this.radix = radix;
        this.label = label;
    }

    // Returns the radix.
    public int getRadix() {
        return radix;
    }

    // Returns the short name.
    public String getLabel() {
        return label;
    }

    // Menu number -> base (1 = binary, 2 = decimal, 3 = hexadecimal, the brief's example
    // numbering).
    public static Base fromChoice(int choice) {
        return values()[choice - 1];
    }
}
