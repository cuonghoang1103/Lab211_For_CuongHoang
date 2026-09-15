package service;

import constants.Constants;
import constants.Message;
import dto.ExpenseRequestDTO;
import dto.ExpenseResponseDTO;
import java.io.IOException;
import java.util.ArrayList;
import model.Expense;
import repository.ExpenseRepository;
import utils.DateUtils;

/**
 * SERVICE: the business rules of the expense book - the automatic ID ("ID = ID Max + 1",
 * first ID 1) and the total - around the CRUD of the repository.
 *
 * @author HE176322
 */
public class ExpenseService {

    // Where the expenses and the data file are kept.
    private ExpenseRepository expenseRepository;

    // Creates the service with its repository.
    public ExpenseService() {
        expenseRepository = new ExpenseRepository();
    }

    // Start-up: reads the data file.
    public void loadExpenses() throws Exception {
        // an unreadable file is reported, the program still starts empty
        try {
            expenseRepository.loadExpenses();
        } catch (IOException e) {
            // turn the technical error into the screen's message
            throw new Exception(String.format(Message.LOAD_FAIL, e.getMessage()));
        }
    }

    // The brief's addExpense: gives the new expense the next ID and stores it.
    public boolean addExpense(ExpenseRequestDTO requestDTO) throws Exception {
        Expense expense = new Expense(nextId(), requestDTO.getDate(),
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
    // then removes it.
    public boolean deleteExpense(ExpenseRequestDTO requestDTO) throws Exception {
        Expense exp = expenseRepository.findById(requestDTO.getId());
        // the brief: a nonexistent ID -> "Delete an expense fail"
        if (exp == null) {
            throw new Exception(Message.DELETE_FAIL);
        }
        // removing also saves the file, which can fail
        try {
            return expenseRepository.deleteExpense(exp);
        } catch (IOException e) {
            // the file could not be written; the book is unchanged
            throw new Exception(Message.DELETE_FAIL);
        }
    }

    // The rows of the brief's displayAll, in the order they were added.
    public ArrayList<ExpenseResponseDTO> getAllExpenses() {
        ArrayList<ExpenseResponseDTO> result = new ArrayList<>();
        // copy every expense into the shape the view displays
        for (Expense expense : expenseRepository.findAll()) {
            result.add(toResponse(expense));
        }
        return result;
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
    private int nextId() {
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

    // Copies one expense into the DTO the view is allowed to see.
    private ExpenseResponseDTO toResponse(Expense expense) {
        ExpenseResponseDTO response = new ExpenseResponseDTO();
        response.setId(expense.getId());
        response.setDate(DateUtils.formatDate(expense.getDate()));
        response.setAmount(expense.getAmount());
        response.setContent(expense.getContent());
        return response;
    }
}
