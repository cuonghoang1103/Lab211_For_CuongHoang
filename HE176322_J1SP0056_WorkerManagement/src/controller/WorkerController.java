package controller;

import constants.Message;
import dto.WorkerRequestDTO;
import dto.WorkerResponseDTO;
import java.util.ArrayList;
import service.WorkerService;
import view.WorkerView;

/**
 * CONTROLLER (and FACADE): receives a request DTO from main, asks the service to do the
 * work, and hands the answer to the view - one render per menu option. No Scanner, no
 * print, no model.
 *
 * @author HE176322
 */
public class WorkerController {

    // The business rules (the brief's "Management"): Controller -> Service -> Repository
    // -> Model.
    private WorkerService workerService;

    // Where the results are printed.
    private WorkerView workerView;

    // Creates the controller together with its service and view.
    public WorkerController() {
        workerService = new WorkerService();
        workerView = new WorkerView();
    }

    // Option 1 (the brief's addWorker): stores the new worker, then the view prints
    // "Worker [code] has been added." - once.
    public void addWorker(WorkerRequestDTO requestDTO) throws Exception {
        WorkerResponseDTO responseDTO = new WorkerResponseDTO();

        // the service throws when a rule of the brief is broken; true = the worker is stored
        if (workerService.addWorker(requestDTO)) {
            responseDTO.setMessage(String.format(Message.ADD_SUCCESS, requestDTO.getCode()));
        }

        // hand the answer to the view, then render it - once for the whole flow
        workerView.setResponseDTO(responseDTO);
        workerView.display();
    }

    // Options 2 and 3 (the brief's changeSalary): raises or cuts a salary, then the view
    // prints "Salary has been adjusted." - once.
    public void changeSalary(WorkerRequestDTO requestDTO) throws Exception {
        WorkerResponseDTO responseDTO = new WorkerResponseDTO();

        // the service throws when a rule of the brief is broken; true = adjusted and logged
        if (workerService.changeSalary(requestDTO)) {
            responseDTO.setMessage(Message.CHANGE_SUCCESS);
        }

        // hand the answer to the view, then render it - once for the whole flow
        workerView.setResponseDTO(responseDTO);
        workerView.display();
    }

    // Option 4 (the brief's getInfomationSalary): the service gives the rows sorted by code,
    // the view prints them - once.
    public void displaySalaryHistory() {
        WorkerResponseDTO responseDTO = new WorkerResponseDTO();
        ArrayList<String> rowList = workerService.getInfomationSalary();

        // no salary has been adjusted yet: say so instead of a bare header
        if (rowList.isEmpty()) {
            responseDTO.setMessage(Message.NO_HISTORY);
        } else {
            // at least one adjustment: the header and one line per adjustment
            responseDTO.setRowList(rowList);
        }

        // hand the answer to the view, then render it - once for the whole flow
        workerView.setResponseDTO(responseDTO);
        workerView.display();
    }
}
