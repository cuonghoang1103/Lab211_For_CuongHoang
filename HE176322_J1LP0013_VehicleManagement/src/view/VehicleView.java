package view;

import constants.Constants;
import constants.Message;
import dto.VehicleResponseDTO;
import dto.VehicleRowDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * VIEW: the only place (with main) allowed to print results. It receives the data through
 * its attribute (the ResponseDTO, as in the Guide sample), never through the parameters of
 * display().
 *
 * @author HE176322
 */
public class VehicleView {

    // Price like 35,000.00 on every machine's locale.
    private DecimalFormat priceFormat;

    // The answer to print, handed over by the controller.
    private VehicleResponseDTO responseDTO;

    // Creates the view; Locale.US keeps "35,000.00" even on a Vietnamese machine.
    public VehicleView() {
        priceFormat = new DecimalFormat(Constants.PRICE_PATTERN,
                DecimalFormatSymbols.getInstance(Locale.US));
    }

    // Receives the answer the next display() call will print.
    public void setResponseDTO(VehicleResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    // Prints what the controller set, in this order: the result line, one vehicle, a list.
    public void display() {
        // a one-line result such as "Add successfully!"
        if (responseDTO.getMessage() != null) {
            System.out.println(responseDTO.getMessage());
        }

        // one vehicle (update, search by id): the header and its row
        if (responseDTO.getVehicle() != null) {
            displayHeader();
            System.out.println(formatRow(responseDTO.getVehicle()));
        }

        // a list (search by name, show all): the table and its total
        if (responseDTO.getVehicleList() != null) {
            displayList();
        }
    }

    // Prints header, rows (and sounds), closing line and total.
    private void displayList() {
        displayHeader();

        // one row per vehicle, in the order the service chose
        for (VehicleRowDTO row : responseDTO.getVehicleList()) {
            System.out.println(formatRow(row));

            // Function 6.2: a motorbike says "Tin tin tin" under its row
            if (row.getSound() != null) {
                System.out.println(row.getSound());
            }
        }

        // the closing line, then the count
        System.out.println(Constants.TABLE_LINE);
        System.out.println(String.format(Message.TOTAL, responseDTO.getVehicleList().size()));
    }

    // Prints the column names and the line under them (same format as the rows).
    private void displayHeader() {
        System.out.println(String.format(Constants.ROW_FORMAT, Message.COL_ID,
                Message.COL_NAME, Message.COL_COLOR, Message.COL_PRICE, Message.COL_BRAND,
                Message.COL_KIND, Message.COL_DETAILS));
        System.out.println(Constants.TABLE_LINE);
    }

    // One vehicle as one row of the table.
    private String formatRow(VehicleRowDTO row) {
        return String.format(Constants.ROW_FORMAT, row.getId(), row.getName(), row.getColor(),
                priceFormat.format(row.getPrice()), row.getBrand(),
                row.getVehicleType().getLabel(), row.getDetails());
    }
}
