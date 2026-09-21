package service;

import java.util.Comparator;
import model.Vehicle;

/**
 * STRATEGY: name Z -> A (the brief's "search by name (descending)"), id as tie-breaker.
 *
 * @author HE176322
 */
public class VehicleNameComparator implements Comparator<Vehicle> {

    // Creates the strategy; it keeps no state.
    public VehicleNameComparator() {
    }

    // Negative when first comes before second: the bigger name first.
    @Override
    public int compare(Vehicle first, Vehicle second) {
        int byName = second.getName().compareToIgnoreCase(first.getName());

        // same name: the id decides, so the order never changes between two runs
        if (byName == 0) {
            return first.getId().compareToIgnoreCase(second.getId());
        }

        return byName;
    }
}
