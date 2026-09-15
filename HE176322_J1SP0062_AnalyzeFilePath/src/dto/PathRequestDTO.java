package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the path to analyse.
 *
 * @author HE176322
 */
public class PathRequestDTO {

    // The whole path typed by the user, already validated.
    private String fullPath;

    // Creates an empty request; main fills it through the setter.
    public PathRequestDTO() {
    }

    // Returns the path.
    public String getFullPath() {
        return fullPath;
    }

    // Sets the path.
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
