package controller;

import constants.Message;
import dto.AssetRequestDTO;
import dto.AssetResponseDTO;
import dto.PersonDTO;
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
 * work, and hands the answer to the view - one render per function at most. No Scanner,
 * no print, no model. Functions 3-5 check the employee first and fill his id from the
 * session.
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

        // AssetService and BorrowService see the same stock
        authService = new AuthService(new EmployeeRepository());
        assetService = new AssetService(assetRepository);
        borrowService = new BorrowService(assetRepository, new RequestRepository(),
                new BorrowRepository());
        assetView = new AssetView();
    }

    // Start-up: hands the lines main read from the four files to the services, whose
    // repositories turn them into objects. Nothing is shown.
    public void loadData(AssetRequestDTO requestDTO) {
        authService.loadData(requestDTO);
        assetService.loadData(requestDTO);
        borrowService.loadData(requestDTO);
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

    // Function 3, before the asset is typed: every asset of the company (the brief shows
    // the list first) - the one render of this function.
    public void showAssets() throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only an employee logged in; the service throws when asset.dat is empty
        authService.checkEmployee();
        responseDTO.setAssetList(assetService.getAllAssets());
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 3: the request is written in the name of the employee logged in. Nothing
    // is shown (the brief asks to continue next); a problem is thrown.
    public void sendRequest(AssetRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeId(authService.getCurrentEmployeeId());
        borrowService.sendRequest(requestDTO);
    }

    // Function 4, before the id is typed: his requests (the brief shows them first) - the
    // one render of this function.
    public void showMyRequests() throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only an employee logged in; the service throws when he has no request
        authService.checkEmployee();
        responseDTO.setRequestList(borrowService.getMyRequests(
                authService.getCurrentEmployeeId()));
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 4, right after the id is typed: refuses an id that is not one of his
    // requests (check-only: it throws, it shows nothing).
    public void checkMyRequest(AssetRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeId(authService.getCurrentEmployeeId());
        borrowService.checkMyRequest(requestDTO);
    }

    // Function 4: cancels the confirmed request. Nothing is shown (the brief asks to
    // continue next); a problem is thrown.
    public void cancelRequest(AssetRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeId(authService.getCurrentEmployeeId());
        borrowService.cancelRequest(requestDTO);
    }

    // Function 5, before the id is typed: his borrows (the brief shows them first) - the
    // one render of this function.
    public void showMyBorrows() throws Exception {
        AssetResponseDTO responseDTO = new AssetResponseDTO();

        // only an employee logged in; the service throws when he holds nothing
        authService.checkEmployee();
        responseDTO.setBorrowList(borrowService.getMyBorrows(
                authService.getCurrentEmployeeId()));
        assetView.setResponseDTO(responseDTO);
        assetView.display();
    }

    // Function 5, right after the id is typed: refuses an id that is not one of his
    // borrows (check-only: it throws, it shows nothing).
    public void checkMyBorrow(AssetRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeId(authService.getCurrentEmployeeId());
        borrowService.checkMyBorrow(requestDTO);
    }

    // Function 5: returns the confirmed borrow. Nothing is shown (the brief asks to
    // continue next); a problem is thrown.
    public void returnBorrow(AssetRequestDTO requestDTO) throws Exception {
        authService.checkEmployee();
        requestDTO.setEmployeeId(authService.getCurrentEmployeeId());
        borrowService.returnBorrow(requestDTO);
    }
}
