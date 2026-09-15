package model;

import constants.Constants;
import constants.Message;
import java.util.Locale;

/**
 * MODEL: a cube, a three-dimensional shape given by its side.
 *
 * @author HE176322
 */
public class Cube extends ThreeDimensionalShape {

    // Side (edge) of the cube.
    private double side;

    // JavaBean constructor: an empty cube, filled through the setter.
    public Cube() {
    }

    // Creates a cube with its side.
    public Cube(double side) {
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

    // Surface area of a cube: A = 6 * s^2 (six square faces).
    @Override
    public double getArea() {
        return Constants.CUBE_FACES * side * side;
    }

    // Volume of a cube: V = s^3.
    @Override
    public double getVolume() {
        return Math.pow(side, Constants.CUBE_POWER);
    }

    // Description for the report.
    @Override
    public String toString() {
        return String.format(Locale.US, Message.CUBE_TEXT, side);
    }
}
