package dto;

/**
 * DTO carrying what the user typed FROM main TO the controller, already checked.
 *
 * @author HE176322
 */
public class NoteRequestDTO {

    // Option 2: the ID to delete.
    private int id;

    // Option 1: the content of the new note.
    private String content;

    // JavaBean constructor: an empty request, filled through the setters.
    public NoteRequestDTO() {
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Sets the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the content.
    public String getContent() {
        return content;
    }

    // Sets the content.
    public void setContent(String content) {
        this.content = content;
    }
}
