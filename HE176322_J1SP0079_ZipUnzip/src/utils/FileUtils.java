package utils;

import constants.Constants;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * File and folder helpers shared by zipping and unzipping: walk a folder, create a
 * folder, copy one stream into another, compare paths.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Makes sure the folder exists, creating it and its missing parents.
    public static boolean makeFolder(File folder) {
        return folder.isDirectory() || folder.mkdirs();
    }

    // Adds every folder and file under "folder" to the list, depth-first and sorted by
    // name (listFiles returns them in whatever order the disk likes, so two runs could
    // otherwise zip in a different order).
    public static void listAll(File folder, ArrayList<File> result) {
        File[] children = folder.listFiles();
        // null means the folder cannot be read: nothing to add
        if (children == null) {
            return;
        }
        Arrays.sort(children);
        // a folder is added, then walked; a file is just added
        for (File child : children) {
            result.add(child);
            // go down into sub-folders
            if (child.isDirectory()) {
                listAll(child, result);
            }
        }
    }

    // The name of "file" relative to "folder", with "/" separators on every system (the
    // zip format demands "/"), and a trailing "/" for a folder: data +
    // data/docs/guide.txt gives "docs/guide.txt".
    public static String relativeName(File folder, File file) {
        return folder.toURI().relativize(file.toURI()).getPath();
    }

    // Tells whether two paths are the same file on the disk (canonical paths, so "a/../b"
    // and "b" match).
    public static boolean isSameFile(File first, File second) throws IOException {
        return first.getCanonicalFile().equals(second.getCanonicalFile());
    }

    // Tells whether "target" lies INSIDE "folder" once "..", "." and links are resolved -
    // the zip slip guard: an entry named ../../x must not escape the destination folder.
    public static boolean isInside(File target, File folder) throws IOException {
        String root = folder.getCanonicalPath() + File.separator;
        return target.getCanonicalPath().startsWith(root);
    }

    // Copies every byte of "in" into "out" and closes NEITHER stream: when zipping, "out"
    // is the shared ZipOutputStream, and closing it would end the whole zip after the
    // first file.
    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[Constants.BUFFER_SIZE];
        int count = in.read(buffer);
        // read() returns -1 at the end of the stream (or of the zip entry)
        while (count != Constants.END_OF_STREAM) {
            out.write(buffer, 0, count);
            count = in.read(buffer);
        }
    }
}
