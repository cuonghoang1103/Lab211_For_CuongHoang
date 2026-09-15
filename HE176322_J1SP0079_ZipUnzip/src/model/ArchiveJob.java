package model;

import java.util.ArrayList;

/**
 * MODEL: one zip or unzip job - where from, where to, under which name, and which files
 * went through.
 *
 * @author HE176322
 */
public class ArchiveJob {

    // Folder to zip (compress) or zip file to unzip (extract).
    private String sourcePath;
    // Folder that receives the zip file or the unzipped files.
    private String destinationPath;
    // Name of the zip file to create (compress only).
    private String zipName;
    // Names (inside the zip) of the files that went through.
    private ArrayList<String> fileNames;
    // Why the job failed; empty while nothing went wrong.
    private String error;

    // JavaBean constructor: an empty job.
    public ArchiveJob() {
        this.fileNames = new ArrayList<>();
        this.error = "";
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
    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    // Replaces the list of file names.
    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    // Records one more file that went through the job (the job's own behaviour, so the
    // tasks never touch the list directly).
    public void addFileName(String fileName) {
        fileNames.add(fileName);
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
        return sourcePath + " -> " + destinationPath;
    }
}
