package repository;

import model.CalculatorMemory;

/**
 * REPOSITORY: holds the data of the program - the brief's temporary memory of the normal
 * calculator - and only simple CRUD on it. No computing, no print.
 *
 * @author HE176322
 */
public class CalculatorRepository {

    // The temporary memory every next operator is applied to (the model).
    private CalculatorMemory memory;

    // Creates the store with an empty memory (it holds 0).
    public CalculatorRepository() {
        memory = new CalculatorMemory();
    }

    // Update: stores a new value in the memory.
    public void saveMemory(double value) {
        memory.setValue(value);
    }

    // Read: returns the memory.
    public CalculatorMemory getMemory() {
        return memory;
    }
}
