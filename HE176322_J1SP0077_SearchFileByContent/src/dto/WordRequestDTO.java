package dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DTO carrying what main prepared FROM main INTO the controller: a path (a file for option
 * 1, a folder for option 2), the word, and what main read from the disk (checklist 1.1:
 * reading files happens in main).
 *
 * @author HE176322
 */
public class WordRequestDTO {

    // The file path (option 1) or folder path (option 2).
    private String path;

    // The word to count or to look for.
    private String word;

    // Option 1: the lines of the file, read by main.
    private ArrayList<String> lineList;

    // Option 2: file name -> lines, for every file of the folder, read by main.
    private HashMap<String, ArrayList<String>> fileLineMap;

    // JavaBean constructor: an empty request, filled through the setters.
    public WordRequestDTO() {
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the word.
    public String getWord() {
        return word;
    }

    // Sets the word.
    public void setWord(String word) {
        this.word = word;
    }

    // Returns the lines of the file (option 1).
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines of the file (option 1).
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }

    // Returns the files of the folder (option 2).
    public HashMap<String, ArrayList<String>> getFileLineMap() {
        return fileLineMap;
    }

    // Sets the files of the folder (option 2).
    public void setFileLineMap(HashMap<String, ArrayList<String>> fileLineMap) {
        this.fileLineMap = fileLineMap;
    }
}
