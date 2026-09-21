package service;

import constants.Constants;
import java.io.File;
import java.io.FileFilter;

/**
 * CONCRETE STRATEGY for java.io.FileFilter: accepts the files bigger than n kilobytes
 * (option 3).
 *
 * @author HE176322
 */
public class SizeFileFilter implements FileFilter {

    // Files must be strictly bigger than this many bytes.
    private long minBytes;

    // Creates the filter for "size > n KB".
    public SizeFileFilter(int sizeInKilobytes) {
        // long arithmetic: n * 1024 as an int overflows above 2 GB
        this.minBytes = sizeInKilobytes * Constants.KILOBYTE;
    }

    // Keeps a real file (not a folder) whose size is greater than n KB.
    @Override
    public boolean accept(File file) {
        return file.isFile() && (file.length() > minBytes);
    }
}
