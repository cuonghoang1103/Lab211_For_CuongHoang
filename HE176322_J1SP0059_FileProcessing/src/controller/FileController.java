package controller;

import constants.Message;
import dto.FileRequestDTO;
import dto.ReportResponseDTO;
import service.FileService;
import service.SalaryComparator;
import view.FileView;

/**
 * CONTROLLER: receives a request DTO from main, asks the service to do the work, and
 * hands the result to the view.
 *
 * @author HE176322
 */
public class FileController {

    // Does the file work; configured with the salary order.
    private FileService fileService;
    // Prints the results.
    private FileView fileView;

    // Creates the controller: the service gets SalaryComparator as its sorting strategy.
    public FileController() {
        fileService = new FileService(new SalaryComparator());
        fileView = new FileView();
    }

    // Option 1: finds the persons earning at least the money entered and lets the view
    // print the table, Max and Min.
    public void findPerson(FileRequestDTO requestDTO) throws Exception {
        ReportResponseDTO report = fileService.findPerson(requestDTO);
        fileView.setReport(report);
        fileView.display();
    }

    // Option 2: copies every single word of the source into the new file.
    public void copyText(FileRequestDTO requestDTO) throws Exception {
        // the brief's copy status: true means the new file is written
        if (fileService.copyText(requestDTO)) {
            fileView.showMessage(Message.COPY_DONE);
        }
    }
}
