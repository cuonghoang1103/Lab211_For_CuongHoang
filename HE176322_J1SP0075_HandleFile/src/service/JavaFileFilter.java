package service;

import constants.Constants;
import java.io.File;
import java.io.FilenameFilter;

/**
 * CONCRETE STRATEGY for java.io.FilenameFilter: accepts the files whose name ends with
 * ".java" (option 2).
 *
 * @author HE176322
 */
public class JavaFileFilter implements FilenameFilter {

    // Creates the filter; it needs no data.
    public JavaFileFilter() {
    }

    // Keeps a real file (not a folder) whose name ends with .java, in upper or lower
    // case.
    @Override
    public boolean accept(File directory, String name) {
        return new File(directory, name).isFile()
                && name.toLowerCase().endsWith(Constants.JAVA_EXTENSION);
    }
}
