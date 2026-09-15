package view;

import constants.Constants;
import constants.Message;
import dto.ShapeResponseDTO;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: prints the report of the brief's sample output.
 *
 * @author HE176322
 */
public class ShapeView {

    // The rows to display, handed over by the controller.
    private ArrayList<ShapeResponseDTO> shapes;

    // Receives the rows the next display() call will print.
    public void setShapes(ArrayList<ShapeResponseDTO> shapes) {
        this.shapes = shapes;
    }

    // Prints the ruler, the header, one line per shape and the closing ruler.
    public void display() {
        System.out.println(Message.LINE);
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_NO,
                Message.LABEL_SHAPE, Message.LABEL_AREA, Message.LABEL_VOLUME));
        System.out.println(Message.LINE);
        // one line per shape, in the order of the array
        for (ShapeResponseDTO shape : shapes) {
            String area = String.format(Locale.US, Constants.NUMBER_FORMAT, shape.getArea());
            // a solid shows its volume, a flat shape shows "-"
            if (shape.isThreeDimensional()) {
                String volume = String.format(Locale.US, Constants.NUMBER_FORMAT,
                        shape.getVolume());
                System.out.println(String.format(Constants.ROW_3D_FORMAT, shape.getNo(),
                        shape.getDescription(), area, volume));
            } else {
                // no volume for a two-dimensional shape
                System.out.println(String.format(Constants.ROW_2D_FORMAT, shape.getNo(),
                        shape.getDescription(), area, Message.NO_VOLUME));
            }
        }
        System.out.println(Message.LINE);
    }
}
