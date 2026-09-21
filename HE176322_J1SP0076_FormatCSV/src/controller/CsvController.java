package controller;

import constants.Message;
import dto.CsvRequestDTO;
import dto.CsvResponseDTO;
import service.CsvService;
import view.CsvView;

/**
 * CONTROLLER (Facade): receives the request from main, lets the service do the work, and
 * hands the result to the view ONCE per flow. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class CsvController {

    // Keeps dataCSV and does the import, the formatting and the export.
    private CsvService csvService;

    // Prints the result of every option.
    private CsvView csvView;

    // Creates the controller together with its service and its view.
    public CsvController() {
        csvService = new CsvService();
        csvView = new CsvView();
    }

    // Option 1 (importCSV): the service keeps the lines main read, then the view says so.
    public void importCSV(CsvRequestDTO requestDTO) {
        csvService.importCSV(requestDTO);
        showResult(Message.IMPORT_DONE);
    }

    // Option 2 (formatAddress): formats every Address of dataCSV, then the view says so.
    public void formatAddress() throws Exception {
        csvService.formatAddress(csvService.getDataCSV());
        showResult(Message.FORMAT_DONE);
    }

    // Option 3 (formatName): formats every Name of dataCSV, then the view says so.
    public void formatName() throws Exception {
        csvService.formatName(csvService.getDataCSV());
        showResult(Message.FORMAT_DONE);
    }

    // Option 4 (exportCSV): writes dataCSV into the file main asked for, then the view
    // says so.
    public void exportCSV(CsvRequestDTO requestDTO) throws Exception {
        csvService.exportCSV(requestDTO.getPath());
        showResult(Message.EXPORT_DONE);
    }

    // Hands the success line to the view and renders it - the only render of the flow.
    private void showResult(String message) {
        CsvResponseDTO responseDTO = new CsvResponseDTO();

        // the view receives the line through its attribute, then prints it
        responseDTO.setMessage(message);
        csvView.setResponseDTO(responseDTO);
        csvView.display();
    }
}
