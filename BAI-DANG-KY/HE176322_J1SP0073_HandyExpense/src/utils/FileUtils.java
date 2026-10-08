package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    // Constructor private: lop chi co ham static
    private FileUtils() {
    }

    // Doc tat ca dong cua file; file chua co thi tra ve danh sach rong
    public static List<String> readLines(String fileName) throws IOException {
        List<String> lineList = new ArrayList<>();
        File file = new File(fileName);

        // Chua co file (lan chay dau) thi khong co gi de doc
        if (!file.exists()) {
            return lineList;
        }

        // try-with-resources: tu dong dong file ke ca khi loi
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            // Doc tung dong cho den het file
            while (line != null) {
                lineList.add(line);
                line = reader.readLine();
            }
        }
        return lineList;
    }

    // Ghi de toan bo file bang danh sach dong moi
    public static void writeLines(String fileName, List<String> lineList) throws IOException {
        // try-with-resources: tu dong dong file ke ca khi loi
        try (PrintWriter writer = new PrintWriter(fileName)) {
            // Ghi tung dong
            for (String line : lineList) {
                writer.println(line);
            }

            // PrintWriter khong nem loi nen phai tu hoi
            if (writer.checkError()) {
                throw new IOException(fileName);
            }
        }
    }
}
