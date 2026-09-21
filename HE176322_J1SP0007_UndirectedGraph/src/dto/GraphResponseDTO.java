package dto;

/**
 * DTO carrying the answer FROM the controller OUT TO the view: the one line to print,
 * "This is  an edge" or "This is not an edge".
 *
 * @author HE176322
 */
public class GraphResponseDTO {

    // The line to print: the answer to the edge question.
    private String message;

    // JavaBean constructor: an empty answer; the service fills it through the setter.
    public GraphResponseDTO() {
    }

    // Returns the line to print.
    public String getMessage() {
        return message;
    }

    // Sets the line to print.
    public void setMessage(String message) {
        this.message = message;
    }
}
