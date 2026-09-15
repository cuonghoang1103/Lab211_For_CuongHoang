package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the path of the file to
 * import or export.
 *
 * @author HE176322
 */
public class CsvRequestDTO {

    // The file path typed by the user.
    private String path;

    // JavaBean constructor: an empty request, filled through the setter.
    public CsvRequestDTO() {
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Sets the path.
    public void setPath(String path) {
        this.path = path;
    }
}
