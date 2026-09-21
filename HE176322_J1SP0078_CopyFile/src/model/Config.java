package model;

import constants.Constants;

/**
 * MODEL: the three settings of config.properties, as one object - a JavaBean (private
 * fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class Config {

    // COPY_FOLDER: the folder the files are copied from.
    private String copyFolder;

    // DATA_TYPE: the accepted file types.
    private String dataType;

    // PATH: the folder the files are copied into.
    private String path;

    // JavaBean constructor: an empty config, filled through the setters.
    public Config() {
        this.copyFolder = "";
        this.dataType = "";
        this.path = "";
    }

    // Creates a config with every setting filled in.
    public Config(String copyFolder, String dataType, String path) {
        this.copyFolder = copyFolder;
        this.dataType = dataType;
        this.path = path;
    }

    // Returns COPY_FOLDER.
    public String getCopyFolder() {
        return copyFolder;
    }

    // Changes COPY_FOLDER.
    public void setCopyFolder(String copyFolder) {
        this.copyFolder = copyFolder;
    }

    // Returns DATA_TYPE.
    public String getDataType() {
        return dataType;
    }

    // Changes DATA_TYPE.
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    // Returns PATH.
    public String getPath() {
        return path;
    }

    // Changes PATH.
    public void setPath(String path) {
        this.path = path;
    }

    // Polymorphism: overrides Object.toString() to show the three settings on one line.
    @Override
    public String toString() {
        return String.format(Constants.CONFIG_FORMAT, copyFolder, dataType, path);
    }
}
