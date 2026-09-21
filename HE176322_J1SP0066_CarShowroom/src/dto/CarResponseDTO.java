package dto;

/**
 * DTO carrying the answer of one check FROM the controller OUT TO the view - a JavaBean.
 * A refused request never gets here: it is a CarException caught by main.
 *
 * @author HE176322
 */
public class CarResponseDTO {

    // The one-line result: "Sell Car".
    private String message;

    // JavaBean constructor: an empty answer, filled through the setter.
    public CarResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }
}
