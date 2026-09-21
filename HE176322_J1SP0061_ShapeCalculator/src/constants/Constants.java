package constants;

/**
 * Numbers and layout pieces the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Every length must be strictly greater than this.
    public static final double MIN_LENGTH = 0;

    // One labelled line: the label, then the number exactly as Java prints it
    // (452.3893421169302 - no rounding).
    public static final String LINE_FORMAT = "%s%s";

    // Two labelled lines, one under the other (the width and the length of the rectangle).
    public static final String TWO_LINES_FORMAT = "%s%s\n%s%s";

    // Three labelled lines, one under the other (the three sides of the triangle).
    public static final String THREE_LINES_FORMAT = "%s%s\n%s%s\n%s%s";

    // A whole result block: the title line, the property lines, then the area line and the
    // perimeter line.
    public static final String RESULT_FORMAT = "%s\n%s\n%s%s\n%s%s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
