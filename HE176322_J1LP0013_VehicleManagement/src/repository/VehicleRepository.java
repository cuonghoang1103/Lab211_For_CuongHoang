package repository;

import constants.Constants;
import java.util.ArrayList;
import model.Vehicle;
import utils.FileUtils;

/**
 * REPOSITORY: the show room - ONE collection of vehicles (the brief's Function 0) with
 * simple CRUD on it, and the writing of that collection back to vehicles.txt. No rule,
 * no print, no reading of the file (main reads it).
 *
 * @author HE176322
 */
public class VehicleRepository {

    // The "database": cars and motorbikes together, in the order they were added.
    private ArrayList<Vehicle> vehicleList;

    // Creates an empty show room.
    public VehicleRepository() {
        vehicleList = new ArrayList<>();
    }

    // Counts the vehicles.
    public int countVehicles() {
        return vehicleList.size();
    }

    // Finds a vehicle by id, ignoring case (c001 = C001); null when no vehicle has it.
    public Vehicle findById(String id) {
        // look at every vehicle once
        for (Vehicle vehicle : vehicleList) {
            // same id, whatever the case
            if (vehicle.getId().equalsIgnoreCase(id)) {
                return vehicle;
            }
        }

        return null;
    }

    // Appends a new vehicle.
    public void addVehicle(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    // Puts the updated vehicle back in its place.
    public boolean updateVehicle(Vehicle vehicle) {
        // walk by index so the vehicle keeps its position
        for (int i = 0; i < vehicleList.size(); i++) {
            // found the stored one with the same id
            if (vehicleList.get(i).getId().equalsIgnoreCase(vehicle.getId())) {
                vehicleList.set(i, vehicle);
                return true;
            }
        }

        return false;
    }

    // Removes the vehicle with this id; false when no vehicle has it.
    public boolean deleteVehicle(String id) {
        Vehicle vehicle = findById(id);

        // nothing has this id
        if (vehicle == null) {
            return false;
        }

        return vehicleList.remove(vehicle);
    }

    // Returns a copy of the list, so sorting a result never reorders the show room.
    public ArrayList<Vehicle> findAll() {
        return new ArrayList<>(vehicleList);
    }

    // Function 1: the vehicles of the file replace the collection (not appended to it).
    public void replaceAll(ArrayList<Vehicle> loadedList) {
        vehicleList.clear();
        vehicleList.addAll(loadedList);
    }

    // Function 7: writes every vehicle as one line of vehicles.txt (the file is replaced,
    // not appended) and returns how many vehicles were written.
    public int storeToFile() throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // toDataLine() is the Template Method of Vehicle
        for (Vehicle vehicle : vehicleList) {
            lineList.add(vehicle.toDataLine());
        }

        // FileUtils empties the file first, then writes one line per vehicle
        FileUtils.writeLines(Constants.DATA_FILE, lineList);
        return lineList.size();
    }
}
