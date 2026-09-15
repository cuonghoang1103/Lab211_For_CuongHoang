package constants;

/**
 * The five body-status bands of the brief, as an enum.
 *
 * @author HE176322
 */
public enum BMI {

    // BMI less than 19.
    UNDER_STANDARD("UNDER-STANDARD"),
    // BMI from 19 to below 25.
    STANDARD("STANDARD"),
    // BMI from 25 to below 30.
    OVERWEIGHT("OVERWEIGHT"),
    // BMI from 30 to below 40.
    FAT("FAT - SHOULD LOSE WEIGHT"),
    // BMI 40 or more.
    VERY_FAT("VERY FAT - SHOULD LOSE WEIGHT IMMEDIATELY");

    // The words printed on screen.
    private final String label;

    // Creates one status constant; private because only the five constants above may
    // exist.
    private BMI(String label) {
        this.label = label;
    }

    // Returns the words printed on screen.
    public String getLabel() {
        return label;
    }
}
