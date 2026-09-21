package dto;

import java.util.ArrayList;

/**
 * DTO carrying what main read, FROM main INTO the controller: the lines of the data file
 * (at start), or what the user typed (one menu option).
 *
 * @author HE176322
 */
public class WordRequestDTO {

    // Lines of the data file, read by main when the program starts (loadData only).
    private ArrayList<String> lineList;

    // English word typed by the user (add, delete and translate).
    private String english;

    // Vietnamese meaning typed by the user (add only).
    private String vietnamese;

    // The user's answer to "update its meaning (Y/N)?": true = Y.
    private boolean overwrite;

    // Creates an empty request; main fills it through the setters.
    public WordRequestDTO() {
    }

    // Returns the lines of the data file.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines of the data file.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }

    // Returns the English word.
    public String getEnglish() {
        return english;
    }

    // Sets the English word.
    public void setEnglish(String english) {
        this.english = english;
    }

    // Returns the Vietnamese meaning.
    public String getVietnamese() {
        return vietnamese;
    }

    // Sets the Vietnamese meaning.
    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    // Tells whether an existing meaning may be replaced.
    public boolean isOverwrite() {
        return overwrite;
    }

    // Sets the answer to the Y/N question.
    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }
}
