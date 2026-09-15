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
    private ArrayList<String> extensions = new ArrayList<>();

    // Turns DATA_TYPE ("*.CSV,*.WAV", "csv, wav" ...) into extensions.
    public DataTypeFilter(String dataType) {
        // one comma-separated part = one file type
        for (String part : dataType.split(Constants.TYPE_SEPARATOR)) {
            String extension = part.trim().toLowerCase();
            // "*.csv" -> ".csv"
            if (extension.startsWith(Constants.WILDCARD)) {
                extension = extension.substring(Constants.WILDCARD.length());
            }
            // "csv" -> ".csv"
            if (!extension.isEmpty() && !extension.startsWith(Constants.EXTENSION_DOT)) {
                extension = Constants.EXTENSION_DOT + extension;
            }
            // an empty part (",," or a trailing comma) accepts nothing
            if (!extension.isEmpty()) {
                extensions.add(extension);
            }
        }
    }

    // The Strategy method: File.listFiles calls it once per entry.
    @Override
    public boolean accept(File file) {
        // sub-folders are never copied
        if (!file.isFile()) {
            return false;
        }
        String name = file.getName().toLowerCase();
        // accepted as soon as one extension matches
        for (String extension : extensions) {
            // the name ends with this accepted extension
            if (name.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
