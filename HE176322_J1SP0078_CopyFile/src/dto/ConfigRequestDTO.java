package dto;

import java.util.ArrayList;

/**
 * DTO carrying what main prepared FROM main INTO the controller: the config the user
 * typed (the brief's form), or the lines main read from config.properties (checklist
 * 1.1: reading a file happens in main) - a JavaBean (private fields, public no-argument
 * constructor, getters/setters).
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

    // The lines of config.properties, read by main.
    private ArrayList<String> lineList;

    // JavaBean constructor: an empty request, filled through the setters.
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

    // Returns the lines of config.properties.
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines of config.properties.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
