package utils;

import constants.Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads text files and folders. Called by main only (checklist 1.1: main reads the
 * files); a utility: no object, no field.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Tells whether something (file or folder) is at the path.
    private static boolean isExist(String path) {
        return new File(path).exists();
    }

    // Tells whether the path is a file (not a folder).
    private static boolean isFile(String path) {
        return new File(path).isFile();
    }

    // Option 1: checks the path is a file, then reads every line of it.
    public static ArrayList<String> readTextFile(String path) throws Exception {
        // nothing at this path
        if (!isExist(path)) {
            throw new Exception(String.format(Message.FILE_NOT_FOUND, path));
        }

        // a folder has no lines to count
        if (!isFile(path)) {
            throw new Exception(String.format(Message.NOT_A_FILE, path));
        }

        return readLines(path);
    }

    // Option 2: checks the path is a folder, then reads every file directly inside it
    // (sub-folders are not searched); the result is file name -> lines of the file.
    public static HashMap<String, ArrayList<String>> readFolder(String path)
            throws Exception {
        HashMap<String, ArrayList<String>> fileLineMap = new HashMap<>();
        File[] fileArray = new File[0];

        // nothing at this path
        if (!isExist(path)) {
            throw new Exception(String.format(Message.FOLDER_NOT_FOUND, path));
        }

        // a file is not a folder to search in
        if (isFile(path)) {
            throw new Exception(String.format(Message.NOT_A_FOLDER, path));
        }

        // every entry directly inside the folder
        fileArray = new File(path).listFiles();

        // null (not an empty array) means the folder could not be read
        if (fileArray == null) {
            throw new Exception(String.format(Message.CANNOT_READ_FOLDER, path));
        }

        // read every entry that is a file; sub-folders are skipped
        for (File file : fileArray) {
            // only files have lines
            if (file.isFile()) {
                fileLineMap.put(file.getName(), readLines(file.getPath()));
            }
        }

        return fileLineMap;
    }

    // Reads every line of a text file.
    private static ArrayList<String> readLines(String path) throws Exception {
        ArrayList<String> lineList = new ArrayList<>();
        String line = "";

        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            line = reader.readLine();

            // read until readLine() returns null = end of file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // no permission to read, or the file vanished
            throw new Exception(String.format(Message.CANNOT_READ_FILE, path));
        }

        return lineList;
    }
}
