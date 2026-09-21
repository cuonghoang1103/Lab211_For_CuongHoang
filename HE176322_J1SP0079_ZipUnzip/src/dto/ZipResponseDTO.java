package dto;

import java.util.ArrayList;

/**
 * DTO carrying the result FROM the controller OUT TO the view: success or not, the files
 * that went through, and the reason of a failure - a JavaBean.
 *
 * @author HE176322
 */
public class ZipResponseDTO {

    // The status the brief's compressTo/extractTo return.
    private boolean success;

    // Names of the files zipped or unzipped.
    private ArrayList<String> fileNameList;

    // Why the job failed; empty on success.
    private String error;

    // JavaBean constructor: an empty result, filled through the setters.
    public ZipResponseDTO() {
        fileNameList = new ArrayList<>();
        error = "";
    }

    // Tells whether the job succeeded.
    public boolean isSuccess() {
        return success;
    }

    // Sets the status.
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Returns the names of the files.
    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    // Sets the names of the files.
    public void setFileNameList(ArrayList<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    // Returns the reason of a failure.
    public String getError() {
        return error;
    }

    // Sets the reason of a failure.
    public void setError(String error) {
        this.error = error;
    }
}
