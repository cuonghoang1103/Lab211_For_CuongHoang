package constants;

/**
 * The four legal ranks of graduation (the brief: Excellence, Good, Fair, Poor).
 *
 * @author HE176322
 */
public enum GraduationRank {

    // Best rank.
    EXCELLENCE("Excellence"),
    // Second rank.
    GOOD("Good"),
    // Third rank.
    FAIR("Fair"),
    // Lowest rank.
    POOR("Poor");

    // Spelling printed on screen.
    private final String label;

    // Creates one constant.
    GraduationRank(String label) {
        this.label = label;
    }

    // Returns the spelling printed on screen.
    public String getLabel() {
        return label;
    }

    // Finds the rank whose label equals the text, ignoring upper/lower case.
    public static GraduationRank fromText(String text) {
        // compare the text with each of the four labels
        for (GraduationRank rank : values()) {
            // same word, whatever the capitals
            if (rank.label.equalsIgnoreCase(text)) {
                return rank;
            }
        }
        return null;
    }
}
