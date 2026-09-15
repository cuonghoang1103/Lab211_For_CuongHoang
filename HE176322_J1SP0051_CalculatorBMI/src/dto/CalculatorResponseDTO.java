package dto;

/**
 * DTO carrying the value in memory FROM the controller OUT TO the view, for the "Memory:"
 * and "Result:" lines.
 *
 * @author HE176322
 */
public class CalculatorResponseDTO {

    // The value in memory after the last step.
    private double memory;

    // JavaBean constructor: an empty response, filled through the setter.
    public CalculatorResponseDTO() {
    }

    // Returns the value in memory.
    public double getMemory() {
        return memory;
    }

    // Sets the value in memory.
    public void setMemory(double memory) {
        this.memory = memory;
    }
}
