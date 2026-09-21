package controller;

import dto.FileRequestDTO;
import dto.FileResponseDTO;
import service.FileService;
import service.SalaryComparator;
import view.FileView;

/**
 * CONTROLLER (Facade): receives a request DTO from main, asks the service to do the work,
 * and hands the answer to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class FileController {

    // Does the work of both functions; configured with the salary order.
    private FileService fileService;

    // Prints the answer of each function.
    private FileView fileView;

    // Creates the controller: the service gets SalaryComparator as its sorting strategy.
    public FileController() {
        fileService = new FileService(new SalaryComparator());
        fileView = new FileView();
    }

    // Option 1: finds the persons earning at least the money entered; the view shows the
    // table, Max and Min.
    public void findPerson(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = fileService.findPerson(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }

    // Option 2: copies every single word of the source into the new file; the view shows
    // "Copy done...".
    public void copyText(FileRequestDTO requestDTO) throws Exception {
        FileResponseDTO responseDTO = fileService.copyText(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        fileView.setResponseDTO(responseDTO);
        fileView.display();
    }
}
