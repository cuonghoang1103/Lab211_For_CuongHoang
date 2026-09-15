package utils;

import constants.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads and writes text files.
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
            // a folder, or no permission to read
            throw new Exception(Message.CANNOT_READ);
        }
        return lines;
    }

    // Writes the lines into a file, replacing what was there.
    public static void writeLines(String path, ArrayList<String> lines)
            throws Exception {
        // try-with-resources flushes and closes the file even on failure
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // one element of the list = one line of the file
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // a folder, a missing parent folder, a read-only file...
            throw new Exception(Message.CANNOT_WRITE);
        }
    }
}
