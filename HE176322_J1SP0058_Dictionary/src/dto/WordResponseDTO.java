package dto;

/**
 * DTO carrying the result of a translation FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class WordResponseDTO {

    // The English word the user asked for.
    private String english;
    // Its meaning, or null when the word is not in the dictionary.
    private String vietnamese;

    // Creates an empty response (JavaBean constructor).
    public WordResponseDTO() {
    }

    // Creates the response with both fields filled in.
    public WordResponseDTO(String english, String vietnamese) {
        this.english = english;
        this.vietnamese = vietnamese;
    }

    // Returns the English word.
    public String getEnglish() {
        return english;
    }

    // Sets the English word.
    public void setEnglish(String english) {
        this.english = english;
    }

    // Returns the meaning.
    public String getVietnamese() {
        return vietnamese;
    }

    // Sets the meaning.
    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    // Tells whether the word was found.
    public boolean isFound() {
        return vietnamese != null;
    }
}
