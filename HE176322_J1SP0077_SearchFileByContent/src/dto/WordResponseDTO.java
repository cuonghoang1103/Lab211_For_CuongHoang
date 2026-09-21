package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the line "Bout: n" of
 * option 1, or the file names of option 2.
 *
 * @author HE176322
 */
public class WordResponseDTO {

    // Option 1: the result line "Bout: n"; null for option 2.
    private String message;

    // Option 2: names of the files that contain the word, sorted; null for option 1.
    private ArrayList<String> fileNameList;

    // JavaBean constructor: an empty result, filled through the setters.
    public WordResponseDTO() {
    }

    // Returns the result line.
    public String getMessage() {
        return message;
    }

    // Sets the result line.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the file names.
    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    // Sets the file names.
    public void setFileNameList(ArrayList<String> fileNameList) {
        this.fileNameList = fileNameList;
    }
}
