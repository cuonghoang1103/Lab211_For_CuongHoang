package model;

import constants.Message;
import java.util.Locale;

/**
 * MODEL: a triangle, a two-dimensional shape given by its base and height.
 *
 * @author HE176322
 */
public class Triangle extends TwoDimensionalShape {

    // Length of the base.
    private double base;
    // Height measured from the base.
    private double height;

    // JavaBean constructor: an empty triangle, filled through the setters.
    public Triangle() {
    }

    // Creates a triangle with its base and height.
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    // Returns the base.
    public double getBase() {
        return base;
    }

    // Changes the base.
    public void setBase(double base) {
        this.base = base;
    }

    // Returns the height.
    public double getHeight() {
        return height;
    }

    // Changes the height.
    public void setHeight(double height) {
        this.height = height;
    }

    // Area of a triangle: A = 1/2 * base * height, written base * height / 2 so no
    // fraction literal is needed.
    @Override
    public double getArea() {
        return base * height / 2;
    }

    // Description for the report.
    @Override
    public String toString() {
        return String.format(Locale.US, Message.TRIANGLE_TEXT, base, height);
    }
}
