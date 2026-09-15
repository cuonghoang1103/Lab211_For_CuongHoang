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
 * Reading and writing a text file line by line (UTF-8).
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Tells whether the file is there.
    public static boolean exists(String fileName) {
        return new File(fileName).exists();
    }

    // Returns every line of the file.
    public static ArrayList<String> readLines(String fileName) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // try-with-resources closes the reader even when a read fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            // until the end of the file
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // missing, locked, no permission...
            throw new Exception(String.format(Message.FILE_ERROR, fileName));
        }
        return lines;
    }

    // Replaces the file with these lines (FileOutputStream truncates, never appends).
    public static void writeLines(String fileName, ArrayList<String> lines) throws Exception {
        // try-with-resources closes (and flushes) the writer even when a write fails
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            // one line per record
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // read-only folder, disk full...
            throw new Exception(String.format(Message.FILE_ERROR, fileName));
        }
    }
}
