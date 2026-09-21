package view;

import constants.Constants;
import constants.Message;
import dto.ReportResponseDTO;
import dto.ShapeResponseDTO;
import java.util.Locale;

/**
 * VIEW: prints the report of the brief's sample output. It receives the data through its
 * attribute (the ResponseDTO), never through the parameters of display().
 *
 * @author HE176322
 */
public class ShapeView {

    // The report to print, handed over by the controller.
    private ReportResponseDTO responseDTO;

    // Receives the report the next display() call will print.
    public void setResponseDTO(ReportResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints the ruler, the header, one line per shape and the closing ruler.
    public void display() {
        // the frame on top: ruler, header, ruler
        System.out.println(Message.LINE);
        System.out.println(String.format(Constants.HEADER_FORMAT, Message.LABEL_NO,
                Message.LABEL_SHAPE, Message.LABEL_AREA, Message.LABEL_VOLUME));
        System.out.println(Message.LINE);

        // one line per shape, in the order of the array
        for (ShapeResponseDTO row : responseDTO.getRowList()) {
            String area = String.format(Locale.US, Constants.NUMBER_FORMAT, row.getArea());

            // a solid shows its volume, a flat shape shows "-"
            if (row.isThreeDimensional()) {
                String volume = String.format(Locale.US, Constants.NUMBER_FORMAT,
                        row.getVolume());

                System.out.println(String.format(Constants.ROW_3D_FORMAT, row.getNo(),
                        row.getDescription(), area, volume));
            } else {
                // no volume for a two-dimensional shape
                System.out.println(String.format(Constants.ROW_2D_FORMAT, row.getNo(),
                        row.getDescription(), area, Message.NO_VOLUME));
            }
        }

        // the closing ruler
        System.out.println(Message.LINE);
    }
}
