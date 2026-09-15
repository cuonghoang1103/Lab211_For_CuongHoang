package controller;

import constants.Message;
import dto.FileRequestDTO;
import dto.FileResponseDTO;
import repository.TextFileRepository;
import view.FileView;

/**
 * CONTROLLER (Facade): the one door main uses.
 *
 * @author HE176322
 */
public class FileController {

    // Saves and loads the file.
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

    // Function 2 workflow: reads the file and lets the view show it.
    public void readFile(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO response = textFileRepository.loadFile(requestDTO);
        fileView.setResponse(response);
        fileView.display();
    }
}
