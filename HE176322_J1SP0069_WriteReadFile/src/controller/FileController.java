package controller;

import constants.Message;
import dto.FileRequestDTO;
import dto.FileResponseDTO;
import repository.TextFileRepository;
import view.FileView;

/**
 * CONTROLLER (Facade): the one door main uses. No Scanner, no print, no model: it only
 * routes the request to the repository and the result to the view.
 *
 * @author HE176322
 */
public class FileController {

    // Keeps the file and saves it (Controller -> Repository -> Model; no calculation, so
    // no service).
    private TextFileRepository textFileRepository;

    // Prints what was read.
    private FileView fileView;

    // Creates the controller with its repository and view.
    public FileController() {
        textFileRepository = new TextFileRepository();
        fileView = new FileView();
    }

    // Function 1 workflow: writes the typed content to the typed path.
    public void writeFile(FileRequestDTO requestDTO) throws Exception {
        // writeFile reports failure as false; main shows it as a message
        if (!textFileRepository.saveFile(requestDTO)) {
            throw new Exception(Message.CANNOT_WRITE);
        }
    }

    // Function 2 workflow: keeps the file main read and lets the view show it ONCE.
    public void displayFile(FileRequestDTO requestDTO) {
        FileResponseDTO responseDTO = new FileResponseDTO();

        // the repository keeps the file; the content shown is the one it holds
        textFileRepository.addFile(requestDTO);
        responseDTO.setContent(textFileRepository.getContent());

        // hand the content to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }
}
