package service;

import constants.Constants;
import constants.Message;
import constants.SalaryStatus;
import dto.WorkerRequestDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import model.SalaryHistory;
import model.Worker;
import repository.WorkerRepository;

/**
 * SERVICE: the brief's "Class Management" - the rules about workers and salaries
 * (addWorker, changeSalary, getInfomationSalary). Called only by the controller; no print,
 * no keyboard.
 *
 * @author HE176322
 */
public class WorkerService {

    // Where the workers and the salary log are stored (Service -> Repository -> Model).
    private WorkerRepository workerRepository;

    // Which strategy adjusts the salary for each status.
    private HashMap<SalaryStatus, ISalaryStrategy> strategyMap;

    // Creates the service with its repository and registers one strategy per status.
    public WorkerService() {
        workerRepository = new WorkerRepository();
        strategyMap = new HashMap<>();
        strategyMap.put(SalaryStatus.UP, new IncreaseSalaryStrategy());
        strategyMap.put(SalaryStatus.DOWN, new DecreaseSalaryStrategy());
    }

    // The brief's addWorker: checks the new worker in the brief's order, then stores it.
    public boolean addWorker(WorkerRequestDTO requestDTO) throws Exception {
        String code = requestDTO.getCode();

        // the brief: Code(id) cannot be null (an empty line counts as null)
        if ((code == null) || code.isEmpty()) {
            throw new Exception(Message.CODE_NULL);
        }

        // the brief: the code cannot be duplicated with a code in the DB
        if (workerRepository.isExistWorker(code)) {
            throw new Exception(String.format(Message.CODE_DUPLICATE, code));
        }

        // the brief: age must be in range 18 to 50
        if ((requestDTO.getAge() < Constants.MIN_AGE) ||
                (requestDTO.getAge() > Constants.MAX_AGE)) {
            throw new Exception(String.format(Message.AGE_RANGE, Constants.MIN_AGE,
                    Constants.MAX_AGE));
        }

        // the brief: salary must be greater than 0
        if (requestDTO.getSalary() <= 0) {
            throw new Exception(Message.SALARY_POSITIVE);
        }

        // every rule passed: the repository turns the request into a Worker and stores it
        return workerRepository.addWorker(requestDTO);
    }

    // The brief's changeSalary (options 2 and 3 in ONE method: they differ only by the
    // strategy).
    public boolean changeSalary(WorkerRequestDTO requestDTO) throws Exception {
        Worker worker = workerRepository.findWorker(requestDTO.getCode());
        ISalaryStrategy strategy = strategyMap.get(requestDTO.getStatus());
        SalaryHistory history = null;
        double newSalary = 0;

        // the brief: Code(id) must exist in DB
        if (worker == null) {
            throw new Exception(String.format(Message.CODE_NOT_EXIST, requestDTO.getCode()));
        }

        // the brief: amount of money must be > 0
        if (requestDTO.getAmount() <= 0) {
            throw new Exception(Message.AMOUNT_POSITIVE);
        }

        // the strategy of the status adds or subtracts the amount
        newSalary = strategy.calculateSalary(worker.getSalary(), requestDTO.getAmount());

        // a salary must stay greater than 0 (same rule as when adding)
        if (newSalary <= 0) {
            throw new Exception(Message.SALARY_POSITIVE);
        }

        // keep the new salary, then log the change with the date of today
        worker.setSalary(newSalary);
        history = new SalaryHistory(worker, newSalary, requestDTO.getStatus(), LocalDate.now());
        return workerRepository.addHistory(history);
    }

    // The brief's getInfomationSalary (the brief's spelling is kept): every adjustment,
    // sorted by worker code, as the table rows the view prints.
    public ArrayList<String> getInfomationSalary() {
        ArrayList<SalaryHistory> historyList = workerRepository.getHistoryList();
        ArrayList<String> rowList = new ArrayList<>();

        // sort by worker code; the sort is stable, so the lines of one worker keep their
        // time order
        historyList.sort(Comparator.comparing(history -> history.getWorker().getCode()));

        // one table row per adjustment, text built by the model's toString()
        for (SalaryHistory history : historyList) {
            rowList.add(history.toString());
        }

        return rowList;
    }
}
