package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: the abstract base of the three shapes (the brief: "Create an abstract class
 * Shape contains three methods printResult, getPerimeter and getArea").
 *
 * @author HE176322
 */
public abstract class Shape {

    // The brief's Function 1: calculates the perimeter.
    public abstract double getPerimeter();

    // The brief's Function 2: calculates the area.
    public abstract double getArea();

    // Template step: the title line of this shape.
    protected abstract String getTitle();

    // Template step: the property lines of this shape.
    protected abstract String getProperties();

    // Hook: the label in front of the area.
    protected String getAreaLabel() {
        return Message.LABEL_AREA;
    }

    // Hook: the label in front of the perimeter; default "Perimeter:".
    protected String getPerimeterLabel() {
        return Message.LABEL_PERIMETER;
    }

    // The template method: builds the whole result block of the brief's screen.
    @Override
    public final String toString() {
        return getTitle() + Constants.NEW_LINE
                + getProperties() + Constants.NEW_LINE
                + getAreaLabel() + getArea() + Constants.NEW_LINE
                + getPerimeterLabel() + getPerimeter();
    }
}
