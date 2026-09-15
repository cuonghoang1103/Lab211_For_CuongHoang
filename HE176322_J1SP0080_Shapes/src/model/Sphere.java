package model;

import constants.Constants;
import constants.Message;
import java.util.Locale;

/**
 * MODEL: a sphere, a three-dimensional shape given by its radius.
 *
 * @author HE176322
 */
public class Sphere extends ThreeDimensionalShape {

    // Radius of the sphere.
    private double radius;

    // JavaBean constructor: an empty sphere, filled through the setter.
    public Sphere() {
    }

    // Creates a sphere with its radius.
    public Sphere(double radius) {
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

    // Surface area of a sphere: A = 4 * PI * r^2.
    @Override
    public double getArea() {
        return Constants.SPHERE_AREA_FACTOR * Math.PI * Math.pow(radius, 2);
    }

    // Volume of a sphere: V = (4 / 3) * PI * r^3 (4.0 / 3.0 in Constants, never the
    // integer division 4 / 3).
    @Override
    public double getVolume() {
        return Constants.SPHERE_VOLUME_FACTOR * Math.PI
                * Math.pow(radius, Constants.CUBE_POWER);
    }

    // Description for the report.
    @Override
    public String toString() {
        return String.format(Locale.US, Message.SPHERE_TEXT, radius);
    }
}
