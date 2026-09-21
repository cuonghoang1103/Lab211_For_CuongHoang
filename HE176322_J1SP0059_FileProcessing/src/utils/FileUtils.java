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
 * Reads and writes text files. A utility: no object, no field. Main calls readLines (the
 * checklist puts every file reading in main); the repository calls writeLines.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Reads every line of a text file; the brief's two reading errors come from here.
    public static ArrayList<String> readLines(String path) throws Exception {
        File file = new File(path);
        ArrayList<String> lineList = new ArrayList<>();
        String line = "";

        // the brief's first error: nothing at this path
        if (!file.exists()) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            line = reader.readLine();

            // read until readLine() returns null = end of file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // the brief's second error: a folder, or no read permission
            throw new Exception(Message.CANNOT_READ);
        }

        return lineList;
    }

    // Writes the lines into a file, replacing what was there.
    public static void writeLines(String path, ArrayList<String> lineList) throws Exception {
        // try-with-resources flushes and closes the file even on failure
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // one element of the list = one line of the file
            for (String line : lineList) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // missing folder, read-only file, the path is a folder...
            throw new Exception(Message.CANNOT_WRITE);
        }
    }
}
