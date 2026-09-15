package model;

import constants.Message;
import java.util.Locale;

/**
 * MODEL: a square, a two-dimensional shape given by its side.
 *
 * @author HE176322
 */
public class Square extends TwoDimensionalShape {

    // Side of the square.
    private double side;

    // JavaBean constructor: an empty square, filled through the setter.
    public Square() {
    }

    // Creates a square with its side.
    public Square(double side) {
        this.side = side;
    }

    // Returns the side.
    public double getSide() {
        return side;
    }

    // Changes the side.
    public void setSide(double side) {
        this.side = side;
    }

    // Area of a square: A = s^2.
    @Override
    public double getArea() {
        return side * side;
    }

    // Description for the report.
    @Override
    public String toString() {
        return String.format(Locale.US, Message.SQUARE_TEXT, side);
    }
}
