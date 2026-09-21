package repository;

import dto.FileRequestDTO;
import model.TextFile;
import utils.FileUtils;

/**
 * REPOSITORY: holds the data of the program - the text file it works on (the model) - and
 * only simple CRUD on it: keep the file, give its content back, save it to the disk
 * (writing itself is FileUtils' job). No print, no reading of files.
 *
 * @author HE176322
 */
public class TextFileRepository {

    // The file the program works on: the one just written, or the one main read.
    private TextFile textFile;

    // Creates the repository with an empty file description.
    public TextFileRepository() {
        textFile = new TextFile();
    }

    // Create + save (Function 1): keeps the typed file, then writes its content into the
    // typed path with the brief's writeFile; false when it could not be written.
    public boolean saveFile(FileRequestDTO requestDTO) {
        textFile = new TextFile(requestDTO.getPath(), requestDTO.getContent());
        return FileUtils.writeFile(textFile.getPath(), textFile.getContent());
    }

    // Create (Function 2): keeps the file main read - its path and its content.
    public void addFile(FileRequestDTO requestDTO) {
        textFile = new TextFile(requestDTO.getPath(), requestDTO.getContent());
    }

    // Read: returns the content of the file kept by the last save or add.
    public String getContent() {
        return textFile.getContent();
    }
}
