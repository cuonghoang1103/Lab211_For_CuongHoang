package controller;

import constants.Message;
import dto.ExpenseRequestDTO;
import service.ExpenseService;
import view.ExpenseView;

/**
 * CONTROLLER (and FACADE): receives a request DTO from main, asks the service to do the
 * work, and hands the result to the view.
 *
 * @author HE176322
 */
public class ExpenseController {

    // The business rules (automatic ID, total) and the storage behind them.
    private ExpenseService expenseService;
    // Where the results are printed.
    private ExpenseView expenseView;

    // Creates the controller together with its service and view.
    public ExpenseController() {
        expenseService = new ExpenseService();
        expenseView = new ExpenseView();
    }

    // Start-up: loads the expenses saved by the previous run.
    public void loadExpenses() throws Exception {
        expenseService.loadExpenses();
    }

    // Option 1 (the brief's addExpense): adds an expense and reports it.
    public void addExpense(ExpenseRequestDTO requestDTO) throws Exception {
        expenseService.addExpense(requestDTO);
        expenseView.showMessage(Message.ADD_SUCCESS);
    }

    // Option 2 (the brief's displayAll): shows every expense and the total.
    public void displayAll() {
        expenseView.setExpenseList(expenseService.getAllExpenses());
        expenseView.setTotal(expenseService.getTotal());
        expenseView.displayAll();
    }

    // Option 3 (the brief's deleteExpense): deletes by ID and reports it.
    public void deleteExpense(ExpenseRequestDTO requestDTO) throws Exception {
        expenseService.deleteExpense(requestDTO);
        expenseView.showMessage(Message.DELETE_SUCCESS);
    }
}
