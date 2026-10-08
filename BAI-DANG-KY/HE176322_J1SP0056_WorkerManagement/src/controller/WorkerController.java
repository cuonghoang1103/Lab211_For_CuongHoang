package controller;

import constants.Message;
import dto.WorkerRequestDTO;
import dto.SalaryHistoryResponseDTO;
import service.WorkerService;
import view.WorkerView;
import java.util.List;

public class WorkerController {
    
    //Khoi tao service va new
    private WorkerService workerService = new WorkerService();
    private WorkerView workerView = new WorkerView();
    
    //Constuctor
    public WorkerController() {}
    
    // Function 1: Them nhan vien
    public void addWorker(WorkerRequestDTO dto) throws Exception {
        // Kiem tra trung ma ID
        if(this.workerService.isDuplicateCode(dto.getId())) {
            throw new Exception(Message.DUPLICATE);
        }
        
        // Khi thoa man 2 dieu kien tren thi dua vao Service
        else {
            this.workerService.addWorker(dto);
        }
    }
    
    // Function 2: Tang luong
    public void increaseSalary(String workerId, double amount) throws Exception {
        // Neu chua co cai nao thi bao loi
        if(this.workerService.isEmpty()) {
            throw new Exception(Message.EMPTY_DATABASE);
        }
        else {
            this.workerService.increaseSalary(workerId, amount);
        }
    }
    
    // Function 3: Giam luong
    public void decreaseSalary(String workerId, double amount) throws Exception {
        // Neu chua co cai nao thi bao loi
        if(this.workerService.isEmpty()) {
            throw new Exception(Message.EMPTY_DATABASE);
        }
        else {
            this.workerService.decreaseSalary(workerId, amount);
        }
    }
    
    // Function 4: Xuat ra lich su
    public void displaySalaryHistory() {
        // Tao danh sach moi dua tren du lieu tu service
        List<SalaryHistoryResponseDTO> historyList = this.workerService.getSalaryHistory();
        
        // Đẩy data vào View và gọi hàm in ra màn hình
        this.workerView.setData(historyList);
        this.workerView.display();
    }
}