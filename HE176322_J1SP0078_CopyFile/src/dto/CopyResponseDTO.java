package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result of the copy FROM the controller OUT TO the view: the names of
 * the files that were copied - a JavaBean.
 *
 * @author HE176322
 */
public class CopyResponseDTO {

    // Names of the copied files, in alphabetical order.
    private ArrayList<String> fileNames;

    // JavaBean constructor: an empty result (no file copied).
    public CopyResponseDTO() {
        this.fileNames = new ArrayList<>();
    }

    // Returns the names of the copied files.
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    // Sets the names of the copied files.
    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }
}
