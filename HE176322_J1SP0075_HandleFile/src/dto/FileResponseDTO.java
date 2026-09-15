package dto;

import java.util.ArrayList;

/**
 * DTO carrying a list of file names FROM the controller OUT TO the view (options 2 and
 * 3).
 *
 * @author HE176322
 */
public class FileResponseDTO {

    // The file names to print, already sorted.
    private ArrayList<String> fileNames;

    // JavaBean constructor: an empty list of names.
    public FileResponseDTO() {
        this.fileNames = new ArrayList<>();
    }

    // Creates the response with its names.
    public FileResponseDTO(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    // Returns the names.
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    // Sets the names.
    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }
}
