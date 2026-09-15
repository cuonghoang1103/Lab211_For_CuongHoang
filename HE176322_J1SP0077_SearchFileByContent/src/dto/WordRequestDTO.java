package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: a path (a file for
 * option 1, a folder for option 2) and the word.
 *
 * @author HE176322
 */
public class WordRequestDTO {

    // The file path (option 1) or folder path (option 2).
    private String path;
    // The word to count or to look for.
    private String word;

    // JavaBean constructor: an empty request, filled through the setters.
    public WordRequestDTO() {
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the word.
    public String getWord() {
        return word;
    }

    // Sets the word.
    public void setWord(String word) {
        this.word = word;
    }
}
