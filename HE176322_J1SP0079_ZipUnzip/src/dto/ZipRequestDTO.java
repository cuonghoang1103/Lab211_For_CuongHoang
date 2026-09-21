package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller - a JavaBean (private
 * fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class ZipRequestDTO {

    // Folder to zip, or zip file to unzip.
    private String sourcePath;

    // Destination folder.
    private String destinationPath;

    // Zip file name (compression only).
    private String zipName;

    // JavaBean constructor: an empty request, filled through the setters.
    public ZipRequestDTO() {
    }

    // Returns the source path.
    public String getSourcePath() {
        return sourcePath;
    }

    // Sets the source path.
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    // Returns the destination folder.
    public String getDestinationPath() {
        return destinationPath;
    }

    // Sets the destination folder.
    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    // Returns the zip name.
    public String getZipName() {
        return zipName;
    }

    // Sets the zip name.
    public void setZipName(String zipName) {
        this.zipName = zipName;
    }
}
