package repository;

import model.NumberArray;

/**
 * REPOSITORY: holds the data of the program - the array of numbers the service searches -
 * and only simple CRUD on it. No searching, no print.
 *
 * @author HE176322
 */
public class NumberRepository {

    // The array the program works on (the model).
    private NumberArray numberArray;

    // Creates the store with an empty array.
    public NumberRepository() {
        numberArray = new NumberArray();
    }

    // Create: wraps the generated numbers in the model and keeps it.
    public void saveNumberArray(int[] valueArray) {
        numberArray = new NumberArray(valueArray);
    }

    // Read: returns the array kept by the last save.
    public NumberArray getNumberArray() {
        return numberArray;
    }
}
