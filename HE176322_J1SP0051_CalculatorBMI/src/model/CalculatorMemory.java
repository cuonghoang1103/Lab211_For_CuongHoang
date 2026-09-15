package model;

/**
 * MODEL: the calculator's "temporary memory" of the brief - the value every next operator
 * is applied to.
 *
 * @author HE176322
 */
public class CalculatorMemory {

    // The value currently in memory.
    private double value;

    // JavaBean constructor: an empty memory holds 0.
    public CalculatorMemory() {
    }

    // Creates a memory that already holds a value.
    public CalculatorMemory(double value) {
        this.value = value;
    }

    // Returns the value in memory.
    public double getValue() {
        return value;
    }

    // Stores a new value in memory.
    public void setValue(double value) {
        this.value = value;
    }
}
