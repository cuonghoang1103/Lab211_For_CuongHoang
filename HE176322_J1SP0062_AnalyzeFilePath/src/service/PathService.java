package service;

import dto.PathRequestDTO;
import dto.PathResponseDTO;
import model.FilePath;
import repository.PathRepository;

/**
 * SERVICE: the business job of the program - analyse one path and collect the five
 * answers for the view. Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class PathService {

    // Keeps the path the service works on (Service -> Repository -> Model).
    private PathRepository pathRepository;

    // Creates the service with an empty repository.
    public PathService() {
        pathRepository = new PathRepository();
    }

    // Keeps the path of the request in the repository, then copies the five answers of
    // the FilePath model into a response DTO.
    public PathResponseDTO analyzePath(PathRequestDTO requestDTO) {
        PathResponseDTO responseDTO = new PathResponseDTO();
        FilePath filePath = null;

        // keep the path in the repository, then work on the model it holds
        pathRepository.saveFilePath(requestDTO.getFullPath());
        filePath = pathRepository.getFilePath();

        // the five answers, each one from the brief's own method of the model
        responseDTO.setDisk(filePath.getDisk());
        responseDTO.setExtension(filePath.getExtension());
        responseDTO.setFileName(filePath.getFileName());
        responseDTO.setPath(filePath.getPath());
        responseDTO.setFolderArray(filePath.getFolders());
        return responseDTO;
    }
}
