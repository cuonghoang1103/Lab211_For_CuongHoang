package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: a rectangle, described by its width and length.
 *
 * @author HE176322
 */
public class Rectangle extends Shape {

    // Width of the rectangle.
    private double width;
    // Length of the rectangle.
    private double length;

    // JavaBean constructor: an empty rectangle, filled through the setters.
    public Rectangle() {
    }

    // Creates a rectangle with both sides.
    public Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
    }

    // Returns the width.
    public double getWidth() {
        return width;
    }

    // Changes the width.
    public void setWidth(double width) {
        this.width = width;
    }

    // Returns the length.
    public double getLength() {
        return length;
    }

    // Changes the length.
    public void setLength(double length) {
        this.length = length;
    }

    // Perimeter of a rectangle: 2 * (width + length).
    @Override
    public double getPerimeter() {
        return 2 * (width + length);
    }

    // Area of a rectangle: width * length.
    @Override
    public double getArea() {
        return width * length;
    }

    // Template step: the title line.
    @Override
    protected String getTitle() {
        return Message.TITLE_RECTANGLE;
    }

    // Template step: the width and length lines.
    @Override
    protected String getProperties() {
        return Message.LABEL_WIDTH + width + Constants.NEW_LINE
                + Message.LABEL_LENGTH + length;
    }

    // Hook overridden: the rectangle prints "Area: " with a space.
    @Override
    protected String getAreaLabel() {
        return Message.LABEL_RECTANGLE_AREA;
    }

    // Hook overridden: the rectangle prints "Perimeter: " with a space.
    @Override
    protected String getPerimeterLabel() {
        return Message.LABEL_RECTANGLE_PERIMETER;
    }
}
