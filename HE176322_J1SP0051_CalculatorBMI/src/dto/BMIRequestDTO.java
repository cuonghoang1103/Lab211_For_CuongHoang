package dto;

/**
 * DTO carrying the weight and height typed FROM main INTO the controller.
 *
 * @author HE176322
 */
public class BMIRequestDTO {

    // Body weight in kg (already checked: a positive number).
    private double weight;
    // Height in cm (already checked: a positive number).
    private double height;

    // JavaBean constructor: an empty request; main fills it through setters.
    public BMIRequestDTO() {
    }

    // Returns the weight.
    public double getWeight() {
        return weight;
    }

    // Sets the weight.
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // Returns the height.
    public double getHeight() {
        return height;
    }

    // Sets the height.
    public void setHeight(double height) {
        this.height = height;
    }
}
