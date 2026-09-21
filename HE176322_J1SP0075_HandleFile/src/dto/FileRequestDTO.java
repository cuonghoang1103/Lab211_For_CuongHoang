package dto;

import java.util.ArrayList;

/**
 * DTO carrying what main prepared FROM main INTO the controller: a path, and for option 3
 * a size, for option 4 the content to add, for option 5 the lines main read from the file
 * (checklist 1.1: reading a file happens in main).
 *
 * @author HE176322
 */
public class FileRequestDTO {

    // The file or directory path typed by the user.
    private String path;

    // Option 3: the size n in KB.
    private int size;

    // Option 4: the text to add at the end of the file.
    private String content;

    // Option 5: the lines of the file, read by main.
    private ArrayList<String> lineList;

    // JavaBean constructor: an empty request, filled through the setters.
    public FileRequestDTO() {
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the size.
    public int getSize() {
        return size;
    }

    // Sets the size.
    public void setSize(int size) {
        this.size = size;
    }

    // Returns the content.
    public String getContent() {
        return content;
    }

    // Sets the content.
    public void setContent(String content) {
        this.content = content;
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
