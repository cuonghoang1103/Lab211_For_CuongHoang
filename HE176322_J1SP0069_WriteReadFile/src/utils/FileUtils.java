package utils;

import constants.Constants;
import constants.Message;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The brief's two methods writeFile and readFile: move text between a String and a file
 * on the disk. readFile is called by main (checklist 1.1: main reads the files); writeFile
 * is called by the repository when it saves the typed file.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Function 1 of the brief: writes the content to the file, replacing what was there
    // before.
    public static boolean writeFile(String path, String content) {
        File file = new File(path);

        // try-with-resources flushes and closes the writer even on failure
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            // missing folder, read-only file, full disk...
            return false;
        }
    }

    // Function 2 of the brief: reads the whole file back into a String, character by
    // character with FileReader, so the text comes back exactly as it was written (line
    // breaks included).
    public static String readFile(String path) throws Exception {
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        int character = 0;

        // a missing path, or a folder, is not a file that can be read
        if (!file.isFile()) {
            throw new Exception(Message.FILE_NOT_EXIST);
        }

        // try-with-resources closes the reader even when reading fails
        try (FileReader reader = new FileReader(file)) {
            character = reader.read();

            // read() returns -1 at the end of the file
            while (character != Constants.END_OF_FILE) {
                content.append((char) character);
                character = reader.read();
            }
        } catch (IOException e) {
            // locked or unreadable file
            throw new Exception(Message.CANNOT_READ);
        }

        return content.toString();
    }
}
