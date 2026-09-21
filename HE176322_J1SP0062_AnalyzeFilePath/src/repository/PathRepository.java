package repository;

import model.FilePath;

/**
 * REPOSITORY: holds the data of the program - the path the user typed, as the model
 * FilePath - and only simple CRUD on it. No analysis, no print.
 *
 * @author HE176322
 */
public class PathRepository {

    // The path the program works on (the model).
    private FilePath filePath;

    // Creates the store with an empty path.
    public PathRepository() {
        filePath = new FilePath();
    }

    // Create: wraps the path typed by the user in the model and keeps it.
    public void saveFilePath(String fullPath) {
        filePath = new FilePath(fullPath);
    }

    // Read: returns the path kept by the last save.
    public FilePath getFilePath() {
        return filePath;
    }
}
