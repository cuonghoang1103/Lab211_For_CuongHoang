package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the two numbers, as
 * TEXT.
 *
 * @author HE176322
 */
public class MultiplyRequestDTO {

    // The first number, digits only (already validated).
    private String firstNumber;

    // The second number, digits only (already validated).
    private String secondNumber;

    // JavaBean constructor: an empty request; main fills it through setters.
    public MultiplyRequestDTO() {
    }

    // Returns the first number.
    public String getFirstNumber() {
        return firstNumber;
    }

    // Sets the first number.
    public void setFirstNumber(String firstNumber) {
        this.firstNumber = firstNumber;
    }

    // Returns the second number.
    public String getSecondNumber() {
        return secondNumber;
    }

    // Sets the second number.
    public void setSecondNumber(String secondNumber) {
        this.secondNumber = secondNumber;
    }
}
