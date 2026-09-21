package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- report frame -----
    // The ruler above and below the header and below the last row.
    public static final String LINE
            = "=============================================================";

    // Header label of the number column.
    public static final String LABEL_NO = "No";

    // Header label of the shape column.
    public static final String LABEL_SHAPE = "Shape";

    // Header label of the area column.
    public static final String LABEL_AREA = "Area";

    // Header label of the volume column.
    public static final String LABEL_VOLUME = "Volume";

    // Shown in the volume column of a two-dimensional shape.
    public static final String NO_VOLUME = "-";

    // ----- shape descriptions (toString of each concrete shape) -----
    // Circle description; %.2f is the radius.
    public static final String CIRCLE_TEXT = "Circle [r=%.2f]";

    // Square description; %.2f is the side.
    public static final String SQUARE_TEXT = "Square [side=%.2f]";

    // Triangle description; %.2f are the base and the height.
    public static final String TRIANGLE_TEXT = "Triangle [base=%.2f, h=%.2f]";

    // Sphere description; %.2f is the radius.
    public static final String SPHERE_TEXT = "Sphere [r=%.2f]";

    // Cube description; %.2f is the side.
    public static final String CUBE_TEXT = "Cube [side=%.2f]";

    // Tetrahedron description; %.2f is the side.
    public static final String TETRAHEDRON_TEXT = "Tetrahedron [side=%.2f]";

    // ----- programming error -----
    // A ShapeType was added without a case in ShapeFactory; %s is the type.
    public static final String UNKNOWN_SHAPE = "Unknown shape type: %s";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
