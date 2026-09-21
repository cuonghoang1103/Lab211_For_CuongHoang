package constants;

/**
 * Numbers the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Menu option: normal calculator; also the smallest option.
    public static final int MENU_NORMAL = 1;

    // Menu option: BMI calculator.
    public static final int MENU_BMI = 2;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // Centimetres in one metre: the brief asks for cm, the formula uses m.
    public static final double CM_PER_METRE = 100.0;

    // Lowest BMI of the STANDARD band (below it: UNDER-STANDARD).
    public static final double BMI_STANDARD_MIN = 19;

    // Lowest BMI of the OVERWEIGHT band.
    public static final double BMI_OVERWEIGHT_MIN = 25;

    // Lowest BMI of the FAT band.
    public static final double BMI_FAT_MIN = 30;

    // Lowest BMI of the VERY FAT band.
    public static final double BMI_VERY_FAT_MIN = 40;

    // The Guidelines spell multiplication "x", the screen spells it "*".
    public static final String MULTIPLY_ALIAS = "x";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
