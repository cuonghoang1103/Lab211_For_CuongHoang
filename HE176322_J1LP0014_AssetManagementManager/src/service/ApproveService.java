package service;

import constants.Constants;
import constants.Message;
import dto.TransactionRequestDTO;
import dto.TransactionResponseDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Asset;
import model.Borrow;
import model.Person;
import model.Request;
import model.Transaction;
import repository.AssetRepository;
import repository.BorrowRepository;
import repository.EmployeeRepository;
import repository.RequestRepository;

/**
 * SERVICE: Functions 5 and 6 - approve a request (three files change together) and list
 * the borrows.
 *
 * @author HE176322
 */
public class ApproveService {

    // asset.dat: stock is checked and lowered.
    private AssetRepository assetRepository;
    // employee.dat: names shown in the tables.
    private EmployeeRepository employeeRepository;
    // request.dat: the approved request is removed.
    private RequestRepository requestRepository;
    // borrow.dat: the approved request becomes a borrow.
    private BorrowRepository borrowRepository;
    // The brief's date format: 23-12-2021 13:17:56.
    private DateTimeFormatter dateTimeFormat;

    // Creates the service on the four files (constructor injection).
    public ApproveService(AssetRepository assetRepository,
            EmployeeRepository employeeRepository, RequestRepository requestRepository,
            BorrowRepository borrowRepository) {
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
        this.requestRepository = requestRepository;
        this.borrowRepository = borrowRepository;
        this.dateTimeFormat = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);
    }

    // Reads request.dat and borrow.dat.
    public void loadTransactions() throws Exception {
        requestRepository.load();
        borrowRepository.load();
    }

    // Function 5, first step: every waiting request.
    public ArrayList<TransactionResponseDTO> getRequests() throws Exception {
        // nothing to approve
        if (requestRepository.isEmpty()) {
            throw new Exception(Message.NO_REQUEST);
        }
        return toResponseList(requestRepository.findAll());
    }

    // Function 5: checks the stock, then borrow.dat, asset.dat, request.dat; returns the
    // id of the new borrow.
    public String approveRequest(TransactionRequestDTO requestDTO) throws Exception {
        Request request = requestRepository.findById(requestDTO.getId());
        // no request has this id
        if (request == null) {
            throw new Exception(String.format(Message.REQUEST_NOT_EXIST,
                    requestDTO.getId().toUpperCase()));
        }
        Asset asset = assetRepository.findById(request.getAssetID());
        // the asset was removed from asset.dat
        if (asset == null) {
            throw new Exception(String.format(Message.REQUEST_ASSET_MISSING,
                    request.getAssetID()));
        }
        // the brief: is the borrowed quantity still in stock?
        if (asset.getQuantity() < request.getQuantity()) {
            throw new Exception(String.format(Message.NOT_ENOUGH, asset.getAssetID(),
                    asset.getQuantity(), request.getId(), request.getQuantity()));
        }
        Borrow borrow = new Borrow(borrowRepository.nextId(Constants.BORROW_PREFIX),
                request.getAssetID(), request.getEmployeeID(), request.getQuantity(),
                LocalDateTime.now().format(dateTimeFormat));
        borrowRepository.add(borrow);
        asset.setQuantity(asset.getQuantity() - request.getQuantity());
        assetRepository.update(asset);
        requestRepository.remove(request);
        return borrow.getId();
    }

    // Function 6: every borrow.
    public ArrayList<TransactionResponseDTO> getBorrows() throws Exception {
        // nothing is borrowed
        if (borrowRepository.isEmpty()) {
            throw new Exception(Message.NO_BORROW);
        }
        return toResponseList(borrowRepository.findAll());
    }

    // Copies requests or borrows into rows (both are Transactions: one method for both).
    private ArrayList<TransactionResponseDTO> toResponseList(
            ArrayList<? extends Transaction> transactions) {
        ArrayList<TransactionResponseDTO> rows = new ArrayList<>();
        // one row per request or borrow, in file order
        for (Transaction transaction : transactions) {
            rows.add(toResponse(transaction));
        }
        return rows;
    }

    // Copies one row and looks up the asset name and the employee name.
    private TransactionResponseDTO toResponse(Transaction transaction) {
        TransactionResponseDTO row = new TransactionResponseDTO();
        row.setId(transaction.getId());
        row.setAssetID(transaction.getAssetID());
        row.setEmployeeID(transaction.getEmployeeID());
        row.setQuantity(transaction.getQuantity());
        row.setDateTime(transaction.getDateTime());
        Asset asset = assetRepository.findById(transaction.getAssetID());
        row.setAssetName(asset == null ? Message.UNKNOWN : asset.getName());
        Person person = employeeRepository.findById(transaction.getEmployeeID());
        row.setEmployeeName(person == null ? Message.UNKNOWN : person.getName());
        return row;
    }
}
