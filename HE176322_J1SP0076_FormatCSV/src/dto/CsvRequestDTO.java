package dto;

import java.util.ArrayList;

/**
 * DTO carrying what main prepared FROM main INTO the controller: the path typed and, for
 * an import, every line main read from that file (checklist 1.1: reading a file happens in
 * main).
 *
 * @author HE176322
 */
public class CsvRequestDTO {

    // The file path typed by the user.
    private String path;

    // The lines of the imported file, read by main (empty for an export).
    private ArrayList<String> lineList;

    // JavaBean constructor: an empty request, filled through the setters.
    public CsvRequestDTO() {
        lineList = new ArrayList<>();
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the lines main read.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines main read.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
