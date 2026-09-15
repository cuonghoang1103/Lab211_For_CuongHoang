package constants;

/**
 * The brief's Enum Color: "List all colors of car in showroom, and 'no color' option".
 *
 * @author HE176322
 */
public enum Color {

    // The unpainted car: $100 cheaper (the brief).
    NO_COLOR("no color"),
    // An AUDI colour.
    WHITE("WHITE"),
    // An AUDI colour.
    YELLOW("YELLOW"),
    // An AUDI colour.
    ORANGE("ORANGE"),
    // A MERCEDES colour.
    GREEN("GREEN"),
    // A MERCEDES colour.
    BLUE("BLUE"),
    // A MERCEDES colour.
    PURPLE("PURPLE"),
    // A BMW colour.
    PINK("PINK"),
    // A BMW colour.
    RED("RED"),
    // A BMW colour.
    BROWN("BROWN");

    // The text the customer types for this colour.
    private final String label;

    // Creates one colour constant (enum constructors are always private).
    Color(String label) {
        this.label = label;
    }

    // Returns the text the customer types for this colour.
    public String getLabel() {
        return label;
    }

    // The brief's lookup "Color getColor(String color), if color is not a Color Enum then
    // return null".
    public static Color getColor(String color) {
        // nothing typed: no colour
        if (color == null) {
            return null;
        }
        // compare the text with the label of every colour
        for (Color candidate : values()) {
            // found the colour the user meant
            if (candidate.label.equalsIgnoreCase(color.trim())) {
                return candidate;
            }
        }
        return null;
    }
}
