package controller;

import constants.Message;
import dto.ExpenseRequestDTO;
import dto.ExpenseResponseDTO;
import java.util.ArrayList;
import service.ExpenseService;
import view.ExpenseView;

/**
 * CONTROLLER (and FACADE): receives a request DTO from main, asks the service to do the
 * work, and hands the answer to the view - one render per menu option. No Scanner, no
 * print, no model.
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

    // Start-up: hands the lines main read from the data file to the service (nothing is
    // shown).
    public void loadExpenses(ExpenseRequestDTO requestDTO) {
        expenseService.loadExpenses(requestDTO);
    }

    // Option 1 (the brief's addExpense): stores a new expense, then the view prints
    // "Add an expense successful" - once.
    public void addExpense(ExpenseRequestDTO requestDTO) throws Exception {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();

        // the service answers true when the expense was stored (and the file saved)
        if (expenseService.addExpense(requestDTO)) {
            responseDTO.setMessage(Message.ADD_SUCCESS);
        }

        // hand the answer to the view, then render it - once for the whole flow
        expenseView.setResponseDTO(responseDTO);
        expenseView.display();
    }

    // Option 2 (the brief's displayAll): the service gives the rows and the total, the
    // view prints them - once.
    public void displayAll() {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        ArrayList<String> rowList = expenseService.getRowList();

        // an empty book: say so instead of a table with a total of 0
        if (rowList.isEmpty()) {
            responseDTO.setMessage(Message.NO_EXPENSE);
        } else {
            // at least one expense: one row each, then the total
            responseDTO.setRowList(rowList);
            responseDTO.setTotal(expenseService.getTotal());
        }

        // hand the answer to the view, then render it - once for the whole flow
        expenseView.setResponseDTO(responseDTO);
        expenseView.display();
    }

    // Option 3 (the brief's deleteExpense): deletes the expense with the typed ID, then
    // the view prints "Delete an expense successful" - once.
    public void deleteExpense(ExpenseRequestDTO requestDTO) throws Exception {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();

        // the brief: "If ID does not exist, display on the screen: Delete an expense fail"
        if (!expenseService.deleteExpense(requestDTO)) {
            throw new Exception(Message.DELETE_FAIL);
        }

        // deleted: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.DELETE_SUCCESS);
        expenseView.setResponseDTO(responseDTO);
        expenseView.display();
    }
}
