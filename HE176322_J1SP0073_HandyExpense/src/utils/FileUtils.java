package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Reading and writing the data file, line by line. Main reads (at start-up); the
 * repository writes (after every add or delete).
 *
 * @author HE176322
 */
public final class FileUtils {

    // Private constructor: every method is called through the class name.
    private FileUtils() {
    }

    // Reads every line of a text file.
    public static ArrayList<String> readLines(String fileName) throws IOException {
        ArrayList<String> lineList = new ArrayList<>();
        File file = new File(fileName);

        // first run: no file yet, nothing to read
        if (!file.exists()) {
            return lineList;
        }

        // try-with-resources closes the reader even when reading fails
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            // read until the end of the file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        }

        return lineList;
    }

    // Rewrites a text file with the given lines.
    public static void writeLines(String fileName, ArrayList<String> lineList)
            throws IOException {
        // try-with-resources closes (and flushes) the writer in every case
        try (PrintWriter writer = new PrintWriter(fileName)) {
            // one line of text per expense
            for (String line : lineList) {
                writer.println(line);
            }

            // PrintWriter hides write errors; ask for them explicitly
            if (writer.checkError()) {
                throw new IOException(fileName);
            }
        }
    }
}
