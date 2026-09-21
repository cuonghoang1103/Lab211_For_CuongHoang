package dto;

import java.util.ArrayList;

/**
 * DTO carrying what main read FROM the keyboard or a file INTO the controller: the typed
 * line (option 4) or the lines of input.txt / output.txt (options 2 and 3).
 *
 * @author HE176322
 */
public class NormalizeRequestDTO {

    // Option 4: the line exactly as typed - NOT trimmed, spaces are the point.
    private String text;

    // Options 2 and 3: the lines of the file main read, exactly as they are on the disk.
    private ArrayList<String> lineList;

    // Creates an empty request; main fills it through the setters.
    public NormalizeRequestDTO() {
        this.text = "";
        this.lineList = new ArrayList<>();
    }

    // Returns the typed line.
    public String getText() {
        return text;
    }

    // Sets the typed line.
    public void setText(String text) {
        this.text = text;
    }

    // Returns the lines of the file.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines of the file.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
