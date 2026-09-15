package controller;

import constants.Message;
import dto.FileRequestDTO;
import dto.FileResponseDTO;
import java.io.File;
import java.util.ArrayList;
import service.FileService;
import view.FileView;

/**
 * CONTROLLER (and Facade): receives the request from main, calls the brief's method in
 * the service, and hands the result to the view.
 *
 * @author HE176322
 */
public class FileController {

    // Does the file work.
    private FileService fileService;
    // Prints the results.
    private FileView fileView;

    // Creates the controller with its service and view.
    public FileController() {
        fileService = new FileService();
        fileView = new FileView();
    }

    // Option 1: shows whether the path is a file, a directory or nothing.
    public void checkPath(FileRequestDTO requestDTO) {
        // the brief: checkInputPath answers by throwing, in all three cases
        try {
            fileService.checkInputPath(requestDTO.getPath());
        } catch (Exception e) {
            // the answer is a result to SHOW, so it goes to the view
            fileView.showMessage(e.getMessage());
        }
    }

    // Option 2: lists the .java files of a directory.
    public void getJavaFiles(FileRequestDTO requestDTO) throws Exception {
        ArrayList<String> names = new ArrayList<>(
                fileService.getAllFileNameJavaInDirectory(requestDTO.getPath()));
        fileView.setResponse(new FileResponseDTO(names));
        fileView.displayFileNames(Message.RESULT_JAVA_FILES);
    }

    // Option 3: lists the files bigger than n KB.
    public void getBigFiles(FileRequestDTO requestDTO) throws Exception {
        File[] files = fileService.getFileWithSizeGreaterThanInput(
                requestDTO.getPath(), requestDTO.getSize());
        fileView.setResponse(new FileResponseDTO(fileService.getFileNames(files)));
        fileView.displayFileNames(Message.RESULT_BIG_FILES);
    }

    // Option 4: adds the content to the file.
    public void appendContent(FileRequestDTO requestDTO) throws Exception {
        // true = recorded; a failure has already been thrown
        if (fileService.appendContentToFile(requestDTO.getPath(),
                requestDTO.getContent())) {
            fileView.showMessage(Message.WRITE_DONE);
        }
    }

    // Option 5: shows the number of words of a text file.
    public void countWords(FileRequestDTO requestDTO) throws Exception {
        int total = fileService.countCharacter(requestDTO.getPath());
        fileView.showMessage(String.format(Message.TOTAL, total));
    }
}
