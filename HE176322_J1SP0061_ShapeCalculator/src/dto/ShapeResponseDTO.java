package dto;

/**
 * DTO carrying one shape's result FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class ShapeResponseDTO {

    // The shape's whole result block: title, properties, area, perimeter.
    private String result;

    // JavaBean constructor: an empty response, filled through the setter.
    public ShapeResponseDTO() {
    }

    // Returns the result block.
    public String getResult() {
        return result;
    }

    // Sets the result block.
    public void setResult(String result) {
        this.result = result;
    }
}
