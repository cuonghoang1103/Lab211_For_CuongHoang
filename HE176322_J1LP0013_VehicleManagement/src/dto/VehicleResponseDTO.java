package dto;

import java.util.ArrayList;

/**
 * DTO controller -> view: everything ONE function shows, handed to the view through its
 * attribute (checklist 1.1). Each part is null when the function has nothing of it; the
 * view prints only the parts that are set.
 *
 * @author HE176322
 */
public class VehicleResponseDTO {

    // The result line, e.g. "Add successfully!", or null.
    private String message;

    // One vehicle shown as a header and a row (update, search by id), or null.
    private VehicleRowDTO vehicle;

    // A list shown as a table with its total (search by name, show all), or null.
    private ArrayList<VehicleRowDTO> vehicleList;

    // JavaBean constructor.
    public VehicleResponseDTO() {
    }

    // Returns the result line.
    public String getMessage() {
        return message;
    }

    // Changes the result line.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the single vehicle.
    public VehicleRowDTO getVehicle() {
        return vehicle;
    }

    // Changes the single vehicle.
    public void setVehicle(VehicleRowDTO vehicle) {
        this.vehicle = vehicle;
    }

    // Returns the list of vehicles.
    public ArrayList<VehicleRowDTO> getVehicleList() {
        return vehicleList;
    }

    // Changes the list of vehicles.
    public void setVehicleList(ArrayList<VehicleRowDTO> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
