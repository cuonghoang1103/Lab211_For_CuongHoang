package controller;

import constants.Message;
import dto.CsvRequestDTO;
import service.CsvService;
import view.CsvView;

/**
 * CONTROLLER (and Facade): receives the request from main, asks the service to do the
 * work, and lets the view show the result.
 *
 * @author HE176322
 */
public class CsvController {

    // Does the import, formatting and export.
    private CsvService csvService;
    // Prints the results.
    private CsvView csvView;

    // Creates the controller with its service and view.
    public CsvController() {
        csvService = new CsvService();
        csvView = new CsvView();
    }

    // Option 1: imports the file, then shows it.
    public void importCSV(CsvRequestDTO requestDTO) throws Exception {
        csvService.importCSV(requestDTO.getPath());
        showResult(Message.IMPORT_DONE);
    }

    // Option 2: formats every Address, then shows the data.
    public void formatAddress() throws Exception {
        csvService.formatAddress(csvService.getDataCSV());
        showResult(Message.FORMAT_DONE);
    }

    // Option 3: formats every Name, then shows the data.
    public void formatName() throws Exception {
        csvService.formatName(csvService.getDataCSV());
        showResult(Message.FORMAT_DONE);
    }

    // Option 4: writes the current data into a new file.
    public void exportCSV(CsvRequestDTO requestDTO) throws Exception {
        csvService.exportCSV(requestDTO.getPath());
        csvView.showMessage(Message.EXPORT_DONE);
    }

    // Shows a "Done" line and then the current data, so the user can SEE what the step
    // changed.
    private void showResult(String doneMessage) {
        csvView.showMessage(doneMessage);
        csvView.setResponse(csvService.getData());
        csvView.display();
    }
}
