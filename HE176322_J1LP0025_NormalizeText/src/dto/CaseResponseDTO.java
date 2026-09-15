package dto;

/**
 * DTO carrying one text before and after normalizing, FROM the controller OUT TO the view
 * (options 4 and 5).
 *
 * @author HE176322
 */
public class CaseResponseDTO {

    // Name of the case (option 5); empty for a typed line.
    private String title;
    // The text before normalizing.
    private String input;
    // The text after normalizing.
    private String output;

    // JavaBean constructor: an empty case.
    public CaseResponseDTO() {
    }

    // Creates the case with every field filled in.
    public CaseResponseDTO(String title, String input, String output) {
        this.title = title;
        this.input = input;
        this.output = output;
    }

    // Returns the title.
    public String getTitle() {
        return title;
    }

    // Sets the title.
    public void setTitle(String title) {
        this.title = title;
    }

    // Returns the text before.
    public String getInput() {
        return input;
    }

    // Sets the text before.
    public void setInput(String input) {
        this.input = input;
    }

    // Returns the text after.
    public String getOutput() {
        return output;
    }

    // Sets the text after.
    public void setOutput(String output) {
        this.output = output;
    }
}
