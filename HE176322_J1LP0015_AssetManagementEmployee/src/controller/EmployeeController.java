package controller;

import constants.Message;
import dto.AssetRequestDTO;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import dto.TransactionRequestDTO;
import repository.AssetRepository;
import repository.BorrowRepository;
import repository.EmployeeRepository;
import repository.RequestRepository;
import service.AssetService;
import service.AuthService;
import service.BorrowService;
import view.AssetView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks a service to do the
 * work, and hands the result to the view. Functions 3-5 check the employee first and
 * fill his id from the session.
 *
 * @author HE176322
 */
public class EmployeeController {

    // Login and the employee guard.
    private AuthService authService;
    // Search and the asset list.
    private AssetService assetService;
    // Send, cancel, return.
    private BorrowService borrowService;
    // Prints every result.
    private AssetView assetView;

    // Wires the program: ONE store per file, shared by every service that needs it.
    public EmployeeController() {
        AssetRepository assetRepository = new AssetRepository();
        authService = new AuthService(new EmployeeRepository());
        assetService = new AssetService(assetRepository);
        borrowService = new BorrowService(assetRepository, new RequestRepository(),
                new BorrowRepository());
        assetView = new AssetView();
    }

    // Reads the four files once, at start-up.
    public void loadData() throws Exception {
        authService.loadEmployees();
        assetService.loadAssets();
        borrowService.loadTransactions();
    }

    // Function 1: "Successfully" and a welcome line, or "Incorrect id or password".
    public void login(LoginRequestDTO requestDTO) throws Exception {
        LoginResponseDTO user = authService.login(requestDTO);
        assetView.showMessage(Message.LOGIN_SUCCESS);
        assetView.showMessage(String.format(Message.WELCOME, user.getName(), user.getTitle()));
    }

    // The guard of Functions 3-5, asked before any question is typed.
    public void checkEmployee() throws Exception {
        authService.checkEmployee();
    }

    // Function 2 (no login needed): the matching assets, name descending.
    public void searchAsset(AssetRequestDTO requestDTO) throws Exception {
        assetView.displayAssets(assetService.searchByName(requestDTO));
    }

    // Function 3, first step: every asset of the company.
    public void showAllAssets() throws Exception {
        authService.checkEmployee();
        assetView.displayAssets(assetService.getAllAssets());
    }

    // Function 3: sends the request in the name of the employee logged in.
    public void sendRequest(TransactionRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        String requestID = borrowService.sendRequest(requestDTO);
        assetView.showMessage(String.format(Message.REQUEST_SENT, requestID));
    }

    // Function 4, first step: his requests.
    public void showMyRequests() throws Exception {
        authService.checkEmployee();
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        assetView.displayRequests(borrowService.getMyRequests(requestDTO));
    }

    // Function 4, second step: refuses an id that is not one of his requests.
    public void checkMyRequest(TransactionRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        borrowService.checkMyRequest(requestDTO);
    }

    // Function 4: cancels the request.
    public void cancelRequest(TransactionRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        borrowService.cancelRequest(requestDTO);
        assetView.showMessage(String.format(Message.REQUEST_CANCELLED,
                requestDTO.getId().toUpperCase()));
    }

    // Function 5, first step: his borrows.
    public void showMyBorrows() throws Exception {
        authService.checkEmployee();
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        assetView.displayBorrows(borrowService.getMyBorrows(requestDTO));
    }

    // Function 5, second step: refuses an id that is not one of his borrows.
    public void checkMyBorrow(TransactionRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        borrowService.checkMyBorrow(requestDTO);
    }

    // Function 5: returns the borrow.
    public void returnBorrow(TransactionRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeID(authService.getCurrentEmployeeID());
        borrowService.returnBorrow(requestDTO);
        assetView.showMessage(String.format(Message.BORROW_RETURNED,
                requestDTO.getId().toUpperCase()));
    }
}
