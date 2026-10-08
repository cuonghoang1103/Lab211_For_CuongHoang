package view;

import dto.ExpenseResponseDTO;
import java.util.List;

public class ExpenseView {

    // Danh sach dong cua bang, controller dua vao
    private List<ExpenseResponseDTO> expenseList;

    // Tong tien da dinh dang
    private String total;

    // Constructor
    public ExpenseView() {
    }

    // Nhan danh sach tu controller
    public void setExpenseList(List<ExpenseResponseDTO> expenseList) {
        this.expenseList = expenseList;
    }

    // Nhan tong tien tu controller
    public void setTotal(String total) {
        this.total = total;
    }

    // In bang theo format cua de: tieu de cot, tung dong, dong Total
    public void display() {
        System.out.printf("%-4s %-13s %-12s %s\n", "ID", "Date", "Amount", "Content");

        // Duyet du lieu roi in tung dong
        for (ExpenseResponseDTO expense : expenseList) {
            System.out.println(expense);
        }
        System.out.println("Total: " + total);
    }
}
