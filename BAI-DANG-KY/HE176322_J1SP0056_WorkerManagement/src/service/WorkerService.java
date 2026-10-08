package service;

import constants.Message;
import constants.SalaryStatus;
import dto.WorkerRequestDTO;
import dto.SalaryHistoryResponseDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.SalaryHistory;
import model.Worker;
import repository.WorkerRepository;
import java.util.Objects;

public class WorkerService {

    // Khai bao workerRepository 
    private WorkerRepository workerRepository = new WorkerRepository();

    //Constructor
    public WorkerService() {
    }

    // Chuyen lenh luu nhan vien xuong map
    public void addWorker(WorkerRequestDTO dto) {
        this.workerRepository.save(dto);
    }

    // Goi ham update chung voi isIncrease = true
    public void increaseSalary(String workerId, double amount) throws Exception {
        Worker worker = this.workerRepository.findById(workerId);

        // Bao loi neu khong tim thay ID
        if (Objects.isNull(worker)) {
            throw new Exception(Message.WORKER_NULL);
        } else {
            double currentSalary = worker.getCurrentSalary();

            // Cap nhat luong cua nhan vien
            worker.setCurrentSalary(currentSalary + amount);

            // Xac dinh trang thai 
            SalaryStatus status = SalaryStatus.UP;

            // Khoi tao lich su giao dich va cat vao ho so nhan vien
            worker.addHistory(new SalaryHistory(currentSalary + amount, status, LocalDate.now()));
        }

    }

    // Goi ham update chung voi isIncrease = false
    public void decreaseSalary(String workerId, double amount) throws Exception {
        Worker worker = this.workerRepository.findById(workerId);

        // Bao loi neu khong tim thay ID
        if (Objects.isNull(worker)) {
            throw new Exception(Message.WORKER_NULL);
        } else {
            double currentSalary = worker.getCurrentSalary();

            // Bao loi neu so tien giam vuot qua luong hien tai
            if (amount > currentSalary) {
                throw new Exception(Message.INSUFFICIENT_SALARY);
            }

            // Cap nhat luong cua nhan vien
            worker.setCurrentSalary(currentSalary - amount);

            // Xac dinh trang thai 
            SalaryStatus status = SalaryStatus.DOWN;

            // Khoi tao lich su giao dich va cat vao ho so nhan vien
            worker.addHistory(new SalaryHistory(currentSalary - amount, status, LocalDate.now()));
        }

    }

    // Kiem tra id trung lap
    public boolean isDuplicateCode(String code) {
        return this.workerRepository.exists(code);
    }

    // Kiem tra kho co rong khong
    public boolean isEmpty() {
        return this.workerRepository.isEmpty();
    }

    public List<SalaryHistoryResponseDTO> getSalaryHistory() {
        List<SalaryHistoryResponseDTO> result = new ArrayList<>();

        List<Worker> workers = this.workerRepository.findAll();
        // Duyet tung Worker trong map
        for (Worker w : workers) {
            // duyet tung lich su giao dich
            for (SalaryHistory h : w.getHistory()) {
                result.add(new SalaryHistoryResponseDTO(
                        w.getId(), w.getName(), w.getAge(), h.getSalaryAfter(), h.getStatus().name(), h.getDate()
                ));
            }
        }

        return result;
    }

    
}
