package model;

import constants.Constants;

/**
 * MODEL: one Windows path to a file: drive letter, folders and file name.
 *
 * @author HE176322
 */
public class FilePath {

    // The whole path as typed.
    private String fullPath;

    // Creates an empty path (JavaBean constructor).
    public FilePath() {
        this.fullPath = "";
    }

    // Creates the object for one path.
    public FilePath(String fullPath) {
        this.fullPath = fullPath;
    }

    // Returns the whole path.
    public String getFullPath() {
        return fullPath;
    }

    // Changes the whole path.
    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    // The brief's getDisk: everything before the FIRST backslash.
    public String getDisk() {
        int firstSlash = fullPath.indexOf(Constants.BACKSLASH);
        // no backslash at all: there is no disk part
        if (firstSlash < 0) {
            return "";
        }
        return fullPath.substring(0, firstSlash);
    }

    // The brief's getPath: everything before the LAST backslash.
    public String getPath() {
        int lastSlash = fullPath.lastIndexOf(Constants.BACKSLASH);
        // no backslash at all: the file sits in no folder
        if (lastSlash < 0) {
            return "";
        }
        return fullPath.substring(0, lastSlash);
    }

    // The brief's getFileName: the last part of the path, without its extension.
    public String getFileName() {
        String name = getNameWithExtension();
        int lastDot = name.lastIndexOf(Constants.DOT);
        // "test.txt": cut before the dot; "hosts" or ".gitignore": keep all
        if (lastDot > 0) {
            return name.substring(0, lastDot);
        }
        return name;
    }

    // The brief's getExtension: what follows the last dot of the file name.
    public String getExtension() {
        String name = getNameWithExtension();
        int lastDot = name.lastIndexOf(Constants.DOT);
        // a dot that is not the first character starts the extension
        if (lastDot > 0) {
            return name.substring(lastDot + 1);
        }
        return "";
    }

    // The brief's getFolders: the folder names between the disk and the file.
    public String[] getFolders() {
        int firstSlash = fullPath.indexOf(Constants.BACKSLASH);
        int lastSlash = fullPath.lastIndexOf(Constants.BACKSLASH);
        // "C:\test.txt": one backslash only, so no folder in between
        if (firstSlash < 0 || firstSlash == lastSlash) {
            return new String[0];
        }
        return fullPath.substring(firstSlash + 1, lastSlash)
                .split(Constants.BACKSLASH_REGEX);
    }

    // The last part of the path.
    private String getNameWithExtension() {
        return fullPath.substring(fullPath.lastIndexOf(Constants.BACKSLASH) + 1);
    }

    // Polymorphism: overrides Object.toString(); returns the text, never prints it.
    @Override
    public String toString() {
        return fullPath;
    }
}
