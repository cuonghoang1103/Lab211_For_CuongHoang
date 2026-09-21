package repository;

import dto.ShapeRequestDTO;
import java.util.ArrayList;
import model.Circle;
import model.Rectangle;
import model.Shape;
import model.Triangle;

/**
 * REPOSITORY: holds the data of the program - the three shapes built from the lengths the
 * user typed - and only simple CRUD on them. No formula, no print.
 *
 * @author HE176322
 */
public class ShapeRepository {

    // The shapes of this run, in the order of the brief's screen: rectangle, circle,
    // triangle. The type is the parent class Shape, the objects are the subclasses.
    private ArrayList<Shape> shapeList;

    // Creates an empty store.
    public ShapeRepository() {
        shapeList = new ArrayList<>();
    }

    // Create: turns the request into the three shapes (the models) and stores them in
    // place of any earlier ones.
    public void saveShapes(ShapeRequestDTO requestDTO) {
        Shape rectangle = new Rectangle(requestDTO.getWidth(), requestDTO.getLength());
        Shape circle = new Circle(requestDTO.getRadius());
        Shape triangle = new Triangle(requestDTO.getSideA(), requestDTO.getSideB(),
                requestDTO.getSideC());

        // forget the shapes of an earlier input, then keep the three new ones in the order
        // of the screen
        shapeList.clear();
        shapeList.add(rectangle);
        shapeList.add(circle);
        shapeList.add(triangle);
    }

    // Read: returns every stored shape.
    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }
}
