package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the content read from the
 * file.
 *
 * @author HE176322
 */
public class FileResponseDTO {

    // Content read from the file, to be printed.
    private String content;

    // JavaBean constructor: an empty response, filled through the setter.
    public FileResponseDTO() {
    }

    // Creates the response with the content filled in.
    public FileResponseDTO(String content) {
        this.content = content;
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
