package repository;

import model.Shape;

/**
 * REPOSITORY: holds the data of the program - the brief's single array of Shape
 * references - and only simple CRUD on it. No formula, no print.
 *
 * @author HE176322
 */
public class ShapeRepository {

    // The brief's "single Shape[] array": one object of each concrete class. The type is
    // the parent class Shape, the objects are the subclasses.
    private Shape[] shapeArray;

    // Creates an empty store.
    public ShapeRepository() {
        shapeArray = new Shape[0];
    }

    // Create: keeps the array of shapes in place of any earlier one.
    public void saveShapeArray(Shape[] shapeArray) {
        this.shapeArray = shapeArray;
    }

    // Read: returns the array kept by the last save.
    public Shape[] getShapeArray() {
        return shapeArray;
    }
}
