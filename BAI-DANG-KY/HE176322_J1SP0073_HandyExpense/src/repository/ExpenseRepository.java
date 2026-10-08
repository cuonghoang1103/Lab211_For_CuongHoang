package repository;

import constants.Constants;
import constants.Message;
import java.util.ArrayList;
import java.util.List;
import model.Expense;
import utils.FileUtils;

public class ExpenseRepository {

    // Danh sach chi tieu luu trong bo nho
    private List<Expense> expenseList;

    // Constructor: tao danh sach rong
    public ExpenseRepository() {
        expenseList = new ArrayList<>();
    }

    // Tach cac dong doc tu file thanh Expense
    public void loadExpenses(List<String> lineList) {
        // Moi dong la 1 chi tieu: id|date|amount|content
        for (String line : lineList) {
            String[] partArray = line.split(Constants.FILE_SEPARATOR_REGEX,
                    Constants.FILE_FIELDS);

            // Dong thieu cot thi bo qua
            if (partArray.length < Constants.FILE_FIELDS) {
                continue;
            }

            // Dong hong (id/amount khong phai so) thi bo qua
            try {
                expenseList.add(new Expense(Integer.parseInt(partArray[Constants.FIELD_ID].trim()),
                        partArray[Constants.FIELD_DATE].trim(),
                        Double.parseDouble(partArray[Constants.FIELD_AMOUNT].trim()),
                        partArray[Constants.FIELD_CONTENT]));
            } catch (NumberFormatException e) {
                // Bo qua dong hong, khong dung chuong trinh
                continue;
            }
        }
    }

    // Tra ve ban sao danh sach de ben ngoai khong sua nham
    public List<Expense> findAll() {
        return new ArrayList<>(expenseList);
    }

    // Tim chi tieu theo id, khong co thi tra ve null
    public Expense findById(int expenseId) {
        // Duyet tung chi tieu
        for (Expense expense : expenseList) {
            // Trung id thi tra ve
            if (expense.getId() == expenseId) {
                return expense;
            }
        }
        return null;
    }

    // Them chi tieu roi ghi lai file
    public void addExpense(Expense expense) throws Exception {
        expenseList.add(expense);
        saveExpenses();
    }

    // Xoa chi tieu roi ghi lai file
    public void deleteExpense(Expense expense) throws Exception {
        expenseList.remove(expense);
        saveExpenses();
    }

    // Ghi toan bo danh sach xuong file, moi chi tieu 1 dong
    private void saveExpenses() throws Exception {
        List<String> lineList = new ArrayList<>();

        // Ghep tung chi tieu thanh 1 dong id|date|amount|content
        for (Expense expense : expenseList) {
            lineList.add(String.join(Constants.FILE_SEPARATOR, String.valueOf(expense.getId()),
                    expense.getDate(), String.valueOf(expense.getAmount()), expense.getContent()));
        }

        // Ghi file, loi thi bao cho nguoi dung
        try {
            FileUtils.writeLines(Constants.FILE_NAME, lineList);
        } catch (Exception e) {
            // Ghi file that bai: bao loi ro rang
            throw new Exception(Message.WRITE_FAIL);
        }
    }
}
