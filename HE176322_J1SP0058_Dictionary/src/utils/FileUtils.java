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
 * Reads and writes the text file of the dictionary, line by line.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Tells whether the data file is already on the disk (the brief: "test has data files
    // or not yet").
    public static boolean isFileExist(String path) {
        File file = new File(path);

        return file.exists() && file.isFile();
    }

    // Reads every line of a text file.
    public static ArrayList<String> readLines(String path) throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            // read until readLine() returns null = end of file
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

    // Replaces the whole content of a text file with the given lines (the brief's
    // updateDatabase "overwrites the data on file").
    public static void writeLines(String path, ArrayList<String> lineList) throws Exception {
        // try-with-resources flushes and closes the file even on failure
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {
            // one element of the list = one line of the file
            for (String line : lineList) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // read-only file, full disk, missing folder...
            throw new Exception(Message.CANNOT_WRITE);
        }
    }
}
