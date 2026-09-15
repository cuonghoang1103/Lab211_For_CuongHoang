package utils;

import constants.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Reads and writes text files, line by line.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Reads every line of a text file, exactly as it is on the disk.
    public static ArrayList<String> readLines(String path) throws Exception {
        File file = new File(path);
        // the brief's first case: the file is not there
        if (!file.exists()) {
            throw new Exception(String.format(Message.FILE_NOT_FOUND, path));
        }
        // a folder with that name is not a text file
        if (file.isDirectory()) {
            throw new Exception(String.format(Message.NOT_A_FILE, path));
        }
        // no permission to read
        if (!file.canRead()) {
            throw new Exception(String.format(Message.CANNOT_READ, path));
        }
        ArrayList<String> lines = new ArrayList<>();
        // try-with-resources closes the reader even when reading fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            // read until readLine() returns null = end of file
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // the disk failed while reading
            throw new Exception(String.format(Message.CANNOT_READ, path));
        }
        return lines;
    }

    // Replaces the whole content of a text file with the given lines.
    public static void writeLines(String path, ArrayList<String> lines)
            throws Exception {
        File file = new File(path);
        // a read-only file would otherwise fail with a Java message
        if (file.exists() && !file.canWrite()) {
            throw new Exception(String.format(Message.CANNOT_WRITE, path));
        }
        // try-with-resources flushes and closes the writer even on failure
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            // one element of the list = one line of the file
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // a folder with that name, full disk, missing folder...
            throw new Exception(String.format(Message.CANNOT_WRITE, path));
        }
    }
}
