package utils;

import constants.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Reads, writes and lists files on disk.
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

    // Lists the names of a directory's entries that the filter keeps.
    public static ArrayList<String> listFileNames(String path, FilenameFilter filter) {
        ArrayList<String> names = new ArrayList<>();
        String[] found = new File(path).list(filter);
        // list() gives null (not an empty array) for a file or unreadable folder
        if (found != null) {
            names.addAll(Arrays.asList(found));
            // the disk order differs between machines; sorted is always the same
            Collections.sort(names);
        }
        return names;
    }

    // Lists the entries of a directory that the filter keeps.
    public static File[] listFiles(String path, FileFilter filter) {
        File[] found = new File(path).listFiles(filter);
        // listFiles() gives null for a file or unreadable folder
        if (found == null) {
            return new File[0];
        }
        Arrays.sort(found);
        return found;
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

    // Adds one line at the END of a file.
    public static void appendLine(String path, String content) throws Exception {
        // try-with-resources flushes and closes the file even on failure
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            // a folder, or a read-only file
            throw new Exception(Message.CANNOT_WRITE);
        }
    }
}
