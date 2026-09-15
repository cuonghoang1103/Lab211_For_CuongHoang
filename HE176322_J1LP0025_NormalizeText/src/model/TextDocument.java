package model;

import constants.Constants;
import java.util.ArrayList;

/**
 * MODEL: one document on its way through the program - the lines exactly as they were
 * read, and the normalized text once the rules have run.
 *
 * @author HE176322
 */
public class TextDocument {

    // The lines of the file, untouched (spaces, tabs, blank lines).
    private ArrayList<String> lines;
    // The document after every rule, as one line of text.
    private String normalizedText;

    // JavaBean constructor: an empty document.
    public TextDocument() {
        this.lines = new ArrayList<>();
        this.normalizedText = "";
    }

    // Creates a document from the lines of a file.
    public TextDocument(ArrayList<String> lines) {
        this.lines = lines;
        this.normalizedText = "";
    }

    // Returns the raw lines.
    public ArrayList<String> getLines() {
        return lines;
    }

    // Replaces the raw lines.
    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    // Returns the normalized text.
    public String getNormalizedText() {
        return normalizedText;
    }

    // Stores the normalized text.
    public void setNormalizedText(String normalizedText) {
        this.normalizedText = normalizedText;
    }

    // Counts the raw lines.
    public int getLineCount() {
        return lines.size();
    }

    // The whole document as ONE text, lines separated by a line break - the shape every
    // normalization rule works on.
    public String getFullText() {
        StringBuilder text = new StringBuilder();
        // append every line, with a line break between two of them
        for (int i = 0; i < lines.size(); i++) {
            // not the first line: separate it from the previous one
            if (i > 0) {
                text.append(Constants.LINE_BREAK);
            }
            text.append(lines.get(i));
        }
        return text.toString();
    }

    // Polymorphism: overrides Object.toString().
    @Override
    public String toString() {
        return normalizedText;
    }
}
