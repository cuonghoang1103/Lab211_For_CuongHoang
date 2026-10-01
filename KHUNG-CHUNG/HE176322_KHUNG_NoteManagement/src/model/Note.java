package model;

import constants.Constants;

/**
 * MODEL: one note - its ID and its content.
 *
 * @author HE176322
 */
public class Note {

    // Unique ID, given by the repository (last ID + 1).
    private int id;

    // The text of the note.
    private String content;

    // JavaBean constructor: an empty note, filled through the setters.
    public Note() {
    }

    // Creates a note with every field filled in.
    public Note(int id, String content) {
        this.id = id;
        this.content = content;
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Changes the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the content.
    public String getContent() {
        return content;
    }

    // Changes the content.
    public void setContent(String content) {
        this.content = content;
    }

    // The note as one row of the table.
    @Override
    public String toString() {
        return String.format(Constants.ROW_FORMAT, id, content);
    }
}
