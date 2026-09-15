package dto;

import java.util.ArrayList;

/**
 * DTO carrying a document FROM the controller OUT TO the view: the raw lines (before) and
 * the normalized text (after).
 *
 * @author HE176322
 */
public class DocumentResponseDTO {

    // Lines of a file as they are on the disk.
    private ArrayList<String> lines;
    // The normalized text; empty when the screen shows only lines.
    private String normalizedText;

    // JavaBean constructor: an empty response.
    public DocumentResponseDTO() {
        this.lines = new ArrayList<>();
        this.normalizedText = "";
    }

    // Creates the response with both parts filled in.
    public DocumentResponseDTO(ArrayList<String> lines, String normalizedText) {
        this.lines = lines;
        this.normalizedText = normalizedText;
    }

    // Returns the raw lines.
    public ArrayList<String> getLines() {
        return lines;
    }

    // Sets the raw lines.
    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    // Returns the normalized text.
    public String getNormalizedText() {
        return normalizedText;
    }

    // Sets the normalized text.
    public void setNormalizedText(String normalizedText) {
        this.normalizedText = normalizedText;
    }
}
