package service;

import constants.Constants;
import dto.ExpenseRequestDTO;
import dto.ExpenseResponseDTO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.Expense;
import repository.ExpenseRepository;

public class ExpenseService {

    // Kho chua chi tieu
    private ExpenseRepository expenseRepository;

    // Constructor: tao repository
    public ExpenseService() {
        expenseRepository = new ExpenseRepository();
    }

    // Chuyen cac dong cua file xuong repository
    public void loadExpenses(List<String> lineList) {
        expenseRepository.loadExpenses(lineList);
    }

    // Them chi tieu moi voi ID = ID lon nhat + 1
    public void addExpense(ExpenseRequestDTO expenseRequestDTO) throws Exception {
        Expense expense = new Expense(generateNextId(), expenseRequestDTO.getDate(),
                expenseRequestDTO.getAmount(), expenseRequestDTO.getContent());
        expenseRepository.addExpense(expense);
    }

    // Xoa chi tieu theo id; false = khong co id do
    public boolean deleteExpense(int expenseId) throws Exception {
        Expense expense = expenseRepository.findById(expenseId);

        // Khong tim thay id
        if (expense == null) {
            return false;
        }
        expenseRepository.deleteExpense(expense);
        return true;
    }

    // Doi danh sach Expense sang ResponseDTO de view in
    public List<ExpenseResponseDTO> getExpenseList() {
        List<ExpenseResponseDTO> expenseResponseList = new ArrayList<>();

        // Chuyen tung chi tieu sang 1 dong cua bang
        for (Expense expense : expenseRepository.findAll()) {
            expenseResponseList.add(new ExpenseResponseDTO(expense.getId(), expense.getDate(),
                    formatMoney(expense.getAmount()), expense.getContent()));
        }
        return expenseResponseList;
    }

    // Tinh tong tien tat ca chi tieu (da dinh dang)
    public String getTotal() {
        double total = 0;

        // Cong don so tien
        for (Expense expense : expenseRepository.findAll()) {
            total += expense.getAmount();
        }
        return formatMoney(total);
    }

    // Tim ID lon nhat roi cong 1 (danh sach rong thi ID dau la 1)
    private int generateNextId() {
        int maxId = 0;

        // Duyet tim ID lon nhat
        for (Expense expense : expenseRepository.findAll()) {
            // Gap ID lon hon thi cap nhat
            if (expense.getId() > maxId) {
                maxId = expense.getId();
            }
        }
        return maxId + 1;
    }

    // Dinh dang tien: 100 / 100.1, luon dau cham (Locale.US) ke ca may tieng Viet
    private String formatMoney(double amount) {
        DecimalFormat moneyFormat = new DecimalFormat(Constants.MONEY_FORMAT,
                new DecimalFormatSymbols(Locale.US));
        return moneyFormat.format(amount);
    }
}
