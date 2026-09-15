package utils;

import constants.Constants;
import constants.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Reads and writes text files, and the brief's copyWordOneTimes.
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Reads every line of a text file.
    public static ArrayList<String> readLines(String path) throws Exception {
        File file = new File(path);
        // the brief's first error: nothing at this path
        if (!file.exists()) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }
        ArrayList<String> lines = new ArrayList<>();
        // try-with-resources closes the file even when reading fails
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            // read until readLine() returns null = end of file
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // the brief's second error: a folder, or no read permission
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
            // missing folder, read-only file, the path is a folder...
            throw new Exception(Message.CANNOT_WRITE);
        }
    }

    // The brief's copyWordOneTimes: finds every single word of the source file, each word
    // counted only once, and writes them into the new file, one word per line, in the
    // order they first appear.
    public static boolean copyWordOneTimes(String source, String destination)
            throws Exception {
        ArrayList<String> lines = readLines(source);
        LinkedHashSet<String> words = new LinkedHashSet<>();
        // look at every line of the source file
        for (String line : lines) {
            // split the line at spaces/tabs; add() ignores repeated words
            for (String word : line.trim().split(Constants.WORD_SEPARATOR)) {
                // a blank line splits into one empty "word": skip it
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }
        writeLines(destination, new ArrayList<>(words));
        return true;
    }
}
