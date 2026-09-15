package view;

import constants.Constants;
import constants.Message;
import dto.VehicleResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class VehicleView {

    // Price like 35,000.00 on every machine's locale.
    private DecimalFormat priceFormat = new DecimalFormat(Constants.PRICE_PATTERN,
            DecimalFormatSymbols.getInstance(Locale.US));

    // Prints the header and one vehicle.
    public void displayVehicle(VehicleResponseDTO vehicle) {
        displayHeader();
        System.out.println(formatRow(vehicle));
    }

    // Prints a list of vehicles with its total (Functions 5.1 and 6.1).
    public void displayList(ArrayList<VehicleResponseDTO> vehicles) {
        displayRows(vehicles, false);
    }

    // Prints a list where each vehicle that can be heard makes its sound (Function 6.2).
    public void displaySoundList(ArrayList<VehicleResponseDTO> vehicles) {
        displayRows(vehicles, true);
    }

    // Prints a one-line result such as "Add successfully!".
    public void showMessage(String message) {
        System.out.println(message);
    }

    // Prints header, rows (and sounds), closing line and total.
    private void displayRows(ArrayList<VehicleResponseDTO> vehicles, boolean withSound) {
        displayHeader();
        // one row per vehicle
        for (VehicleResponseDTO vehicle : vehicles) {
            System.out.println(formatRow(vehicle));
            // Function 6.2: a motorbike says "Tin tin tin" under its row
            if (withSound && vehicle.getSound() != null) {
                System.out.println(vehicle.getSound());
            }
        }
        System.out.println(Constants.TABLE_LINE);
        System.out.println(String.format(Message.TOTAL, vehicles.size()));
    }

    // Prints the column names and the line under them (same format as the rows).
    private void displayHeader() {
        System.out.println(String.format(Constants.ROW_FORMAT, Message.COL_ID,
                Message.COL_NAME, Message.COL_COLOR, Message.COL_PRICE, Message.COL_BRAND,
                Message.COL_KIND, Message.COL_DETAILS));
        System.out.println(Constants.TABLE_LINE);
    }

    // One vehicle as one row of the table.
    private String formatRow(VehicleResponseDTO vehicle) {
        return String.format(Constants.ROW_FORMAT, vehicle.getId(), vehicle.getName(),
                vehicle.getColor(), priceFormat.format(vehicle.getPrice()), vehicle.getBrand(),
                vehicle.getVehicleType().getLabel(), vehicle.getDetails());
    }
}
