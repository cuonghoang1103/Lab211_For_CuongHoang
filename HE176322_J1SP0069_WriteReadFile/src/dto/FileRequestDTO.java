package dto;

/**
 * DTO carrying what main prepared FROM main INTO the controller: the path, and the
 * content - typed by the user for writing, or read from the file by main for reading
 * (checklist 1.1: reading a file happens in main).
 *
 * @author HE176322
 */
public class FileRequestDTO {

    // Path typed by the user.
    private String path;

    // Content typed by the user (write), or read by main from the file (read).
    private String content;

    // Creates an empty request; main fills it through the setters.
    public FileRequestDTO() {
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
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
