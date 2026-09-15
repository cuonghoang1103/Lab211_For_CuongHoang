package repository;

import java.util.ArrayList;
import model.Vehicle;

/**
 * REPOSITORY: the show room - ONE collection of vehicles (the brief's Function 0) and
 * whether it changed since it was last loaded or stored.
 *
 * @author HE176322
 */
public class VehicleRepository {

    // The "database": cars and motorbikes together, in the order they were added.
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    // True when something was added, updated or deleted and not stored yet.
    private boolean changed;

    // Creates an empty show room.
    public VehicleRepository() {
    }

    // Counts the vehicles.
    public int countVehicles() {
        return vehicles.size();
    }

    // Finds a vehicle by id, ignoring case (c001 = C001).
    public Vehicle findById(String id) {
        // look at every vehicle once
        for (Vehicle vehicle : vehicles) {
            // same id, whatever the case
            if (vehicle.getId().equalsIgnoreCase(id)) {
                return vehicle;
            }
        }
        return null;
    }

    // Appends a new vehicle.
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        changed = true;
    }

    // Puts the updated vehicle back in its place.
    public boolean updateVehicle(Vehicle vehicle) {
        // walk by index so the vehicle keeps its position
        for (int i = 0; i < vehicles.size(); i++) {
            // found the stored one with the same id
            if (vehicles.get(i).getId().equalsIgnoreCase(vehicle.getId())) {
                vehicles.set(i, vehicle);
                changed = true;
                return true;
            }
        }
        return false;
    }

    // Removes the vehicle with this id.
    public boolean deleteVehicle(String id) {
        Vehicle vehicle = findById(id);
        // nothing has this id
        if (vehicle == null) {
            return false;
        }
        changed = true;
        return vehicles.remove(vehicle);
    }

    // Returns a copy of the list, so sorting a result never reorders the show room.
    public ArrayList<Vehicle> findAll() {
        return new ArrayList<>(vehicles);
    }

    // Function 1: the file replaces the collection (it is not appended to it).
    public void replaceAll(ArrayList<Vehicle> loaded) {
        vehicles.clear();
        vehicles.addAll(loaded);
        changed = false;
    }

    // Function 7: the collection now equals the file.
    public void markStored() {
        changed = false;
    }

    // Tells whether there are changes not stored yet.
    public boolean isChanged() {
        return changed;
    }
}
