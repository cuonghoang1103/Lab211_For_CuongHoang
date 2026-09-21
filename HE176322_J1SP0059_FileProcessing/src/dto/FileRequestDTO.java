package dto;

import java.util.ArrayList;

/**
 * DTO carrying what main gathered, FROM main INTO the controller, for both functions:
 * path + minimum money (find person info) or source + destination (copy text), and the
 * lines main read from that file.
 *
 * @author HE176322
 */
public class FileRequestDTO {

    // Path of the person file (function 1).
    private String path;

    // Minimum salary to show (function 1); already checked >= 0.
    private double money;

    // Path of the file to copy the words from (function 2).
    private String source;

    // Path of the new file to write the words into (function 2).
    private String destination;

    // The lines main read from the file: the person file (function 1) or the source
    // (function 2). Reading is main's job (checklist 1.1).
    private ArrayList<String> lineList;

    // Creates an empty request; main fills it through the setters.
    public FileRequestDTO() {
    }

    // Returns the path of the person file.
    public String getPath() {
        return path;
    }

    // Sets the path of the person file.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the minimum salary.
    public double getMoney() {
        return money;
    }

    // Sets the minimum salary.
    public void setMoney(double money) {
        this.money = money;
    }

    // Returns the source file.
    public String getSource() {
        return source;
    }

    // Sets the source file.
    public void setSource(String source) {
        this.source = source;
    }

    // Returns the destination file.
    public String getDestination() {
        return destination;
    }

    // Sets the destination file.
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Returns the lines main read from the file.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines main read from the file.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
