package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: a triangle, described by its three sides.
 *
 * @author HE176322
 */
public class Triangle extends Shape {

    // Length of side A.
    private double sideA;

    // Length of side B.
    private double sideB;

    // Length of side C.
    private double sideC;

    // JavaBean constructor: an empty triangle, filled through the setters.
    public Triangle() {
    }

    // Creates a triangle with its three sides (constructors may take three parameters -
    // the Guide's own Doctor constructor takes four).
    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    // Returns side A.
    public double getSideA() {
        return sideA;
    }

    // Changes side A.
    public void setSideA(double sideA) {
        this.sideA = sideA;
    }

    // Returns side B.
    public double getSideB() {
        return sideB;
    }

    // Changes side B.
    public void setSideB(double sideB) {
        this.sideB = sideB;
    }

    // Returns side C.
    public double getSideC() {
        return sideC;
    }

    // Changes side C.
    public void setSideC(double sideC) {
        this.sideC = sideC;
    }

    // Perimeter of a triangle: a + b + c.
    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    // Area by Heron's formula: S = sqrt(p(p-a)(p-b)(p-c)), where p is HALF the perimeter.
    @Override
    public double getArea() {
        double halfPerimeter = getPerimeter() / 2;
        double heronProduct = halfPerimeter * (halfPerimeter - sideA)
                * (halfPerimeter - sideB) * (halfPerimeter - sideC);

        return Math.sqrt(heronProduct);
    }

    // Template step: the title line.
    @Override
    protected String getTitle() {
        return Message.TITLE_TRIANGLE;
    }

    // Template step: the three side lines.
    @Override
    protected String getProperties() {
        return String.format(Constants.THREE_LINES_FORMAT, Message.LABEL_SIDE_A, sideA,
                Message.LABEL_SIDE_B, sideB, Message.LABEL_SIDE_C, sideC);
    }
}
