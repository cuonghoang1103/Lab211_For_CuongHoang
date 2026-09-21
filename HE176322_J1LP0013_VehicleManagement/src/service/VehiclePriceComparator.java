package service;

import java.util.Comparator;
import model.Vehicle;

/**
 * STRATEGY: price high -> low (the brief's Function 6.2), id as tie-breaker.
 *
 * @author HE176322
 */
public class VehiclePriceComparator implements Comparator<Vehicle> {

    // Creates the strategy; it keeps no state.
    public VehiclePriceComparator() {
    }

    // Negative when first comes before second: the more expensive first.
    @Override
    public int compare(Vehicle first, Vehicle second) {
        int byPrice = Double.compare(second.getPrice(), first.getPrice());

        // same price: the id decides, so the order never changes between two runs
        if (byPrice == 0) {
            return first.getId().compareToIgnoreCase(second.getId());
        }

        return byPrice;
    }
}
