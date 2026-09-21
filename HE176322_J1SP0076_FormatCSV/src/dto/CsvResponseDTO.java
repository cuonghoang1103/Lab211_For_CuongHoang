package dto;

/**
 * DTO carrying the result of an option FROM the controller OUT TO the view: the one line
 * to print, such as "Import: Done".
 *
 * @author HE176322
 */
public class CsvResponseDTO {

    // The line to print: "Import: Done", "Format: Done" or "Export: Done".
    private String message;

    // JavaBean constructor: an empty result, filled through the setter.
    public CsvResponseDTO() {
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
