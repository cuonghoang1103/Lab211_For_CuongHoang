package service;

import constants.Constants;
import constants.Message;
import constants.ShapeType;
import model.Circle;
import model.Cube;
import model.Shape;
import model.Sphere;
import model.Square;
import model.Tetrahedron;
import model.Triangle;

/**
 * FACTORY (design pattern): the ONE place that knows "shape type X -> new class X with
 * its sample size".
 *
 * @author HE176322
 */
public class ShapeFactory {

    // Creates the factory; it keeps no state.
    public ShapeFactory() {
    }

    // Creates the sample shape of the given type, with the sizes of the brief's example
    // (Constants).
    public Shape createShape(ShapeType type) {
        // one case per concrete class of the brief's figure
        switch (type) {
            // two-dimensional: circle of radius 2
            case CIRCLE:
                return new Circle(Constants.CIRCLE_RADIUS);

            // two-dimensional: square of side 3
            case SQUARE:
                return new Square(Constants.SQUARE_SIDE);

            // two-dimensional: triangle of base 4 and height 5
            case TRIANGLE:
                return new Triangle(Constants.TRIANGLE_BASE, Constants.TRIANGLE_HEIGHT);

            // three-dimensional: sphere of radius 2
            case SPHERE:
                return new Sphere(Constants.SPHERE_RADIUS);

            // three-dimensional: cube of side 3
            case CUBE:
                return new Cube(Constants.CUBE_SIDE);

            // three-dimensional: regular tetrahedron of side 4
            case TETRAHEDRON:
                return new Tetrahedron(Constants.TETRAHEDRON_SIDE);

            // a new ShapeType nobody taught the factory to build
            default:
                throw new IllegalArgumentException(String.format(Message.UNKNOWN_SHAPE,
                        type));
        }
    }
}
