package repository;

import model.NumberArray;

/**
 * REPOSITORY: keeps the array between menu choices (the brief: "Save element(s) of
 * array") - the data of the program - and only simple CRUD on it. No sorting, no print.
 *
 * @author HE176322
 */
public class NumberRepository {

    // The array typed in option 1 (the model); empty until option 1 has been used.
    private NumberArray numberArray;

    // Creates the store with an empty array.
    public NumberRepository() {
        numberArray = new NumberArray();
    }

    // Create: wraps the typed elements in the model and keeps it, replacing any earlier
    // array.
    public void saveNumberArray(int[] valueArray) {
        numberArray = new NumberArray(valueArray);
    }

    // Read: returns the array kept by the last save.
    public NumberArray getNumberArray() {
        return numberArray;
    }
}
