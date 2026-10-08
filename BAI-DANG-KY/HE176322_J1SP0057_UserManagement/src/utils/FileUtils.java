package utils;

import constants.Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class FileUtils {

    // Ngan khong cho tao object
    private FileUtils() {
    }

    // Doc tung dong cua file vao danh sach; file chua co thi tra ve danh sach rong
    public static ArrayList<String> readLines(String path) throws Exception {
        ArrayList<String> lineList = new ArrayList<>();
        File file = new File(path);

        // File chua ton tai thi chua co tai khoan nao
        if (!file.exists()) {
            return lineList;
        }

        // Mo file de doc, try-with-resources tu dong dong file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            // Doc den khi het file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Loi khi doc file
            throw new Exception(Message.CANNOT_READ);
        }
        return lineList;
    }

    // Ghi them 1 dong vao CUOI file (kiem tra file ton tai truoc khi ghi)
    public static void appendLine(String path, String line) throws Exception {
        File file = new File(path);

        // Mo file de ghi, try-with-resources tu dong dong file
        try {
            // File chua ton tai thi tao file moi
            if (!file.exists()) {
                file.createNewFile();
            }

            // Ghi noi tiep vao cuoi file (true = append)
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            // Loi khi tao hoac ghi file
            throw new Exception(Message.CANNOT_WRITE);
        }
    }
}
