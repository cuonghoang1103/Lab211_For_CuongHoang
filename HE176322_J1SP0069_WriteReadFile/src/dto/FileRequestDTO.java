package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the path, and for
 * writing also the content.
 *
 * @author HE176322
 */
public class FileRequestDTO {

    // Path typed by the user.
    private String path;
    // Content typed by the user (write only); lines already joined.
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
