package repository;

import constants.Constants;
import java.io.IOException;
import java.util.ArrayList;
import model.Expense;
import utils.DateUtils;
import utils.FileUtils;

/**
 * REPOSITORY: holds the expenses and keeps the data file in step with them.
 *
 * @author HE176322
 */
public class ExpenseRepository {

    // The expense book, in the order the expenses were added.
    private ArrayList<Expense> expenseList = new ArrayList<>();

    // Creates an empty repository; loadExpenses() fills it from the file.
    public ExpenseRepository() {
    }

    // Reads the data file into the list.
    public void loadExpenses() throws IOException {
        expenseList.clear();
        // one expense per line of the file
        for (String line : FileUtils.readLines(Constants.FILE_NAME)) {
            Expense expense = toExpense(line);
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

    // Finds the expense with an ID.
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
    // and saves the file; restores it when saving fails.
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

    // Rewrites the data file with the whole book.
    private void saveExpenses() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        // one line per expense
        for (Expense expense : expenseList) {
            lines.add(toLine(expense));
        }
        FileUtils.writeLines(Constants.FILE_NAME, lines);
    }

    // Writes one expense as "id|dd-MMM-yyyy|amount|content".
    private String toLine(Expense expense) {
        return expense.getId() + Constants.FILE_SEPARATOR
                + DateUtils.formatDate(expense.getDate()) + Constants.FILE_SEPARATOR
                + expense.getAmount() + Constants.FILE_SEPARATOR
                + expense.getContent();
    }

    // Reads one line of the file back into an expense.
    private Expense toExpense(String line) {
        String[] parts = line.split(Constants.FILE_SEPARATOR_REGEX, Constants.FILE_FIELDS);
        // too few fields: not an expense line
        if (parts.length < Constants.FILE_FIELDS) {
            return null;
        }
        // a field that does not convert makes the whole line damaged
        try {
            return new Expense(Integer.parseInt(parts[Constants.FIELD_ID].trim()),
                    DateUtils.parseDate(parts[Constants.FIELD_DATE].trim()),
                    Double.parseDouble(parts[Constants.FIELD_AMOUNT].trim()),
                    parts[Constants.FIELD_CONTENT]);
        } catch (Exception e) {
            // bad number or bad date: skip this line
            return null;
        }
    }
}
