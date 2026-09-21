package dto;

/**
 * DTO carrying the answer of one flow FROM the controller OUT TO the view - a JavaBean. A
 * step of the normal calculator fills memory, "=" fills result, the BMI calculator fills
 * the BMI number and status; what is not set stays null.
 *
 * @author HE176322
 */
public class CalculatorResponseDTO {

    // The value in memory after one step ("Memory:").
    private Double memory;

    // The final value when "=" is typed ("Result:").
    private Double result;

    // The BMI number.
    private Double bmiNumber;

    // The BMI status words.
    private String bmiStatus;

    // JavaBean constructor: an empty answer, filled through the setters.
    public CalculatorResponseDTO() {
    }

    // Returns the value in memory.
    public Double getMemory() {
        return memory;
    }

    // Sets the value in memory.
    public void setMemory(Double memory) {
        this.memory = memory;
    }

    // Returns the final value.
    public Double getResult() {
        return result;
    }

    // Sets the final value.
    public void setResult(Double result) {
        this.result = result;
    }

    // Returns the BMI number.
    public Double getBmiNumber() {
        return bmiNumber;
    }

    // Sets the BMI number.
    public void setBmiNumber(Double bmiNumber) {
        this.bmiNumber = bmiNumber;
    }

    // Returns the BMI status words.
    public String getBmiStatus() {
        return bmiStatus;
    }

    // Sets the BMI status words.
    public void setBmiStatus(String bmiStatus) {
        this.bmiStatus = bmiStatus;
    }
}
