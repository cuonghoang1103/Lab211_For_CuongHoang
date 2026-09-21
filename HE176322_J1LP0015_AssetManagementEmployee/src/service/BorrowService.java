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
import model.Request;
import model.Transaction;
import repository.AssetRepository;
import repository.BorrowRepository;
import repository.RequestRepository;

/**
 * SERVICE: Functions 3, 4 and 5 of the employee - send a request, cancel one of his
 * requests, return one of his borrows. An employee only ever sees his own rows.
 *
 * @author HE176322
 */
public class BorrowService {

    // asset.dat: checked on a request, increased on a return.
    private AssetRepository assetRepository;

    // request.dat: rows added and cancelled.
    private RequestRepository requestRepository;

    // borrow.dat: rows returned.
    private BorrowRepository borrowRepository;

    // The brief's date format: 23-12-2021 13:17:56.
    private DateTimeFormatter dateTimeFormat;

    // Creates the service on the three files (constructor injection).
    public BorrowService(AssetRepository assetRepository, RequestRepository requestRepository,
            BorrowRepository borrowRepository) {
        this.assetRepository = assetRepository;
        this.requestRepository = requestRepository;
        this.borrowRepository = borrowRepository;
        this.dateTimeFormat = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);
    }

    // Start-up: the lines main read from request.dat and borrow.dat become rows.
    public void loadData(AssetRequestDTO requestDTO) {
        requestRepository.loadData(requestDTO.getRequestLineList());
        borrowRepository.loadData(requestDTO.getBorrowLineList());
    }

    // Function 3: writes a new request into request.dat, in the name of the employee
    // logged in.
    public void sendRequest(AssetRequestDTO requestDTO) throws Exception {
        Asset asset = assetRepository.findById(requestDTO.getAssetId());
        Request request = null;

        // no asset has this id
        if (asset == null) {
            throw new Exception(Message.ASSET_NOT_EXIST);
        }

        // asking for more than the company has cannot be approved anyway
        if (requestDTO.getQuantity() > asset.getQuantity()) {
            throw new Exception(String.format(Message.NOT_ENOUGH, asset.getQuantity(),
                    asset.getName()));
        }

        // the brief: add the new data into request.dat
        request = new Request(requestRepository.getNextId(Constants.REQUEST_PREFIX),
                asset.getAssetId(), requestDTO.getEmployeeId(), requestDTO.getQuantity(),
                LocalDateTime.now().format(dateTimeFormat));
        requestRepository.add(request);
    }

    // Function 4, before the id is typed: the requests of the employee logged in.
    public ArrayList<TransactionDTO> getMyRequests(String employeeId) throws Exception {
        ArrayList<TransactionDTO> transactionDTOList = new ArrayList<>();

        // keep only this employee's rows
        for (Request request : requestRepository.findAll()) {
            // another employee's request is never shown
            if (request.getEmployeeId().equalsIgnoreCase(employeeId)) {
                transactionDTOList.add(toTransactionDTO(request));
            }
        }

        // nothing to cancel
        if (transactionDTOList.isEmpty()) {
            throw new Exception(Message.NO_REQUEST);
        }

        return transactionDTOList;
    }

    // Function 4, right after the id is typed: the id must be one of HIS requests (asked
    // before the confirmation).
    public void checkMyRequest(AssetRequestDTO requestDTO) throws Exception {
        findMyRequest(requestDTO);
    }

    // Function 4: deletes the request from request.dat.
    public void cancelRequest(AssetRequestDTO requestDTO) throws Exception {
        requestRepository.remove(findMyRequest(requestDTO));
    }

    // Function 5, before the id is typed: the borrows of the employee logged in.
    public ArrayList<TransactionDTO> getMyBorrows(String employeeId) throws Exception {
        ArrayList<TransactionDTO> transactionDTOList = new ArrayList<>();

        // keep only this employee's rows
        for (Borrow borrow : borrowRepository.findAll()) {
            // another employee's borrow is never shown
            if (borrow.getEmployeeId().equalsIgnoreCase(employeeId)) {
                transactionDTOList.add(toTransactionDTO(borrow));
            }
        }

        // nothing to return
        if (transactionDTOList.isEmpty()) {
            throw new Exception(Message.NO_BORROW);
        }

        return transactionDTOList;
    }

    // Function 5, right after the id is typed: the id must be one of HIS borrows (asked
    // before the confirmation).
    public void checkMyBorrow(AssetRequestDTO requestDTO) throws Exception {
        findMyBorrow(requestDTO);
    }

    // Function 5: deletes the borrow, THEN puts the units back in stock.
    public void returnBorrow(AssetRequestDTO requestDTO) throws Exception {
        Borrow borrow = findMyBorrow(requestDTO);
        Asset asset = assetRepository.findById(borrow.getAssetId());

        // the brief: delete the selected borrow (borrow.dat file)
        borrowRepository.remove(borrow);

        // the asset still exists: update its quantity at stock (asset.dat file)
        if (asset != null) {
            asset.setQuantity(asset.getQuantity() + borrow.getQuantity());
            assetRepository.update(asset);
        }
    }

    // Finds the request by id among HIS requests only.
    private Request findMyRequest(AssetRequestDTO requestDTO) throws Exception {
        Request request = requestRepository.findById(requestDTO.getRequestId());

        // no such id, or somebody else's request
        if ((request == null) ||
                !request.getEmployeeId().equalsIgnoreCase(requestDTO.getEmployeeId())) {
            throw new Exception(String.format(Message.NOT_MY_REQUEST,
                    requestDTO.getRequestId().toUpperCase()));
        }

        return request;
    }

    // Finds the borrow by id among HIS borrows only.
    private Borrow findMyBorrow(AssetRequestDTO requestDTO) throws Exception {
        Borrow borrow = borrowRepository.findById(requestDTO.getBorrowId());

        // no such id, or somebody else's borrow
        if ((borrow == null) ||
                !borrow.getEmployeeId().equalsIgnoreCase(requestDTO.getEmployeeId())) {
            throw new Exception(String.format(Message.NOT_MY_BORROW,
                    requestDTO.getBorrowId().toUpperCase()));
        }

        return borrow;
    }

    // Copies a request or a borrow into a row and looks up the asset name.
    private TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        Asset asset = assetRepository.findById(transaction.getAssetId());

        // every column of the table; an asset in no file shows "(unknown)"
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAssetId(transaction.getAssetId());
        transactionDTO.setQuantity(transaction.getQuantity());
        transactionDTO.setDateTime(transaction.getDateTime());
        transactionDTO.setAssetName((asset == null) ? Message.UNKNOWN : asset.getName());
        return transactionDTO;
    }
}
