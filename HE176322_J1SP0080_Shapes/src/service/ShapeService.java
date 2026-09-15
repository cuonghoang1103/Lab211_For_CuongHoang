package service;

import constants.ShapeType;
import dto.ShapeResponseDTO;
import java.util.ArrayList;
import model.Shape;
import model.ThreeDimensionalShape;

/**
 * SERVICE: builds the array of shapes and computes every row of the report.
 *
 * @author HE176322
 */
public class ShapeService {

    // Creates the right subclass for each ShapeType.
    private ShapeFactory shapeFactory;

    // Creates the service with its factory.
    public ShapeService() {
        shapeFactory = new ShapeFactory();
    }

    // The brief's Function 3: one Shape[] array, ONE loop over it.
    public ArrayList<ShapeResponseDTO> getShapeReport() {
        Shape[] shapes = createShapes();
        ArrayList<ShapeResponseDTO> rows = new ArrayList<>();
        // one row per shape, numbered from 1
        for (int i = 0; i < shapes.length; i++) {
            Shape shape = shapes[i];
            ShapeResponseDTO row = new ShapeResponseDTO();
            row.setNo(i + 1);
            row.setDescription(shape.toString());
            row.setArea(shape.getArea());
            // three-dimensional: it also has a volume
            if (shape instanceof ThreeDimensionalShape) {
                ThreeDimensionalShape solid = (ThreeDimensionalShape) shape;
                row.setThreeDimensional(true);
                row.setVolume(solid.getVolume());
            } else {
                // two-dimensional: the area is all there is
                row.setThreeDimensional(false);
            }
            rows.add(row);
        }
        return rows;
    }

    // The brief: "Create an array of Shape references and populate it with one object of
    // each concrete class".
    private Shape[] createShapes() {
        ShapeType[] types = ShapeType.values();
        Shape[] shapes = new Shape[types.length];
        // one shape for each type, in the enum's order
        for (int i = 0; i < types.length; i++) {
            shapes[i] = shapeFactory.createShape(types[i]);
        }
        return shapes;
    }
}
