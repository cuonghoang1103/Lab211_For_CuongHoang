package repository;

import constants.Constants;
import dto.ExpenseRequestDTO;
import java.io.IOException;
import java.util.ArrayList;
import model.Expense;
import utils.DateUtils;
import utils.FileUtils;

/**
 * REPOSITORY: holds the expenses and simple CRUD on them, and keeps the data file in step
 * (main reads the file at start-up; the writing goes through utils/FileUtils).
 *
 * @author HE176322
 */
public class ExpenseRepository {

    // The expense book, in the order the expenses were added.
    private ArrayList<Expense> expenseList;

    // Creates an empty repository; loadExpenses() fills it from the lines of the file.
    public ExpenseRepository() {
        expenseList = new ArrayList<>();
    }

    // Start-up: turns the lines main read from the data file into expenses.
    public void loadExpenses(ExpenseRequestDTO requestDTO) {
        Expense expense = null;

        // start from an empty book
        expenseList.clear();

        // one expense per line of the file
        for (String line : requestDTO.getLineList()) {
            expense = parseExpense(line);

            // a damaged line is skipped
            if (expense != null) {
                expenseList.add(expense);
            }
        }
    }

    // Returns a COPY of the book, so a caller cannot change it by accident.
    public ArrayList<Expense> findAll() {
        return new ArrayList<>(expenseList);
    }

    // Finds the expense with an ID; null when there is none.
    public Expense findById(int id) {
        // compare every stored expense with the ID
        for (Expense expense : expenseList) {
            // found it: stop looking
            if (expense.getId() == id) {
                return expense;
            }
        }

        return null;
    }

    // The brief's addExpense (storage part): adds the expense and saves the file.
    public boolean addExpense(Expense expense) throws IOException {
        // keep it after the others
        expenseList.add(expense);

        // save; undo the add when saving fails
        try {
            saveExpenses();
        } catch (IOException e) {
            // keep memory and file identical
            expenseList.remove(expense);
            throw e;
        }

        return true;
    }

    // The brief's deleteExpense(list, Expense exp) (storage part): removes the expense
    // and saves the file; puts it back when saving fails.
    public boolean deleteExpense(Expense exp) throws IOException {
        int index = expenseList.indexOf(exp);

        // not in the book: nothing to delete
        if (index < 0) {
            return false;
        }

        expenseList.remove(index);

        // save; put the expense back at its place when saving fails
        try {
            saveExpenses();
        } catch (IOException e) {
            // keep memory and file identical
            expenseList.add(index, exp);
            throw e;
        }

        return true;
    }

    // Rewrites the data file with the whole book (the writing itself is FileUtils').
    private void saveExpenses() throws IOException {
        ArrayList<String> lineList = new ArrayList<>();

        // one line per expense
        for (Expense expense : expenseList) {
            lineList.add(formatLine(expense));
        }

        FileUtils.writeLines(Constants.FILE_NAME, lineList);
    }

    // Writes one expense as "id|dd-MMM-yyyy|amount|content" (String.join, no string "+").
    private String formatLine(Expense expense) {
        return String.join(Constants.FILE_SEPARATOR, String.valueOf(expense.getId()),
                DateUtils.formatDate(expense.getDate()), String.valueOf(expense.getAmount()),
                expense.getContent());
    }

    // Reads one line of the file back into an expense; null when the line is damaged.
    private Expense parseExpense(String line) {
        String[] partArray = line.split(Constants.FILE_SEPARATOR_REGEX, Constants.FILE_FIELDS);

        // too few fields: not an expense line
        if (partArray.length < Constants.FILE_FIELDS) {
            return null;
        }

        // a field that does not convert makes the whole line damaged
        try {
            return new Expense(Integer.parseInt(partArray[Constants.FIELD_ID].trim()),
                    DateUtils.parseDate(partArray[Constants.FIELD_DATE].trim()),
                    Double.parseDouble(partArray[Constants.FIELD_AMOUNT].trim()),
                    partArray[Constants.FIELD_CONTENT]);
        } catch (Exception e) {
            // bad number or bad date: skip this line
            return null;
        }
    }
}
