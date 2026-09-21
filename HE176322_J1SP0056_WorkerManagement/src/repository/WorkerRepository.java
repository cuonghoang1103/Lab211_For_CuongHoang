package repository;

import dto.WorkerRequestDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.SalaryHistory;
import model.Worker;

/**
 * REPOSITORY: holds the workers and the salary log ("DB" in the brief) and performs simple
 * CRUD on them. No rule of the brief, no print.
 *
 * @author HE176322
 */
public class WorkerRepository {

    // The workers: code -> worker, in the order they were added.
    private LinkedHashMap<String, Worker> workerMap;

    // The salary log, in the order the adjustments were made (append only).
    private ArrayList<SalaryHistory> historyList;

    // Creates an empty repository.
    public WorkerRepository() {
        workerMap = new LinkedHashMap<>();
        historyList = new ArrayList<>();
    }

    // Tells whether a worker with this code is stored.
    public boolean isExistWorker(String code) {
        return workerMap.containsKey(code);
    }

    // Create: turns the request (already checked by the service) into a Worker - the model
    // - and stores it under its code.
    public boolean addWorker(WorkerRequestDTO requestDTO) {
        Worker worker = new Worker(requestDTO.getCode(), requestDTO.getName(),
                requestDTO.getAge(), requestDTO.getSalary(), requestDTO.getWorkLocation());

        // keep it under its code; true = one more worker is stored
        workerMap.put(worker.getCode(), worker);
        return true;
    }

    // Read: the worker stored under a code, or null when there is none.
    public Worker findWorker(String code) {
        return workerMap.get(code);
    }

    // Create: appends one line to the salary log.
    public boolean addHistory(SalaryHistory history) {
        return historyList.add(history);
    }

    // Read: a COPY of the salary log, so a caller that sorts it cannot reorder the log
    // itself.
    public ArrayList<SalaryHistory> getHistoryList() {
        return new ArrayList<>(historyList);
    }
}
