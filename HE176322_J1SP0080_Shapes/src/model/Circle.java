package model;

import constants.Message;
import java.util.Locale;

/**
 * MODEL: a circle, a two-dimensional shape given by its radius.
 *
 * @author HE176322
 */
public class Circle extends TwoDimensionalShape {

    // Radius of the circle.
    private double radius;

    // JavaBean constructor: an empty circle, filled through the setter.
    public Circle() {
    }

    // Creates a circle with its radius.
    public Circle(double radius) {
        this.radius = radius;
    }

    // Returns the radius.
    public double getRadius() {
        return radius;
    }

    // Changes the radius.
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Area of a circle: A = PI * r^2.
    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    // Description for the report, two decimals pinned to Locale.US so a Vietnamese
    // machine still prints "2.00" and not "2,00".
    @Override
    public String toString() {
        return String.format(Locale.US, Message.CIRCLE_TEXT, radius);
    }
}
