package model;

import constants.Constants;
import java.util.ArrayList;

/**
 * MODEL: one zip or unzip job - where from, where to, under which name, and which files
 * went through. The repository keeps every job of the run.
 *
 * @author HE176322
 */
public class ArchiveJob {

    // Folder (or one file) to zip, or zip file to unzip.
    private String sourcePath;

    // Folder that receives the zip file or the unzipped files.
    private String destinationPath;

    // Name of the zip file to create (compress only).
    private String zipName;

    // Names (inside the zip) of the files that went through.
    private ArrayList<String> fileNameList;

    // Why the job failed; empty while nothing went wrong.
    private String error;

    // JavaBean constructor: an empty job.
    public ArchiveJob() {
        fileNameList = new ArrayList<>();
        error = "";
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

    // Returns the zip file name.
    public String getZipName() {
        return zipName;
    }

    // Sets the zip file name.
    public void setZipName(String zipName) {
        this.zipName = zipName;
    }

    // Returns the names of the files that went through.
    public ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    // Replaces the list of file names.
    public void setFileNameList(ArrayList<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    // Records one more file that went through the job (the job's own behaviour, so the
    // tasks never touch the list directly).
    public void addFileName(String fileName) {
        fileNameList.add(fileName);
    }

    // Returns why the job failed.
    public String getError() {
        return error;
    }

    // Records why the job failed.
    public void setError(String error) {
        this.error = error;
    }

    // Polymorphism: overrides Object.toString() to show the job on one line.
    @Override
    public String toString() {
        return String.format(Constants.JOB_FORMAT, sourcePath, destinationPath);
    }
}
