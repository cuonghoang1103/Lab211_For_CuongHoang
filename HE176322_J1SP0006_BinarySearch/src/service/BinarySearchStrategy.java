package service;

import constants.Constants;
import model.NumberArray;

/**
 * CONCRETE STRATEGY: binary search, exactly the steps of the brief's Guidelines, written
 * iteratively (the brief allows either way).
 *
 * @author HE176322
 */
public class BinarySearchStrategy implements SearchStrategy {

    // Searches a sorted array by halving the part that can still hold the value.
    @Override
    public int search(NumberArray array, int value) {
        int low = 0;
        int high = array.getSize() - 1;
        // the part [low, high] still has elements: look at its middle
        while (low <= high) {
            // low + (high - low) / 2 never overflows, unlike (low + high) / 2
            int middle = low + (high - low) / 2;
            int middleValue = array.getValue(middle);
            // the middle element equals the searched value: stop
            if (middleValue == value) {
                return middle;
            } else if (value < middleValue) {
                // searched value is smaller: keep the part BEFORE the middle
                high = middle - 1;
            } else {
                // searched value is bigger: keep the part AFTER the middle
                low = middle + 1;
            }
        }
        return Constants.NOT_FOUND;
    }
}
