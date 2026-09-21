package repository;

import dto.FileRequestDTO;
import java.util.ArrayList;
import java.util.HashMap;
import utils.FileUtils;

/**
 * REPOSITORY: holds the lines of every text file of "Copy text" - the sources main read
 * and the new files the program wrote - each under the path of its file, and only simple
 * CRUD on them. Saving a new file goes to the disk through FileUtils.
 *
 * @author HE176322
 */
public class TextRepository {

    // Path of a text file -> the lines of that file.
    private HashMap<String, ArrayList<String>> lineMap;

    // Creates an empty store.
    public TextRepository() {
        lineMap = new HashMap<>();
    }

    // Create: keeps the lines main read from the source, under the path of the source.
    public void loadData(FileRequestDTO requestDTO) {
        lineMap.put(requestDTO.getSource(), requestDTO.getLineList());
    }

    // Read: the lines of the file at this path (an empty list for a path never loaded).
    public ArrayList<String> getLineList(String path) {
        return lineMap.getOrDefault(path, new ArrayList<>());
    }

    // Create: writes the lines into the file at this path (FileUtils throws "Can't write
    // file"), then keeps them under that path.
    public void saveLineList(String path, ArrayList<String> lineList) throws Exception {
        FileUtils.writeLines(path, lineList);
        lineMap.put(path, lineList);
    }
}
