package controller;

import constants.Message;
import dto.ExpenseRequestDTO;
import dto.ExpenseResponseDTO;
import java.util.List;
import service.ExpenseService;
import view.ExpenseView;

public class ExpenseController {

    // Service xu ly nghiep vu
    private ExpenseService expenseService;

    // View in ket qua
    private ExpenseView expenseView;

    // Constructor: tao service va view
    public ExpenseController() {
        expenseService = new ExpenseService();
        expenseView = new ExpenseView();
    }

    // Khoi dong: nap cac dong doc tu file
    public void loadExpenses(List<String> lineList) {
        expenseService.loadExpenses(lineList);
    }

    // Function 1: them chi tieu
    public void addExpense(ExpenseRequestDTO expenseRequestDTO) throws Exception {
        expenseService.addExpense(expenseRequestDTO);
    }

    // Function 2: hien thi tat ca chi tieu + tong tien
    public void displayAll() throws Exception {
        List<ExpenseResponseDTO> expenseResponseList = expenseService.getExpenseList();

        // Danh sach rong thi bao loi
        if (expenseResponseList.isEmpty()) {
            throw new Exception(Message.NO_EXPENSE);
        }

        // Dua du lieu vao view roi in
        expenseView.setExpenseList(expenseResponseList);
        expenseView.setTotal(expenseService.getTotal());
        expenseView.display();
    }

    // Function 3: xoa chi tieu theo id
    public void deleteExpense(int expenseId) throws Exception {
        // Khong co id do thi bao xoa that bai
        if (!expenseService.deleteExpense(expenseId)) {
            throw new Exception(Message.DELETE_FAIL);
        }
    }
}
