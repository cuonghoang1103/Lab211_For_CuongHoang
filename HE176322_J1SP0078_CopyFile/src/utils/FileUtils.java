package utils;

import constants.Constants;
import constants.Message;
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
 * folder, copy a file byte for byte. readLines is called by main (checklist 1.1: main
 * reads the files); writeLines by the repository; the rest by the service.
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

        // already there, or created now
        return folder.isDirectory() || folder.mkdirs();
    }

    // Tells whether two paths are the SAME folder on the disk.
    public static boolean isSamePath(String first, String second) throws IOException {
        return new File(first).getCanonicalFile().equals(new File(second).getCanonicalFile());
    }

    // Reads every line of the UTF-8 config file; the brief's "Can't read File Configure"
    // comes from here.
    public static ArrayList<String> readLines(String path) throws Exception {
        ArrayList<String> lineList = new ArrayList<>();
        String line = "";

        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            line = reader.readLine();

            // readLine() returns null at the end of the file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // missing, locked or unreadable file
            throw new Exception(Message.CANNOT_READ);
        }

        return lineList;
    }

    // Replaces the content of a UTF-8 text file with the given lines.
    public static void writeLines(String path, ArrayList<String> lineList) throws IOException {
        // try-with-resources flushes and closes the file even on failure
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {
            // one element of the list = one line of the file
            for (String line : lineList) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Lists the entries of a folder that the filter accepts, sorted by name (listFiles
    // returns them in whatever order the disk likes).
    public static ArrayList<File> listFiles(String folder, FileFilter filter) {
        ArrayList<File> fileList = new ArrayList<>();
        File[] fileArray = new File(folder).listFiles(filter);

        // null means the folder does not exist or cannot be read
        if (fileArray == null) {
            return fileList;
        }

        // the disk order differs between machines; sorted by name is always the same
        Arrays.sort(fileArray);
        fileList.addAll(Arrays.asList(fileArray));
        return fileList;
    }

    // Copies one file as BYTES - FileInputStream/FileOutputStream, never
    // FileReader/FileWriter.
    public static void copyBinary(File source, File target) throws IOException {
        byte[] bufferArray = new byte[Constants.BUFFER_SIZE];
        int count = 0;

        // the source stream is closed even when reading fails
        try (InputStream input = new BufferedInputStream(new FileInputStream(source))) {
            // the target stream is closed (before the source) even when write() fails
            try (OutputStream output = new BufferedOutputStream(new FileOutputStream(target))) {
                count = input.read(bufferArray);

                // read() returns -1 when the whole file has been read
                while (count != Constants.END_OF_STREAM) {
                    output.write(bufferArray, 0, count);
                    count = input.read(bufferArray);
                }
            }
        }
    }

    // Compares two files byte for byte, so a copy can be PROVED identical.
    public static boolean isSameContent(File source, File target) throws IOException {
        int sourceByte = 0;

        // different sizes can never be the same content
        if (source.length() != target.length()) {
            return false;
        }

        // the source stream is closed even when reading fails
        try (InputStream sourceInput = new BufferedInputStream(new FileInputStream(source))) {
            // the target stream too
            try (InputStream targetInput = new BufferedInputStream(new FileInputStream(target))) {
                sourceByte = sourceInput.read();

                // walk both files together until the end of the source
                while (sourceByte != Constants.END_OF_STREAM) {
                    // one different byte is enough to say "not the same"
                    if (sourceByte != targetInput.read()) {
                        return false;
                    }

                    sourceByte = sourceInput.read();
                }
            }
        }

        return true;
    }
}
