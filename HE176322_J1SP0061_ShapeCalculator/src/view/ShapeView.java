package view;

import dto.ShapeResponseDTO;
import java.util.ArrayList;

/**
 * VIEW: prints the result of every shape.
 *
 * @author HE176322
 */
public class ShapeView {

    // The results to display, handed over by the controller.
    private ArrayList<ShapeResponseDTO> shapes;

    // Receives the results the next printResult() call will print.
    public void setShapes(ArrayList<ShapeResponseDTO> shapes) {
        this.shapes = shapes;
    }

    // The brief's Function 3, "public void printResult()": displays the shape
    // information.
    public void printResult() {
        // one block per shape: title, properties, area, perimeter
        for (ShapeResponseDTO shape : shapes) {
            System.out.println(shape.getResult());
        }
    }
}
