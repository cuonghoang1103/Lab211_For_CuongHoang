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
 * Reads and writes the text file user.dat, line by line.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Tells whether a file (not a folder) exists at the path.
    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    // Creates a new, empty file (used the first time an account is saved).
    public static void createFile(String path) throws Exception {
        // createNewFile throws when the folder is missing or read-only
        try {
            new File(path).createNewFile();
        } catch (IOException e) {
            // the disk refused to create the file
            throw new Exception(Message.CANNOT_WRITE);
        }
    }

    // Reads every line of a text file.
    public static ArrayList<String> readLines(String path) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            // read until readLine() returns null = end of file
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // missing, locked or unreadable file
            throw new Exception(Message.CANNOT_READ);
        }
        return lines;
    }

    // Writes one line AT THE END of a text file, keeping what is already there (the
    // brief: "user account will be appended at the end of user.dat file").
    public static void appendLine(String path, String line) throws Exception {
        // the second argument "true" of FileOutputStream opens in APPEND mode
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path, true), StandardCharsets.UTF_8))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            // read-only file, full disk, missing folder...
            throw new Exception(Message.CANNOT_WRITE);
        }
    }
}
