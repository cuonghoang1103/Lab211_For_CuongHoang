package dto;

/**
 * DTO carrying the answer of one menu option FROM the controller OUT TO the view - a
 * JavaBean. Add and delete fill the message; translate fills the meaning, or the message
 * when the word is not found.
 *
 * @author HE176322
 */
public class WordResponseDTO {

    // The one-line result, e.g. "Successful"; null when the answer is a translation.
    private String message;

    // The meaning found by translate; null when the answer is a message.
    private String vietnamese;

    // JavaBean constructor: an empty answer, filled through the setters.
    public WordResponseDTO() {
    }

    // Returns the one-line result.
    public String getMessage() {
        return message;
    }

    // Sets the one-line result.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the meaning.
    public String getVietnamese() {
        return vietnamese;
    }

    // Sets the meaning.
    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }
}
