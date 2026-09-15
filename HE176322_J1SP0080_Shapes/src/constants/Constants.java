package constants;

/**
 * Numbers and formats the program logic depends on: the sample sizes, the numbers inside
 * the formulas, and the column layout of the report.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- the sample shapes of the brief (Guidelines: "Shape[] shapes = ...") -----
    // Radius of the sample circle.
    public static final double CIRCLE_RADIUS = 2;
    // Side of the sample square.
    public static final double SQUARE_SIDE = 3;
    // Base of the sample triangle.
    public static final double TRIANGLE_BASE = 4;
    // Height of the sample triangle.
    public static final double TRIANGLE_HEIGHT = 5;
    // Radius of the sample sphere.
    public static final double SPHERE_RADIUS = 2;
    // Side of the sample cube.
    public static final double CUBE_SIDE = 3;
    // Side (edge) of the sample regular tetrahedron.
    public static final double TETRAHEDRON_SIDE = 4;

    // ----- numbers inside the formulas (brief, Guidelines "Formulas") -----
    // Sphere surface A = 4 * PI * r^2.
    public static final double SPHERE_AREA_FACTOR = 4;
    // Sphere volume V = (4 / 3) * PI * r^3.
    public static final double SPHERE_VOLUME_FACTOR = 4.0 / 3.0;
    // A cube has 6 faces: A = 6 * s^2.
    public static final double CUBE_FACES = 6;
    // Power of a volume: r^3, s^3.
    public static final double CUBE_POWER = 3;
    // Tetrahedron surface A = sqrt(3) * s^2.
    public static final double TETRAHEDRON_AREA_ROOT = 3;
    // Tetrahedron volume V = s^3 / (6 * sqrt(2)).
    public static final double TETRAHEDRON_VOLUME_FACTOR = 6;

    // ----- report layout, measured on the brief's sample output -----
    // Every real number is printed with two decimals (brief).
    public static final String NUMBER_FORMAT = "%.2f";
    // Header: "No", "Shape", "Area" and "Volume" as in the brief.
    public static final String HEADER_FORMAT = "%-4s%-29s%6s%14s";
    // Row of a 2-D shape: no, description, area, "-" under the volume.
    public static final String ROW_2D_FORMAT = "%-3d%-30s%6s%11s";
    // Row of a 3-D shape: no, description, area, volume.
    public static final String ROW_3D_FORMAT = "%-3d%-30s%6s%13s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
