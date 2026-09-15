package service;

import constants.Constants;
import constants.Message;
import constants.SalaryStatus;
import dto.SalaryHistoryResponseDTO;
import dto.SalaryRequestDTO;
import dto.WorkerRequestDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import model.SalaryHistory;
import model.Worker;
import repository.WorkerRepository;

/**
 * SERVICE: the brief's "Class Management" - the rules about workers and salaries
 * (addWorker, changeSalary, getInfomationSalary).
 *
 * @author HE176322
 */
public class WorkerService {

    // Where the workers and the salary log are stored.
    private WorkerRepository workerRepository;
    // Which strategy adjusts the salary for each status.
    private HashMap<SalaryStatus, SalaryStrategy> strategyMap = new HashMap<>();

    // Creates the service with its repository and registers one strategy per status.
    public WorkerService() {
        workerRepository = new WorkerRepository();
        strategyMap.put(SalaryStatus.UP, new IncreaseSalaryStrategy());
        strategyMap.put(SalaryStatus.DOWN, new DecreaseSalaryStrategy());
    }

    // The brief's addWorker: checks the new worker in the brief's order, then stores it.
    public boolean addWorker(WorkerRequestDTO requestDTO) throws Exception {
        String code = requestDTO.getCode();
        // the brief: Code(id) cannot be null (an empty line counts as null)
        if (code == null || code.isEmpty()) {
            throw new Exception(Message.CODE_NULL);
        }
        // the brief: the code cannot be duplicated with a code in the DB
        if (workerRepository.isExistWorker(code)) {
            throw new Exception(String.format(Message.CODE_DUPLICATE, code));
        }
        // the brief: age must be in range 18 to 50
        if (requestDTO.getAge() < Constants.MIN_AGE
                || requestDTO.getAge() > Constants.MAX_AGE) {
            throw new Exception(String.format(Message.AGE_RANGE,
                    Constants.MIN_AGE, Constants.MAX_AGE));
        }
        // the brief: salary must be greater than 0
        if (requestDTO.getSalary() <= 0) {
            throw new Exception(Message.SALARY_POSITIVE);
        }
        Worker worker = new Worker(code, requestDTO.getName(), requestDTO.getAge(),
                requestDTO.getSalary(), requestDTO.getWorkLocation());
        return workerRepository.addWorker(worker);
    }

    // The brief's changeSalary (options 2 and 3 in ONE method: they differ only by the
    // strategy).
    public boolean changeSalary(SalaryRequestDTO requestDTO) throws Exception {
        Worker worker = workerRepository.findWorker(requestDTO.getCode());
        // the brief: Code(id) must exist in DB
        if (worker == null) {
            throw new Exception(String.format(Message.CODE_NOT_EXIST,
                    requestDTO.getCode()));
        }
        // the brief: amount of money must be > 0
        if (requestDTO.getAmount() <= 0) {
            throw new Exception(Message.AMOUNT_POSITIVE);
        }
        SalaryStrategy strategy = strategyMap.get(requestDTO.getStatus());
        double newSalary = strategy.adjust(worker.getSalary(), requestDTO.getAmount());
        // a salary must stay greater than 0 (same rule as when adding)
        if (newSalary <= 0) {
            throw new Exception(Message.SALARY_POSITIVE);
        }
        worker.setSalary(newSalary);
        return workerRepository.addHistory(new SalaryHistory(worker, newSalary,
                requestDTO.getStatus(), LocalDate.now()));
    }

    // The brief's getInfomationSalary (the brief's spelling is kept): every adjustment,
    // sorted by worker code.
    public ArrayList<SalaryHistoryResponseDTO> getInfomationSalary() {
        ArrayList<SalaryHistory> histories = workerRepository.getHistories();
        histories.sort(Comparator.comparing(history -> history.getWorker().getCode()));
        ArrayList<SalaryHistoryResponseDTO> result = new ArrayList<>();
        // copy every sorted line into the shape the view displays
        for (SalaryHistory history : histories) {
            result.add(toResponse(history));
        }
        return result;
    }

    // Copies one log line into the DTO the view is allowed to see.
    private SalaryHistoryResponseDTO toResponse(SalaryHistory history) {
        SalaryHistoryResponseDTO response = new SalaryHistoryResponseDTO();
        Worker worker = history.getWorker();
        response.setCode(worker.getCode());
        response.setName(worker.getName());
        response.setAge(worker.getAge());
        response.setSalary(history.getSalary());
        response.setStatus(history.getStatus().name());
        response.setDate(history.getDate().format(
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)));
        return response;
    }
}
