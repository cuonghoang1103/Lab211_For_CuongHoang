package repository;

import dto.FileRequestDTO;
import java.util.HashMap;
import model.TextFile;
import utils.FileUtils;

/**
 * REPOSITORY: holds the data of the program - the text files main read, each under its
 * path - and only simple CRUD on them. Adding content to a file goes to the disk through
 * FileUtils. No rule, no print, no reading of files.
 *
 * @author HE176322
 */
public class TextFileRepository {

    // Path of a text file -> that file (its path and its lines), as main read it.
    private HashMap<String, TextFile> textFileMap;

    // Creates an empty store.
    public TextFileRepository() {
        textFileMap = new HashMap<>();
    }

    // Create: keeps the lines main read as a text file under its path; reading the same
    // path again replaces the old copy.
    public void addTextFile(FileRequestDTO requestDTO) {
        TextFile textFile = new TextFile(requestDTO.getPath(), requestDTO.getLineList());

        // the path is the key: the same path again replaces the old copy
        textFileMap.put(textFile.getPath(), textFile);
    }

    // Read: the text file kept under this path, or null when none was read there.
    public TextFile getTextFile(String path) {
        return textFileMap.get(path);
    }

    // Update (option 4): adds the content as a new line at the end of the file on the disk;
    // the writing itself is FileUtils' job ("Cannot write to file" comes from there).
    public void appendContent(String path, String content) throws Exception {
        FileUtils.appendLine(path, content);
    }
}
