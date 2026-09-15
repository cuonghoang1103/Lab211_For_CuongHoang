package model;

import constants.Constants;
import constants.Message;
import java.util.Locale;

/**
 * MODEL: a regular tetrahedron (four equal triangular faces), a three-dimensional shape
 * given by its side.
 *
 * @author HE176322
 */
public class Tetrahedron extends ThreeDimensionalShape {

    // Side (edge) of the tetrahedron.
    private double side;

    // JavaBean constructor: an empty tetrahedron, filled through the setter.
    public Tetrahedron() {
    }

    // Creates a regular tetrahedron with its side.
    public Tetrahedron(double side) {
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

    // Surface area of a regular tetrahedron: A = sqrt(3) * s^2.
    @Override
    public double getArea() {
        return Math.sqrt(Constants.TETRAHEDRON_AREA_ROOT) * side * side;
    }

    // Volume of a regular tetrahedron: V = s^3 / (6 * sqrt(2)).
    @Override
    public double getVolume() {
        return Math.pow(side, Constants.CUBE_POWER)
                / (Constants.TETRAHEDRON_VOLUME_FACTOR * Math.sqrt(2));
    }

    // Description for the report.
    @Override
    public String toString() {
        return String.format(Locale.US, Message.TETRAHEDRON_TEXT, side);
    }
}
