package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result of an option FROM the controller OUT TO the view: the file names
 * (options 2 and 3) and the one line to print after them.
 *
 * @author HE176322
 */
public class FileResponseDTO {

    // The file names to print, already sorted; null = the option lists no file.
    private ArrayList<String> fileNameList;

    // The one-line result: "Path to file", "Result 2 file!", "Write done", "Total:3"...
    private String message;

    // JavaBean constructor: an empty result, filled through the setters.
    public FileResponseDTO() {
    }

    // Returns the names.
    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    // Sets the names.
    public void setFileNameList(ArrayList<String> fileNameList) {
        this.fileNameList = fileNameList;
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
