package dto;

/**
 * DTO controller -> view: the result of loading or storing the data file.
 *
 * @author HE176322
 */
public class FileResponseDTO {

    // Name of the data file.
    private String fileName;
    // Vehicles loaded or stored.
    private int count;
    // Damaged lines skipped while loading.
    private int skipped;

    // JavaBean constructor.
    public FileResponseDTO() {
    }

    // Returns the file name.
    public String getFileName() {
        return fileName;
    }

    // Changes the file name.
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Returns the count.
    public int getCount() {
        return count;
    }

    // Changes the count.
    public void setCount(int count) {
        this.count = count;
    }

    // Returns the damaged lines.
    public int getSkipped() {
        return skipped;
    }

    // Changes the damaged lines.
    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }
}
