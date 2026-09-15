package dto;

/**
 * DTO carrying the config the user typed, FROM main INTO the controller - a JavaBean
 * (private fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class ConfigRequestDTO {

    // COPY_FOLDER typed by the user.
    private String copyFolder;
    // DATA_TYPE typed by the user.
    private String dataType;
    // PATH typed by the user.
    private String path;
    // True when the user has just typed the config because the file was not found: the
    // config must then be saved before it is checked.
    private boolean newConfig;

    // JavaBean constructor: an empty request (no typed config), filled through the
    // setters.
    public ConfigRequestDTO() {
    }

    // Returns COPY_FOLDER.
    public String getCopyFolder() {
        return copyFolder;
    }

    // Sets COPY_FOLDER.
    public void setCopyFolder(String copyFolder) {
        this.copyFolder = copyFolder;
    }

    // Returns DATA_TYPE.
    public String getDataType() {
        return dataType;
    }

    // Sets DATA_TYPE.
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    // Returns PATH.
    public String getPath() {
        return path;
    }

    // Sets PATH.
    public void setPath(String path) {
        this.path = path;
    }

    // Tells whether the config was just typed and must be saved first.
    public boolean isNewConfig() {
        return newConfig;
    }

    // Marks the config as just typed (or not).
    public void setNewConfig(boolean newConfig) {
        this.newConfig = newConfig;
    }
}
