package service;

import constants.Constants;
import model.NumberArray;

/**
 * CONCRETE STRATEGY: binary search, exactly the steps of the brief's Guidelines, written
 * iteratively (the brief allows either way).
 *
 * @author HE176322
 */
public class BinarySearchStrategy implements ISearchStrategy {

    // Searches a sorted array by halving the part that can still hold the value; returns
    // the index where the value was found, or Constants.NOT_FOUND.
    @Override
    public int search(NumberArray numberArray, int searchValue) {
        int low = 0;
        int high = numberArray.getSize() - 1;
        int middle = 0;
        int middleValue = 0;

        // the part [low, high] still has elements: look at its middle
        while (low <= high) {
            // low + ((high - low) / 2) never overflows, unlike (low + high) / 2
            middle = low + ((high - low) / 2);
            middleValue = numberArray.getValue(middle);

            // the middle element equals the searched value: stop
            if (middleValue == searchValue) {
                return middle;
            } else if (searchValue < middleValue) {
                // searched value is smaller: keep the part BEFORE the middle
                high = middle - 1;
            } else {
                // searched value is bigger: keep the part AFTER the middle
                low = middle + 1;
            }
        }

        // the part has no elements left: the value is absent
        return Constants.NOT_FOUND;
    }
}
