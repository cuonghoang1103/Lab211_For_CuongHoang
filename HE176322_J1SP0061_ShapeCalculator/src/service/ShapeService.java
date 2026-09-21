package service;

import dto.ShapeRequestDTO;
import dto.ShapeResponseDTO;
import java.util.ArrayList;
import model.Shape;
import repository.ShapeRepository;

/**
 * SERVICE: the calculation of the brief (Guide: services hold "tính tổng, chu vi, diện
 * tích") - it takes the shapes from the repository and turns each one into its result.
 * Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class ShapeService {

    // Keeps the three shapes (Service -> Repository -> Model).
    private ShapeRepository shapeRepository;

    // Creates the service together with its repository.
    public ShapeService() {
        shapeRepository = new ShapeRepository();
    }

    // Stores the three shapes of the request, then computes the result block of each one
    // (area and perimeter included) for the view.
    public ShapeResponseDTO calculateShapes(ShapeRequestDTO requestDTO) {
        ShapeResponseDTO responseDTO = new ShapeResponseDTO();
        ArrayList<String> resultList = new ArrayList<>();

        // the repository builds the models from the request and keeps them
        shapeRepository.saveShapes(requestDTO);

        // one result block per shape, rectangle first as on the brief's screen; toString()
        // runs the version of the real class (polymorphism)
        for (Shape shape : shapeRepository.getShapeList()) {
            resultList.add(shape.toString());
        }

        // the blocks travel to the view inside the response
        responseDTO.setResultList(resultList);
        return responseDTO;
    }
}
