package dto;

import java.util.ArrayList;

/**
 * DTO controller -> view: everything ONE function shows, rendered by one display() call.
 * The controller fills only what its function shows; the view prints what is not null.
 *
 * @author HE176322
 */
public class AssetResponseDTO {

    // The line(s) of a result, e.g. "Successfully"; null when there is none.
    private String message;

    // Rows of the asset table; null when no asset table is shown.
    private ArrayList<AssetDTO> assetList;

    // Rows of the request table; null when no request table is shown.
    private ArrayList<TransactionDTO> requestList;

    // Rows of the borrow table; null when no borrow table is shown.
    private ArrayList<TransactionDTO> borrowList;

    // JavaBean constructor: an empty answer, filled through the setters.
    public AssetResponseDTO() {
    }

    // Returns the result line(s).
    public String getMessage() {
        return message;
    }

    // Changes the result line(s).
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the asset rows.
    public ArrayList<AssetDTO> getAssetList() {
        return assetList;
    }

    // Changes the asset rows.
    public void setAssetList(ArrayList<AssetDTO> assetList) {
        this.assetList = assetList;
    }

    // Returns the request rows.
    public ArrayList<TransactionDTO> getRequestList() {
        return requestList;
    }

    // Changes the request rows.
    public void setRequestList(ArrayList<TransactionDTO> requestList) {
        this.requestList = requestList;
    }

    // Returns the borrow rows.
    public ArrayList<TransactionDTO> getBorrowList() {
        return borrowList;
    }

    // Changes the borrow rows.
    public void setBorrowList(ArrayList<TransactionDTO> borrowList) {
        this.borrowList = borrowList;
    }
}
