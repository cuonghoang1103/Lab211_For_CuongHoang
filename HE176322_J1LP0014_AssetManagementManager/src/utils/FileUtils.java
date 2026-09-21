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
 * Reading and writing a text file line by line (UTF-8). A utility: static methods only -
 * main reads the four .dat files through it, the repositories write through it.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Returns every line of the file; a file not created yet (first run) has no line.
    public static ArrayList<String> readLines(String fileName) throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // first run: nothing was stored yet
        if (!new File(fileName).exists()) {
            return lineList;
        }

        // try-with-resources closes the reader even when a read fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            // until the end of the file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // locked, no permission...
            throw new Exception(String.format(Message.FILE_ERROR, fileName));
        }

        return lineList;
    }

    // Replaces the file with these lines (FileOutputStream truncates, never appends).
    public static void writeLines(String fileName, ArrayList<String> lineList)
            throws Exception {
        // try-with-resources closes (and flushes) the writer even when a write fails
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            // one line per record
            for (String line : lineList) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // read-only folder, disk full...
            throw new Exception(String.format(Message.FILE_ERROR, fileName));
        }
    }
}
