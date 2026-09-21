package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- input screen -----
    // Title printed when the program starts.
    public static final String TITLE = "=====Calculator Shape Program=====";

    // Prompt for the rectangle width.
    public static final String INPUT_WIDTH = "Please input side width of Rectangle:";

    // Prompt for the rectangle length.
    public static final String INPUT_LENGTH = "Please input length of Rectangle:";

    // Prompt for the circle radius.
    public static final String INPUT_RADIUS = "Please input radius of Circle:";

    // Prompt for side A of the triangle.
    public static final String INPUT_SIDE_A = "Please input side A of Triangle:";

    // Prompt for side B of the triangle.
    public static final String INPUT_SIDE_B = "Please input side B of Triangle:";

    // Prompt for side C of the triangle.
    public static final String INPUT_SIDE_C = "Please input side C of Triangle:";

    // ----- validation errors -----
    // The line typed was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // A length of zero or less.
    public static final String INVALID_POSITIVE = "Value must be greater than zero.";

    // Three sides that break the triangle inequality.
    public static final String INVALID_TRIANGLE
            = "These three sides cannot form a triangle. Please input again.";

    // ----- result screen: Rectangle -----
    // Title of the rectangle block.
    public static final String TITLE_RECTANGLE = "-----Rectangle-----";

    // Label of the width line.
    public static final String LABEL_WIDTH = "Width: ";

    // Label of the length line.
    public static final String LABEL_LENGTH = "Length: ";

    // The rectangle's area label - WITH a space, as in the brief.
    public static final String LABEL_RECTANGLE_AREA = "Area: ";

    // The rectangle's perimeter label - WITH a space, as in the brief.
    public static final String LABEL_RECTANGLE_PERIMETER = "Perimeter: ";

    // ----- result screen: Circle -----
    // Title of the circle block.
    public static final String TITLE_CIRCLE = "-----Circle-----";

    // Label of the radius line.
    public static final String LABEL_RADIUS = "Radius: ";

    // ----- result screen: Triangle -----
    // Title of the triangle block.
    public static final String TITLE_TRIANGLE = "-----Triangle-----";

    // Label of the side A line.
    public static final String LABEL_SIDE_A = "Side A: ";

    // Label of the side B line.
    public static final String LABEL_SIDE_B = "Side B: ";

    // Label of the side C line.
    public static final String LABEL_SIDE_C = "Side C: ";

    // ----- result screen: shared by Circle and Triangle -----
    // The default area label - NO space, as the brief prints it.
    public static final String LABEL_AREA = "Area:";

    // The default perimeter label - NO space, as the brief prints it.
    public static final String LABEL_PERIMETER = "Perimeter:";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
