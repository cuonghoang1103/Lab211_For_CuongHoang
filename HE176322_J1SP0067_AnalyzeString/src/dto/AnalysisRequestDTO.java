package dto;

/**
 * DTO carrying the typed string FROM main INTO the controller.
 *
 * @author HE176322
 */
public class AnalysisRequestDTO {

    // The string to analyse (already checked by Validation).
    private String input;

    // Creates an empty request; main fills it through the setter.
    public AnalysisRequestDTO() {
    }

    // Returns the string.
    public String getInput() {
        return input;
    }

    // Sets the string.
    public void setInput(String input) {
        this.input = input;
    }
}
