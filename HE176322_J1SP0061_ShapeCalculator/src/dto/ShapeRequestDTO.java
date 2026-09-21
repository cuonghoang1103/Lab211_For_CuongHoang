package dto;

/**
 * DTO carrying the six lengths the user typed FROM main INTO the controller.
 *
 * @author HE176322
 */
public class ShapeRequestDTO {

    // Width of the rectangle.
    private double width;

    // Length of the rectangle.
    private double length;

    // Radius of the circle.
    private double radius;

    // Side A of the triangle.
    private double sideA;

    // Side B of the triangle.
    private double sideB;

    // Side C of the triangle.
    private double sideC;

    // JavaBean constructor: an empty request; main fills it with setters.
    public ShapeRequestDTO() {
    }

    // Returns the rectangle width.
    public double getWidth() {
        return width;
    }

    // Sets the rectangle width.
    public void setWidth(double width) {
        this.width = width;
    }

    // Returns the rectangle length.
    public double getLength() {
        return length;
    }

    // Sets the rectangle length.
    public void setLength(double length) {
        this.length = length;
    }

    // Returns the circle radius.
    public double getRadius() {
        return radius;
    }

    // Sets the circle radius.
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Returns side A of the triangle.
    public double getSideA() {
        return sideA;
    }

    // Sets side A of the triangle.
    public void setSideA(double sideA) {
        this.sideA = sideA;
    }

    // Returns side B of the triangle.
    public double getSideB() {
        return sideB;
    }

    // Sets side B of the triangle.
    public void setSideB(double sideB) {
        this.sideB = sideB;
    }

    // Returns side C of the triangle.
    public double getSideC() {
        return sideC;
    }

    // Sets side C of the triangle.
    public void setSideC(double sideC) {
        this.sideC = sideC;
    }
}
