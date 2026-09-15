package repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.SalaryHistory;
import model.Worker;

/**
 * REPOSITORY: holds the workers and the salary log ("DB" in the brief) and performs
 * simple CRUD on them.
 *
 * @author HE176322
 */
public class WorkerRepository {

    // The workers: code -> worker.
    private LinkedHashMap<String, Worker> workerMap = new LinkedHashMap<>();
    // The salary log, in the order the adjustments were made (append only).
    private ArrayList<SalaryHistory> historyList = new ArrayList<>();

    // Creates an empty repository.
    public WorkerRepository() {
    }

    // Tells whether a worker with this code is stored.
    public boolean isExistWorker(String code) {
        return workerMap.containsKey(code);
    }

    // Stores a new worker (already checked by the service).
    public boolean addWorker(Worker worker) {
        workerMap.put(worker.getCode(), worker);
        return true;
    }

    // Finds the worker stored under a code.
    public Worker findWorker(String code) {
        return workerMap.get(code);
    }

    // Appends one line to the salary log.
    public boolean addHistory(SalaryHistory history) {
        return historyList.add(history);
    }

    // Returns a COPY of the salary log, so a caller that sorts it cannot reorder the log
    // itself.
    public ArrayList<SalaryHistory> getHistories() {
        return new ArrayList<>(historyList);
    }
}
