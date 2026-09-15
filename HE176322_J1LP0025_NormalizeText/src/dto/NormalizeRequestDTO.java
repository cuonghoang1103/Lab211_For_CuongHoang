package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the line to normalize
 * (option 4).
 *
 * @author HE176322
 */
public class NormalizeRequestDTO {

    // The line exactly as typed - NOT trimmed, spaces are the point.
    private String text;

    // Creates an empty request; main fills it through the setter.
    public NormalizeRequestDTO() {
    }

    // Returns the typed line.
    public String getText() {
        return text;
    }

    // Sets the typed line.
    public void setText(String text) {
        this.text = text;
    }
}
