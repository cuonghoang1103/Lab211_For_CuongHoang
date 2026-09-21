package repository;

import dto.ConvertRequestDTO;
import model.BaseNumber;

/**
 * REPOSITORY: holds the data of the program - the number the user typed, in the base it
 * was typed in - and only simple CRUD on it. No conversion, no print.
 *
 * @author HE176322
 */
public class BaseNumberRepository {

    // The number to convert (the model).
    private BaseNumber baseNumber;

    // Creates the store with the number 0 in decimal.
    public BaseNumberRepository() {
        baseNumber = new BaseNumber();
    }

    // Create: turns the request into the model (value + input base) and keeps it in place
    // of the number of an earlier round.
    public void saveBaseNumber(ConvertRequestDTO requestDTO) {
        baseNumber = new BaseNumber(requestDTO.getValue(), requestDTO.getInputBase());
    }

    // Read: returns the number kept by the last save.
    public BaseNumber getBaseNumber() {
        return baseNumber;
    }
}
