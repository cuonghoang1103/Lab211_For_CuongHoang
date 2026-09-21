package model;

import constants.Constants;
import java.util.ArrayList;

/**
 * MODEL: one text file that was read - its path and its lines - a JavaBean (private
 * fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class TextFile {

    // Where the file was read from.
    private String path;

    // The lines of the file, in file order.
    private ArrayList<String> lineList;

    // JavaBean constructor: an empty text.
    public TextFile() {
        this.lineList = new ArrayList<>();
    }

    // Creates a text file with its lines.
    public TextFile(String path, ArrayList<String> lineList) {
        this.path = path;
        this.lineList = lineList;
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the lines.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }

    // Counts the words: pieces of text separated by whitespace (the brief).
    public int countWords() {
        int total = 0;
        String text = "";

        // count the words of every line
        for (String line : lineList) {
            text = line.trim();

            // an empty line holds no word
            if (!text.isEmpty()) {
                total += text.split(Constants.WORD_SEPARATOR).length;
            }
        }

        return total;
    }
}
