package model;

/**
 * MODEL: one text file of the program - where it is and what it holds.
 *
 * @author HE176322
 */
public class TextFile {

    // Path of the file on the disk.
    private String path;
    // Text stored in the file.
    private String content;

    // JavaBean constructor: an empty file description, filled by setters.
    public TextFile() {
    }

    // Creates a file description with both fields filled in.
    public TextFile(String path, String content) {
        this.path = path;
        this.content = content;
    }

    // Returns the path.
    public String getPath() {
        return path;
    }

    // Changes the path.
    public void setPath(String path) {
        this.path = path;
    }

    // Returns the content.
    public String getContent() {
        return content;
    }

    // Changes the content.
    public void setContent(String content) {
        this.content = content;
    }

    // Polymorphism: overrides Object.toString().
    @Override
    public String toString() {
        return content;
    }
}
