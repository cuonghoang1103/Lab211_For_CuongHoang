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
    private ArrayList<String> fileNameList;

    // JavaBean constructor: an empty result (no file copied).
    public CopyResponseDTO() {
        this.fileNameList = new ArrayList<>();
    }

    // Returns the names of the copied files.
    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    // Sets the names of the copied files.
    public void setFileNameList(ArrayList<String> fileNameList) {
        this.fileNameList = fileNameList;
    }
}
