package service;

import constants.Constants;
import constants.Message;
import dto.AssetRequestDTO;
import dto.TransactionDTO;
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
public class ApprovalService {

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
    public ApprovalService(AssetRepository assetRepository,
            EmployeeRepository employeeRepository, RequestRepository requestRepository,
            BorrowRepository borrowRepository) {
        this.assetRepository = assetRepository;
        this.employeeRepository = employeeRepository;
        this.requestRepository = requestRepository;
        this.borrowRepository = borrowRepository;
        this.dateTimeFormat = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);
    }

    // Start-up: the lines main read from request.dat and borrow.dat become rows.
    public void loadData(AssetRequestDTO requestDTO) {
        requestRepository.loadData(requestDTO.getRequestLineList());
        borrowRepository.loadData(requestDTO.getBorrowLineList());
    }

    // Function 5, before the id is typed: every waiting request.
    public ArrayList<TransactionDTO> getRequests() throws Exception {
        // nothing to approve
        if (requestRepository.isEmpty()) {
            throw new Exception(Message.NO_REQUEST);
        }

        return toTransactionDTOList(requestRepository.findAll());
    }

    // Function 5 (the brief's "approve"): checks the stock, then writes borrow.dat,
    // asset.dat and request.dat, in that order.
    public void acceptRequest(AssetRequestDTO requestDTO) throws Exception {
        Request request = requestRepository.findById(requestDTO.getRequestId());
        Asset asset = null;
        Borrow borrow = null;

        // no request has this id
        if (request == null) {
            throw new Exception(String.format(Message.REQUEST_NOT_EXIST,
                    requestDTO.getRequestId().toUpperCase()));
        }

        asset = assetRepository.findById(request.getAssetId());

        // the asset was removed from asset.dat
        if (asset == null) {
            throw new Exception(String.format(Message.REQUEST_ASSET_MISSING,
                    request.getAssetId()));
        }

        // the brief: is the borrowed quantity still in stock? If not enough, an error
        if (asset.getQuantity() < request.getQuantity()) {
            throw new Exception(String.format(Message.NOT_ENOUGH, asset.getAssetId(),
                    asset.getQuantity(), request.getId(), request.getQuantity()));
        }

        // enough: insert into borrow.dat, update asset.dat, remove from request.dat
        borrow = new Borrow(borrowRepository.getNextId(Constants.BORROW_PREFIX),
                request.getAssetId(), request.getEmployeeId(), request.getQuantity(),
                LocalDateTime.now().format(dateTimeFormat));
        borrowRepository.add(borrow);
        asset.setQuantity(asset.getQuantity() - request.getQuantity());
        assetRepository.update(asset);
        requestRepository.remove(request);
    }

    // Function 6: every borrow.
    public ArrayList<TransactionDTO> getBorrows() throws Exception {
        // nothing is borrowed
        if (borrowRepository.isEmpty()) {
            throw new Exception(Message.NO_BORROW);
        }

        return toTransactionDTOList(borrowRepository.findAll());
    }

    // Copies requests or borrows into rows (both are Transactions: one method for both).
    private ArrayList<TransactionDTO> toTransactionDTOList(
            ArrayList<? extends Transaction> transactionList) {
        ArrayList<TransactionDTO> transactionDTOList = new ArrayList<>();

        // one row per request or borrow, in file order
        for (Transaction transaction : transactionList) {
            transactionDTOList.add(toTransactionDTO(transaction));
        }

        return transactionDTOList;
    }

    // Copies one row and looks up the asset name and the employee name.
    private TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        Asset asset = assetRepository.findById(transaction.getAssetId());
        Person person = employeeRepository.findById(transaction.getEmployeeId());

        // every column of the table; an id in no file shows "(unknown)"
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAssetId(transaction.getAssetId());
        transactionDTO.setEmployeeId(transaction.getEmployeeId());
        transactionDTO.setQuantity(transaction.getQuantity());
        transactionDTO.setDateTime(transaction.getDateTime());
        transactionDTO.setAssetName((asset == null) ? Message.UNKNOWN : asset.getName());
        transactionDTO.setEmployeeName((person == null) ? Message.UNKNOWN : person.getName());
        return transactionDTO;
    }
}
