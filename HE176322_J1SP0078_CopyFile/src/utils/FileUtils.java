package utils;

import constants.Constants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * File and folder helpers: read/write the lines of a text file, list a folder, create a
 * folder, copy a file byte for byte.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Tells whether a FILE (not a folder) exists at the path.
    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    // Tells whether a FOLDER exists at the path.
    public static boolean isFolder(String path) {
        return new File(path).isDirectory();
    }

    // Makes sure the folder exists, creating it (and its missing parents) when needed.
    public static boolean makeFolder(String path) {
        File folder = new File(path);
        return folder.isDirectory() || folder.mkdirs();
    }

    // Tells whether two paths are the SAME folder on the disk.
    public static boolean isSamePath(String first, String second) throws IOException {
        return new File(first).getCanonicalFile().equals(new File(second).getCanonicalFile());
    }

    // Reads every line of a UTF-8 text file.
    public static ArrayList<String> readLines(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            // readLine() returns null at the end of the file
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return lines;
    }

    // Replaces the content of a UTF-8 text file with the given lines.
    public static void writeLines(String path, ArrayList<String> lines) throws IOException {
        // try-with-resources flushes and closes the file even on failure
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {
            // one element of the list = one line of the file
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Lists the entries of a folder that the filter accepts, sorted by name (listFiles
    // returns them in whatever order the disk likes).
    public static ArrayList<File> listFiles(String folder, FileFilter filter) {
        ArrayList<File> result = new ArrayList<>();
        File[] children = new File(folder).listFiles(filter);
        // null means the folder does not exist or cannot be read
        if (children == null) {
            return result;
        }
        Arrays.sort(children);
        result.addAll(Arrays.asList(children));
        return result;
    }

    // Copies one file as BYTES - FileInputStream/FileOutputStream, never
    // FileReader/FileWriter.
    public static void copyBinary(File from, File to) throws IOException {
        // both streams are closed (in reverse order) even when write() fails
        try (InputStream in = new BufferedInputStream(new FileInputStream(from));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(to))) {
            byte[] buffer = new byte[Constants.BUFFER_SIZE];
            int count = in.read(buffer);
            // read() returns -1 when the whole file has been read
            while (count != Constants.END_OF_STREAM) {
                out.write(buffer, 0, count);
                count = in.read(buffer);
            }
        }
    }

    // Compares two files byte for byte, so a copy can be PROVED identical.
    public static boolean isSameContent(File first, File second) throws IOException {
        // different sizes can never be the same content
        if (first.length() != second.length()) {
            return false;
        }
        // both streams are closed even when reading fails
        try (InputStream left = new BufferedInputStream(new FileInputStream(first));
                InputStream right = new BufferedInputStream(new FileInputStream(second))) {
            int x = left.read();
            // walk both files together until the end of the first one
            while (x != Constants.END_OF_STREAM) {
                // one different byte is enough to say "not the same"
                if (x != right.read()) {
                    return false;
                }
                x = left.read();
            }
        }
        return true;
    }
}
