package service;

import constants.Constants;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * CONCRETE STRATEGY (design pattern): accepts a file when its extension is one of the
 * types listed in DATA_TYPE.
 *
 * @author HE176322
 */
public class DataTypeFilter implements FileFilter {

    // Accepted extensions, lower case with their dot.
    private ArrayList<String> extensionList;

    // Turns DATA_TYPE ("*.CSV,*.WAV", "csv, wav" ...) into extensions.
    public DataTypeFilter(String dataType) {
        String extension = "";

        // no accepted type yet
        extensionList = new ArrayList<>();

        // one comma-separated part = one file type
        for (String part : dataType.split(Constants.TYPE_SEPARATOR)) {
            extension = toExtension(part);

            // an empty part (",," or a trailing comma) accepts nothing
            if (!extension.isEmpty()) {
                extensionList.add(extension);
            }
        }
    }

    // Turns one part of DATA_TYPE into an extension: "*.CSV" -> ".csv", "wav" -> ".wav".
    private String toExtension(String part) {
        String extension = part.trim().toLowerCase();

        // "*.csv" -> ".csv"
        if (extension.startsWith(Constants.WILDCARD)) {
            extension = extension.substring(Constants.WILDCARD.length());
        }

        // "csv" -> ".csv"
        if (!extension.isEmpty() && !extension.startsWith(Constants.EXTENSION_DOT)) {
            extension = String.format(Constants.EXTENSION_FORMAT, extension);
        }

        return extension;
    }

    // The Strategy method: File.listFiles calls it once per entry.
    @Override
    public boolean accept(File file) {
        String name = file.getName().toLowerCase();

        // sub-folders are never copied
        if (!file.isFile()) {
            return false;
        }

        // accepted as soon as one extension matches
        for (String extension : extensionList) {
            // the name ends with this accepted extension
            if (name.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}
