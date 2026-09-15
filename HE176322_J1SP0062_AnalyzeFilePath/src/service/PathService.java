package service;

import dto.PathRequestDTO;
import dto.PathResponseDTO;
import model.FilePath;

/**
 * SERVICE: the business job of the program - analyse one path and collect the five
 * answers for the view.
 *
 * @author HE176322
 */
public class PathService {

    // Creates the service; it holds no data of its own.
    public PathService() {
    }

    // Builds the FilePath model from the request and copies its five answers into a
    // response DTO.
    public PathResponseDTO analyzePath(PathRequestDTO requestDTO) {
        FilePath filePath = new FilePath(requestDTO.getFullPath());
        PathResponseDTO response = new PathResponseDTO();
        response.setDisk(filePath.getDisk());
        response.setExtension(filePath.getExtension());
        response.setFileName(filePath.getFileName());
        response.setPath(filePath.getPath());
        response.setFolders(filePath.getFolders());
        return response;
    }
}
