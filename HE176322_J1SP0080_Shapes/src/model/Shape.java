package model;

/**
 * MODEL: the top of the hierarchy in the brief's figure.
 *
 * @author HE176322
 */
public abstract class Shape {

    // The area (surface area for a three-dimensional shape).
    public abstract double getArea();

    // Forces every concrete shape to override Object.toString() with its description.
    @Override
    public abstract String toString();
}
