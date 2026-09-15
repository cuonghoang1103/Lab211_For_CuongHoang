package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest legal mark (the brief's messages: "greater than equal zero").
    public static final double MIN_MARK = 0;
    // Largest legal mark (the brief's messages: "less than equal ten").
    public static final double MAX_MARK = 10;
    // Number of subjects averaged: maths, chemistry, physics.
    public static final int NUMBER_OF_SUBJECTS = 3;

    // Type A: average strictly above this.
    public static final double TYPE_A_ABOVE = 7.5;
    // Type B: average from this up to 7.5.
    public static final double TYPE_B_FROM = 6;
    // Type C: average from this up to (not including) 6.
    public static final double TYPE_C_FROM = 4;

    // Key of type A.
    public static final String TYPE_A = "A";
    // Key of type B.
    public static final String TYPE_B = "B";
    // Key of type C.
    public static final String TYPE_C = "C";
    // Key of type D.
    public static final String TYPE_D = "D";
    // The four types, in the order the statistics are printed.
    public static final String[] TYPES = {TYPE_A, TYPE_B, TYPE_C, TYPE_D};

    // A share of 1 is 100 percent.
    public static final double PERCENT = 100;
    // Multiply, round, divide by this to keep one decimal.
    public static final double ROUND_FACTOR = 10.0;
    // How an average or a percent is printed: one decimal.
    public static final String ONE_DECIMAL = "%.1f";

    // Answer that asks for another student.
    public static final String YES = "Y";
    // Answer that ends the input.
    public static final String NO = "N";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
