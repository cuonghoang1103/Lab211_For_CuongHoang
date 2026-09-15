package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the content to count.
 *
 * @author HE176322
 */
public class CountRequestDTO {

    // The text typed by the user (already checked to be non-blank).
    private String content;

    // Creates an empty request; main fills it through the setter.
    public CountRequestDTO() {
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
