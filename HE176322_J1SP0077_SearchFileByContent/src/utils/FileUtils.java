package utils;

import constants.Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads text files and lists folders.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Tells whether something (file or folder) is at the path.
    public static boolean isExist(String path) {
        return new File(path).exists();
    }

    // Tells whether the path is a file (not a folder).
    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    // Lists the entries directly inside a folder.
    public static File[] listFiles(String path) throws Exception {
        File[] files = new File(path).listFiles();
        // null (not an empty array) means the folder could not be read
        if (files == null) {
            throw new Exception(String.format(Message.CANNOT_READ_FOLDER, path));
        }
        return files;
    }

    // Reads every line of a text file.
    public static ArrayList<String> readLines(String path) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            // read until readLine() returns null = end of file
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // no permission to read, or the file vanished
            throw new Exception(String.format(Message.CANNOT_READ_FILE, path));
        }
        return lines;
    }
}
