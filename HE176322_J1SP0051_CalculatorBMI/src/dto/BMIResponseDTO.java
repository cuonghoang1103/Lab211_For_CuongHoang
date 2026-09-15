package dto;

/**
 * DTO carrying the BMI result FROM the controller OUT TO the view: the number and the
 * status words.
 *
 * @author HE176322
 */
public class BMIResponseDTO {

    // The BMI number.
    private double bmiNumber;
    // The status words.
    private String status;

    // JavaBean constructor: an empty response, filled through the setters.
    public BMIResponseDTO() {
    }

    // Returns the BMI number.
    public double getBmiNumber() {
        return bmiNumber;
    }

    // Sets the BMI number.
    public void setBmiNumber(double bmiNumber) {
        this.bmiNumber = bmiNumber;
    }

    // Returns the status words.
    public String getStatus() {
        return status;
    }

    // Sets the status words.
    public void setStatus(String status) {
        this.status = status;
    }
}
