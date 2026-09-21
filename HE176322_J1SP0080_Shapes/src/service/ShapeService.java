package service;

import constants.ShapeType;
import dto.ReportResponseDTO;
import dto.ShapeResponseDTO;
import java.util.ArrayList;
import model.Shape;
import model.ThreeDimensionalShape;
import repository.ShapeRepository;

/**
 * SERVICE: builds the array of shapes, keeps it in the repository, and computes every row
 * of the report from it. Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class ShapeService {

    // Creates the right subclass for each ShapeType.
    private ShapeFactory shapeFactory;

    // Keeps the array of shapes (Service -> Repository -> Model).
    private ShapeRepository shapeRepository;

    // Creates the service together with its factory and its repository.
    public ShapeService() {
        shapeFactory = new ShapeFactory();
        shapeRepository = new ShapeRepository();
    }

    // The brief's Function 3: one Shape[] array, ONE loop over it - each shape gives its
    // description and area, and instanceof decides whether it also has a volume.
    public ReportResponseDTO getShapeReport() {
        ReportResponseDTO responseDTO = new ReportResponseDTO();
        ArrayList<ShapeResponseDTO> rowList = new ArrayList<>();
        Shape[] shapeArray = null;

        // the repository keeps the array of the brief; the loop reads it back from there
        shapeRepository.saveShapeArray(createShapeArray());
        shapeArray = shapeRepository.getShapeArray();

        // one row per shape, numbered from 1
        for (int i = 0; i < shapeArray.length; i++) {
            Shape shape = shapeArray[i];
            ShapeResponseDTO row = new ShapeResponseDTO();

            // what every shape has: its number, its description and its area
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

            // keep the row, in the order of the array
            rowList.add(row);
        }

        // the rows travel to the view inside the response
        responseDTO.setRowList(rowList);
        return responseDTO;
    }

    // The brief: "Create an array of Shape references and populate it with one object of
    // each concrete class".
    private Shape[] createShapeArray() {
        ShapeType[] typeArray = ShapeType.values();
        Shape[] shapeArray = new Shape[typeArray.length];

        // one shape for each type, in the enum's order
        for (int i = 0; i < typeArray.length; i++) {
            shapeArray[i] = shapeFactory.createShape(typeArray[i]);
        }

        return shapeArray;
    }
}
