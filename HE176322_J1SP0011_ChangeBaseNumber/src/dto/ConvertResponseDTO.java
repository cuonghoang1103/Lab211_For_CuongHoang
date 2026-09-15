package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the input and the output,
 * each already written as "value (BASE)".
 *
 * @author HE176322
 */
public class ConvertResponseDTO {

    // The input.
    private String input;
    // The output.
    private String output;

    // JavaBean constructor: an empty response, filled through the setters.
    public ConvertResponseDTO() {
    }

    // Returns the input text.
    public String getInput() {
        return input;
    }

    // Sets the input text.
    public void setInput(String input) {
        this.input = input;
    }

    // Returns the output text.
    public String getOutput() {
        return output;
    }

    // Sets the output text.
    public void setOutput(String output) {
        this.output = output;
    }
}
