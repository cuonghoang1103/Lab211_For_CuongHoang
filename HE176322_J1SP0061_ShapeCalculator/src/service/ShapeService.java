package service;

import dto.ShapeRequestDTO;
import dto.ShapeResponseDTO;
import java.util.ArrayList;
import model.Circle;
import model.Rectangle;
import model.Shape;
import model.Triangle;

/**
 * SERVICE: builds the three shapes from the request and computes their results.
 *
 * @author HE176322
 */
public class ShapeService {

    // Creates the service; it keeps no state.
    public ShapeService() {
    }

    // Builds the rectangle, the circle and the triangle, and turns each one into the text
    // block the view prints.
    public ArrayList<ShapeResponseDTO> calculateShapes(ShapeRequestDTO requestDTO) {
        Shape rectangle = new Rectangle(requestDTO.getWidth(), requestDTO.getLength());
        Shape circle = new Circle(requestDTO.getRadius());
        Shape triangle = new Triangle(requestDTO.getSideA(), requestDTO.getSideB(),
                requestDTO.getSideC());
        Shape[] shapes = {rectangle, circle, triangle};
        ArrayList<ShapeResponseDTO> results = new ArrayList<>();
        // one result block per shape, rectangle first as on the brief's screen
        for (Shape shape : shapes) {
            ShapeResponseDTO response = new ShapeResponseDTO();
            response.setResult(shape.toString());
            results.add(response);
        }
        return results;
    }
}
