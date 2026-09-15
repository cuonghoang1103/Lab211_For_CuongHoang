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

    // Reads request.dat and borrow.dat.
    public void loadTransactions() throws Exception {
        requestRepository.load();
        borrowRepository.load();
    }

    // Function 3: writes a new request into request.dat and returns its id.
    public String sendRequest(TransactionRequestDTO requestDTO) throws Exception {
        Asset asset = assetRepository.findById(requestDTO.getAssetID());
        // no asset has this id
        if (asset == null) {
            throw new Exception(Message.ASSET_NOT_EXIST);
        }
        // asking for more than the company has cannot be approved anyway
        if (requestDTO.getQuantity() > asset.getQuantity()) {
            throw new Exception(String.format(Message.NOT_ENOUGH, asset.getQuantity(),
                    asset.getName()));
        }
        Request request = new Request(requestRepository.nextId(Constants.REQUEST_PREFIX),
                asset.getAssetID(), requestDTO.getEmployeeID(), requestDTO.getQuantity(),
                LocalDateTime.now().format(dateTimeFormat));
        requestRepository.add(request);
        return request.getId();
    }

    // Function 4, first step: the requests of the employee logged in.
    public ArrayList<TransactionResponseDTO> getMyRequests(TransactionRequestDTO requestDTO)
            throws Exception {
        ArrayList<TransactionResponseDTO> rows = new ArrayList<>();
        // keep only this employee's rows
        for (Request request : requestRepository.findAll()) {
            // another employee's request is never shown
            if (request.getEmployeeID().equalsIgnoreCase(requestDTO.getEmployeeID())) {
                rows.add(toResponse(request));
            }
        }
        // nothing to cancel
        if (rows.isEmpty()) {
            throw new Exception(Message.NO_REQUEST);
        }
        return rows;
    }

    // Function 4, second step: the id must be one of HIS requests (asked before confirm).
    public void checkMyRequest(TransactionRequestDTO requestDTO) throws Exception {
        findMyRequest(requestDTO);
    }

    // Function 4: deletes the request from request.dat.
    public void cancelRequest(TransactionRequestDTO requestDTO) throws Exception {
        requestRepository.remove(findMyRequest(requestDTO));
    }

    // Function 5, first step: the borrows of the employee logged in.
    public ArrayList<TransactionResponseDTO> getMyBorrows(TransactionRequestDTO requestDTO)
            throws Exception {
        ArrayList<TransactionResponseDTO> rows = new ArrayList<>();
        // keep only this employee's rows
        for (Borrow borrow : borrowRepository.findAll()) {
            // another employee's borrow is never shown
            if (borrow.getEmployeeID().equalsIgnoreCase(requestDTO.getEmployeeID())) {
                rows.add(toResponse(borrow));
            }
        }
        // nothing to return
        if (rows.isEmpty()) {
            throw new Exception(Message.NO_BORROW);
        }
        return rows;
    }

    // Function 5, second step: the id must be one of HIS borrows (asked before confirm).
    public void checkMyBorrow(TransactionRequestDTO requestDTO) throws Exception {
        findMyBorrow(requestDTO);
    }

    // Function 5: deletes the borrow, THEN puts the units back in stock.
    public void returnBorrow(TransactionRequestDTO requestDTO) throws Exception {
        Borrow borrow = findMyBorrow(requestDTO);
        borrowRepository.remove(borrow);
        Asset asset = assetRepository.findById(borrow.getAssetID());
        // the asset still exists: its stock grows again
        if (asset != null) {
            asset.setQuantity(asset.getQuantity() + borrow.getQuantity());
            assetRepository.update(asset);
        }
    }

    // Finds the request by id among HIS requests only.
    private Request findMyRequest(TransactionRequestDTO requestDTO) throws Exception {
        Request request = requestRepository.findById(requestDTO.getId());
        // no such id, or somebody else's request
        if (request == null
                || !request.getEmployeeID().equalsIgnoreCase(requestDTO.getEmployeeID())) {
            throw new Exception(String.format(Message.NOT_MY_REQUEST,
                    requestDTO.getId().toUpperCase()));
        }
        return request;
    }

    // Finds the borrow by id among HIS borrows only.
    private Borrow findMyBorrow(TransactionRequestDTO requestDTO) throws Exception {
        Borrow borrow = borrowRepository.findById(requestDTO.getId());
        // no such id, or somebody else's borrow
        if (borrow == null
                || !borrow.getEmployeeID().equalsIgnoreCase(requestDTO.getEmployeeID())) {
            throw new Exception(String.format(Message.NOT_MY_BORROW,
                    requestDTO.getId().toUpperCase()));
        }
        return borrow;
    }

    // Copies a request or a borrow into a row and looks up the asset name.
    private TransactionResponseDTO toResponse(Transaction transaction) {
        TransactionResponseDTO row = new TransactionResponseDTO();
        row.setId(transaction.getId());
        row.setAssetID(transaction.getAssetID());
        row.setQuantity(transaction.getQuantity());
        row.setDateTime(transaction.getDateTime());
        Asset asset = assetRepository.findById(transaction.getAssetID());
        row.setAssetName(asset == null ? Message.UNKNOWN : asset.getName());
        return row;
    }
}
