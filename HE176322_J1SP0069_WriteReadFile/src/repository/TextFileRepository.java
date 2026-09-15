package repository;

import dto.FileRequestDTO;
import dto.FileResponseDTO;
import model.TextFile;
import utils.FileUtils;

/**
 * REPOSITORY: the only class that knows the data lives in a file on the disk.
 *
 * @author HE176322
 */
public class TextFileRepository {

    // Creates the repository.
    public TextFileRepository() {
    }

    // Saves the typed content into the typed path.
    public boolean saveFile(FileRequestDTO requestDTO) {
        TextFile textFile = new TextFile(requestDTO.getPath(), requestDTO.getContent());
        return FileUtils.writeFile(textFile.getPath(), textFile.getContent());
    }

    // Loads the file at the requested path.
    public FileResponseDTO loadFile(FileRequestDTO requestDTO) throws Exception {
        String content = FileUtils.readFile(requestDTO.getPath());
        TextFile textFile = new TextFile(requestDTO.getPath(), content);
        return new FileResponseDTO(textFile.getContent());
    }
}
