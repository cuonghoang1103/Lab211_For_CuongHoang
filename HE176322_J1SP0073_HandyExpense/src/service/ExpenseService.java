package service;

import constants.Constants;
import constants.Message;
import dto.ExpenseRequestDTO;
import java.io.IOException;
import java.util.ArrayList;
import model.Expense;
import repository.ExpenseRepository;

/**
 * SERVICE: the business rules of the expense book - the automatic ID ("ID = ID Max + 1",
 * first ID 1) and the total - around the CRUD of the repository.
 *
 * @author HE176322
 */
public class ExpenseService {

    // Where the expenses are kept (and saved to the data file).
    private ExpenseRepository expenseRepository;

    // Creates the service with its repository.
    public ExpenseService() {
        expenseRepository = new ExpenseRepository();
    }

    // Start-up: hands the lines of the data file to the repository, which keeps the
    // expenses.
    public void loadExpenses(ExpenseRequestDTO requestDTO) {
        expenseRepository.loadExpenses(requestDTO);
    }

    // The brief's addExpense: gives the new expense the next ID and stores it; true =
    // stored (the brief's "Add expense status").
    public boolean addExpense(ExpenseRequestDTO requestDTO) throws Exception {
        Expense expense = new Expense(generateNextId(), requestDTO.getDate(),
                requestDTO.getAmount(), requestDTO.getContent());

        // storing also saves the file, which can fail
        try {
            return expenseRepository.addExpense(expense);
        } catch (IOException e) {
            // the file could not be written; the book is unchanged
            throw new Exception(String.format(Message.ADD_FAIL, e.getMessage()));
        }
    }

    // The brief's deleteExpense: turns the typed ID into the Expense the brief passes,
    // then removes it; false = no expense has that ID (the brief's "Delete the expense
    // status").
    public boolean deleteExpense(ExpenseRequestDTO requestDTO) throws Exception {
        Expense exp = expenseRepository.findById(requestDTO.getId());

        // a nonexistent ID: nothing to delete
        if (exp == null) {
            return false;
        }

        // removing also saves the file, which can fail
        try {
            return expenseRepository.deleteExpense(exp);
        } catch (IOException e) {
            // the file could not be written; the book is unchanged
            throw new Exception(Message.DELETE_FAIL);
        }
    }

    // The data half of the brief's displayAll: every expense as the table row its
    // toString() gives, in the order they were added.
    public ArrayList<String> getRowList() {
        ArrayList<String> rowList = new ArrayList<>();

        // one row per stored expense
        for (Expense expense : expenseRepository.findAll()) {
            rowList.add(expense.toString());
        }

        return rowList;
    }

    // The total of all amounts (the brief: "total all the inputted expense amount").
    public double getTotal() {
        double total = 0;

        // add up every amount
        for (Expense expense : expenseRepository.findAll()) {
            total += expense.getAmount();
        }

        return total;
    }

    // The brief: "ID = ID Max + 1", the first ID is 1.
    private int generateNextId() {
        int maxId = Constants.FIRST_ID - 1;

        // find the largest ID in use
        for (Expense expense : expenseRepository.findAll()) {
            // a bigger ID than any seen so far
            if (expense.getId() > maxId) {
                maxId = expense.getId();
            }
        }

        return maxId + 1;
    }
}
