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
 * the service, and hands the result to the view ONCE per flow. No Scanner, no print, no
 * model.
 *
 * @author HE176322
 */
public class FileController {

    // Does the file work (Controller -> Service -> Repository -> Model).
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
        FileResponseDTO responseDTO = new FileResponseDTO();

        // the brief: checkInputPath answers by throwing, in all three cases
        try {
            fileService.checkInputPath(requestDTO.getPath());
        } catch (Exception e) {
            // the answer is a result to SHOW, so it goes to the view
            responseDTO.setMessage(e.getMessage());
        }

        // hand the answer to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }

    // Option 2: lists the .java files of a directory, then "Result n file!".
    public void getJavaFiles(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = new FileResponseDTO();
        ArrayList<String> fileNameList = new ArrayList<>();

        // the names, then the brief's summary line with their number
        fileNameList.addAll(fileService.getAllFileNameJavaInDirectory(requestDTO.getPath()));
        responseDTO.setFileNameList(fileNameList);
        responseDTO.setMessage(String.format(Message.RESULT_JAVA_FILES, fileNameList.size()));

        // hand the result to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }

    // Option 3: lists the files bigger than n KB, then "Result n files!".
    public void getBigFiles(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = new FileResponseDTO();
        File[] fileArray = fileService.getFileWithSizeGreaterThanInput(requestDTO.getPath(),
                requestDTO.getSize());
        ArrayList<String> fileNameList = fileService.getFileNameList(fileArray);

        // the names, then the brief's summary line with their number
        responseDTO.setFileNameList(fileNameList);
        responseDTO.setMessage(String.format(Message.RESULT_BIG_FILES, fileNameList.size()));

        // hand the result to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }

    // Option 4: adds the content to the file, then "Write done".
    public void appendContent(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = new FileResponseDTO();

        // "Recording status": true = recorded; a failure has already been thrown
        if (fileService.appendContentToFile(requestDTO.getPath(), requestDTO.getContent())) {
            responseDTO.setMessage(Message.WRITE_DONE);
        }

        // hand the result to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }

    // Option 5: keeps the file main read, then shows its number of words.
    public void countWords(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = new FileResponseDTO();

        // the service keeps the lines main read, then counts the words of that file
        fileService.addTextFile(requestDTO);
        responseDTO.setMessage(String.format(Message.TOTAL,
                fileService.countCharacter(requestDTO.getPath())));

        // hand the result to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }
}
