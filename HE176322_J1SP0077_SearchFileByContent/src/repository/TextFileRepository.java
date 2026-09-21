package repository;

import dto.WordRequestDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.TextFile;

/**
 * REPOSITORY: holds the data of the program - the text files main read, a single file
 * under its path (option 1) and the files of a folder under the folder's path (option 2) -
 * and only simple CRUD on them. No count, no print, no reading of files.
 *
 * @author HE176322
 */
public class TextFileRepository {

    // Path of a text file -> that file, as main read it (option 1).
    private HashMap<String, TextFile> textFileMap;

    // Path of a folder -> the text files directly inside it, as main read them (option 2).
    private HashMap<String, ArrayList<TextFile>> folderMap;

    // Creates an empty store.
    public TextFileRepository() {
        textFileMap = new HashMap<>();
        folderMap = new HashMap<>();
    }

    // Create (option 1): keeps the lines main read as a text file under its path; reading
    // the same path again replaces the old copy.
    public void addTextFile(WordRequestDTO requestDTO) {
        TextFile textFile = new TextFile(requestDTO.getPath(), requestDTO.getLineList());

        // the path is the key: the same path again replaces the old copy
        textFileMap.put(requestDTO.getPath(), textFile);
    }

    // Read: the text file kept under this path, or null when none was read there.
    public TextFile getTextFile(String path) {
        return textFileMap.get(path);
    }

    // Create (option 2): keeps every file main read from the folder - its name and its
    // lines - under the folder's path; reading the same folder again replaces the old list.
    public void addFolder(WordRequestDTO requestDTO) {
        ArrayList<TextFile> textFileList = new ArrayList<>();

        // one entry of the map = one file of the folder
        for (Map.Entry<String, ArrayList<String>> entry : requestDTO.getFileLineMap().entrySet()) {
            textFileList.add(new TextFile(entry.getKey(), entry.getValue()));
        }

        // the folder's path is the key: the same folder again replaces the old list
        folderMap.put(requestDTO.getPath(), textFileList);
    }

    // Read: the files kept for the folder at this path, or null when none was read there.
    public ArrayList<TextFile> getTextFileList(String path) {
        return folderMap.get(path);
    }
}
