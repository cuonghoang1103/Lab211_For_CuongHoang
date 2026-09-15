package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the count of option 1, or
 * the file names of option 2.
 *
 * @author HE176322
 */
public class WordResponseDTO {

    // Option 1: how many times the word occurs.
    private int count;
    // Option 2: names of the files that contain the word, sorted.
    private ArrayList<String> fileNames;

    // JavaBean constructor: an empty result.
    public WordResponseDTO() {
        this.fileNames = new ArrayList<>();
    }

    // Returns the count.
    public int getCount() {
        return count;
    }

    // Sets the count.
    public void setCount(int count) {
        this.count = count;
    }

    // Returns the file names.
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    // Sets the file names.
    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }
}
