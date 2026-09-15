package controller;

import constants.Message;
import dto.AssetRequestDTO;
import dto.AssetResponseDTO;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import dto.TransactionRequestDTO;
import repository.AssetRepository;
import repository.BorrowRepository;
import repository.EmployeeRepository;
import repository.RequestRepository;
import service.ApproveService;
import service.AssetService;
import service.AuthService;
import view.AssetView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks a service to do the
 * work, and hands the result to the view. Functions 3-6 check the manager first.
 *
 * @author HE176322
 */
public class ManagerController {

    // Login and the manager guard.
    private AuthService authService;
    // Search, create, update.
    private AssetService assetService;
    // Approve and list borrows.
    private ApproveService approveService;
    // Prints every result.
    private AssetView assetView;

    // Wires the program: ONE store per file, shared by every service that needs it.
    public ManagerController() {
        AssetRepository assetRepository = new AssetRepository();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        authService = new AuthService(employeeRepository);
        assetService = new AssetService(assetRepository);
        approveService = new ApproveService(assetRepository, employeeRepository,
                new RequestRepository(), new BorrowRepository());
        assetView = new AssetView();
    }

    // Reads the four files once, at start-up.
    public void loadData() throws Exception {
        authService.loadEmployees();
        assetService.loadAssets();
        approveService.loadTransactions();
    }

    // Function 1: "Successfully" and a welcome line, or "Incorrect id or password".
    public void login(LoginRequestDTO requestDTO) throws Exception {
        LoginResponseDTO user = authService.login(requestDTO);
        assetView.showMessage(Message.LOGIN_SUCCESS);
        assetView.showMessage(String.format(Message.WELCOME, user.getName(), user.getTitle()));
    }

    // The guard of Functions 3-6, asked before any question is typed.
    public void checkManager() throws Exception {
        authService.checkManager();
    }

    // Function 2 (no login needed): the matching assets, name descending.
    public void searchAsset(AssetRequestDTO requestDTO) throws Exception {
        assetView.displayAssets(assetService.searchByName(requestDTO));
    }

    // Function 3, first step: refuses an id already used.
    public void checkNewAssetId(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        assetService.checkNewId(requestDTO);
    }

    // Function 3: creates the asset and shows it.
    public void createAsset(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        AssetResponseDTO created = assetService.createAsset(requestDTO);
        assetView.showMessage(String.format(Message.CREATE_SUCCESS, created.getAssetID()));
        assetView.displayAsset(created);
    }

    // Function 4, first step: shows the asset, or "Asset does not exist".
    public void findAsset(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        assetView.displayAsset(assetService.findAsset(requestDTO));
    }

    // Function 4: updates the asset and prints the result.
    public void updateAsset(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        AssetResponseDTO updated = assetService.updateAsset(requestDTO);
        assetView.showMessage(String.format(Message.UPDATE_SUCCESS, updated.getAssetID()));
        assetView.displayAsset(updated);
    }

    // Function 5, first step: the waiting requests.
    public void showRequests() throws Exception {
        authService.checkManager();
        assetView.displayRequests(approveService.getRequests());
    }

    // Function 5: approves the request.
    public void approveRequest(TransactionRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        String borrowID = approveService.approveRequest(requestDTO);
        assetView.showMessage(String.format(Message.APPROVE_SUCCESS,
                requestDTO.getId().toUpperCase(), borrowID));
    }

    // Function 6: the borrowed assets.
    public void showBorrows() throws Exception {
        authService.checkManager();
        assetView.displayBorrows(approveService.getBorrows());
    }
}
