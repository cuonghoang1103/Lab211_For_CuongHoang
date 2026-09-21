package controller;

import constants.Message;
import dto.AssetRequestDTO;
import dto.AssetResponseDTO;
import dto.PersonDTO;
import repository.AssetRepository;
import repository.BorrowRepository;
import repository.EmployeeRepository;
import repository.RequestRepository;
import service.ApprovalService;
import service.AssetService;
import service.AuthService;
import view.AssetView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks a service to do the
 * work, and hands the answer to the view - one render per function at most. No Scanner,
 * no print, no model. Functions 3-6 check the manager first.
 *
 * @author HE176322
 */
public class ManagerController {

    // Login and the manager guard.
    private AuthService authService;

    // Search, create, update.
    private AssetService assetService;

    // Approve and list borrows.
    private ApprovalService approvalService;

    // Prints every result.
    private AssetView assetView;

    // Wires the program: ONE store per file, shared by every service that needs it.
    public ManagerController() {
        AssetRepository assetRepository = new AssetRepository();
        EmployeeRepository employeeRepository = new EmployeeRepository();

        // AssetService and ApprovalService see the same stock
        authService = new AuthService(employeeRepository);
        assetService = new AssetService(assetRepository);
        approvalService = new ApprovalService(assetRepository, employeeRepository,
                new RequestRepository(), new BorrowRepository());
        assetView = new AssetView();
    }

    // Start-up: hands the lines main read from the four files to the services, whose
    // repositories turn them into objects. Nothing is shown.
    public void loadData(AssetRequestDTO requestDTO) {
        authService.loadData(requestDTO);
        assetService.loadData(requestDTO);
        approvalService.loadData(requestDTO);
    }

    // Function 1: "Successfully" and a welcome line - once; a wrong id or password is
    // thrown ("Incorrect id or password").
    public void login(AssetRequestDTO requestDTO) throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();
        PersonDTO personDTO = authService.login(requestDTO);

        // logged in: hand the answer to the view, then render it - once for the flow
        responseDTO.setMessage(String.format(Message.LOGIN_SUCCESS, personDTO.getName(),
                personDTO.getTitle()));
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 2 (no login needed): the matching assets, name descending - once.
    public void searchAsset(AssetRequestDTO requestDTO) throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // the service throws "No asset found." when nothing matched
        responseDTO.setAssetList(assetService.searchByName(requestDTO));
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Functions 3 and 4, before any question: logged in, and a manager. A check-only call:
    // it throws, it shows nothing.
    public void checkManager() throws Exception {
        authService.checkManager();
    }

    // Function 3, while the id is typed: refuses an id already used (check-only).
    public void checkNewAssetId(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        assetService.checkNewId(requestDTO);
    }

    // Function 3: creates the asset, then shows it - once.
    public void createAsset(AssetRequestDTO requestDTO) throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only the manager; the service throws when the id is taken
        authService.checkManager();
        responseDTO.setAssetList(assetService.createAsset(requestDTO));
        responseDTO.setMessage(String.format(Message.CREATE_SUCCESS,
                requestDTO.getAssetId().toUpperCase()));

        // hand the answer to the view, then render it - once for the flow
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 4, right after the id is typed: "Asset does not exist" (check-only).
    public void checkAssetExist(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        assetService.checkAssetExist(requestDTO);
    }

    // Function 4: updates the asset, then prints the result of the updating - once.
    public void updateAsset(AssetRequestDTO requestDTO) throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only the manager; the service throws "Asset does not exist"
        authService.checkManager();
        responseDTO.setAssetList(assetService.updateAsset(requestDTO));
        responseDTO.setMessage(String.format(Message.UPDATE_SUCCESS,
                requestDTO.getAssetId().toUpperCase()));

        // hand the answer to the view, then render it - once for the flow
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 5, before the id is typed: the waiting requests (the brief shows them
    // before the choice) - the one render of this function.
    public void showRequests() throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only the manager; the service throws when there is no request
        authService.checkManager();
        responseDTO.setRequestList(approvalService.getRequests());
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 5 (the brief's "approve"): the chosen request becomes a borrow. Nothing is
    // shown - the brief goes back to the main screen; a problem is thrown.
    public void acceptRequest(AssetRequestDTO requestDTO) throws Exception {
        authService.checkManager();
        approvalService.acceptRequest(requestDTO);
    }

    // Function 6: the borrowed assets - once.
    public void showBorrows() throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only the manager; the service throws when nothing is borrowed
        authService.checkManager();
        responseDTO.setBorrowList(approvalService.getBorrows());
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }
}
