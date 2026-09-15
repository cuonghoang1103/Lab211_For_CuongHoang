package dto;

/**
 * DTO carrying the five answers FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class PathResponseDTO {

    // Disk driver.
    private String disk;
    // File extension.
    private String extension;
    // File name without extension.
    private String fileName;
    // Path to the file's folder.
    private String path;
    // Folder names between the disk and the file.
    private String[] folders;

    // Creates an empty response; the service fills it through the setters.
    public PathResponseDTO() {
    }

    // Returns the disk driver.
    public String getDisk() {
        return disk;
    }

    // Sets the disk driver.
    public void setDisk(String disk) {
        this.disk = disk;
    }

    // Returns the extension.
    public String getExtension() {
        return extension;
    }

    // Sets the extension.
    public void setExtension(String extension) {
        this.extension = extension;
    }

    // Returns the file name.
    public String getFileName() {
        return fileName;
    }

    // Sets the file name.
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Returns the folder path.
    public String getPath() {
        return path;
    }

    // Sets the folder path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the folders.
    public String[] getFolders() {
        return folders;
    }

    // Sets the folders.
    public void setFolders(String[] folders) {
        this.folders = folders;
    }
}
