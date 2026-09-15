package model;

/**
 * MODEL: a solid shape - it has a surface area AND a volume.
 *
 * @author HE176322
 */
public abstract class ThreeDimensionalShape extends Shape {

    // The volume (brief: "ThreeDimensionalShape adds an abstract method getVolume()").
    public abstract double getVolume();
}
