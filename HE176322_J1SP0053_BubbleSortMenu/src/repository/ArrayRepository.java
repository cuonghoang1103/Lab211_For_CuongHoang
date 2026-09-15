package repository;

import constants.Message;
import dto.ArrayRequestDTO;
import java.util.Arrays;
import model.NumberArray;

/**
 * REPOSITORY: keeps the array between menu choices (the brief: "Save element(s) of
 * array") and hands out copies of it.
 *
 * @author HE176322
 */
public class ArrayRepository {

    // The saved array; null until option 1 has been used.
    private NumberArray numberArray;

    // Creates an empty repository.
    public ArrayRepository() {
    }

    // Saves the typed elements, replacing any earlier array.
    public void saveArray(ArrayRequestDTO requestDTO) {
        int[] elements = requestDTO.getElements();
        numberArray = new NumberArray(Arrays.copyOf(elements, elements.length));
    }

    // Returns a COPY of the saved elements, so sorting for display never changes the
    // array the user typed.
    public int[] getValues() throws Exception {
        // option 2 or 3 before option 1: there is nothing to sort
        if (numberArray == null) {
            throw new Exception(Message.ARRAY_EMPTY);
        }
        return Arrays.copyOf(numberArray.getValues(), numberArray.getSize());
    }
}
