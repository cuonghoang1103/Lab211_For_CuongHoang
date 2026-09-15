package controller;

import constants.Message;
import dto.SalaryRequestDTO;
import dto.WorkerRequestDTO;
import service.WorkerService;
import view.WorkerView;

/**
 * CONTROLLER (and FACADE): receives a request DTO from main, asks the service to do the
 * work, and hands the result to the view.
 *
 * @author HE176322
 */
public class WorkerController {

    // The business rules (the brief's "Management").
    private WorkerService workerService;
    // Where the results are printed.
    private WorkerView workerView;

    // Creates the controller together with its service and view.
    public WorkerController() {
        workerService = new WorkerService();
        workerView = new WorkerView();
    }

    // Option 1: adds a worker, then reports it.
    public void addWorker(WorkerRequestDTO requestDTO) throws Exception {
        workerService.addWorker(requestDTO);
        workerView.showMessage(String.format(Message.ADD_SUCCESS,
                requestDTO.getCode()));
    }

    // Options 2 and 3: raises or cuts a salary, then reports it.
    public void changeSalary(SalaryRequestDTO requestDTO) throws Exception {
        workerService.changeSalary(requestDTO);
        workerView.showMessage(Message.CHANGE_SUCCESS);
    }

    // Option 4: lets the view print the salary log sorted by code.
    public void getInfomationSalary() {
        workerView.setHistoryList(workerService.getInfomationSalary());
        workerView.display();
    }
}
