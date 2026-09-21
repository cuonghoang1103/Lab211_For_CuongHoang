package dto;

import java.util.ArrayList;

/**
 * DTO main -> controller: what main read or typed for ONE function - the lines of the
 * four files at start-up, the login, the search text, a request to send, the request or
 * borrow chosen. Main fills only what its function needs. The employee id of Functions
 * 3-5 is filled by the controller from the session, never typed.
 *
 * @author HE176322
 */
public class AssetRequestDTO {

    // Lines of asset.dat, read by main.
    private ArrayList<String> assetLineList;

    // Lines of employee.dat, read by main.
    private ArrayList<String> employeeLineList;

    // Lines of request.dat, read by main.
    private ArrayList<String> requestLineList;

    // Lines of borrow.dat, read by main.
    private ArrayList<String> borrowLineList;

    // Employee id: typed at login; the session's id in Functions 3-5.
    private String employeeId;

    // MD5 hash of the password typed at login (main hashes it; the text never travels).
    private String password;

    // Text searched in the names.
    private String keyword;

    // Asset to borrow.
    private String assetId;

    // Quantity to borrow.
    private int quantity;

    // Request id to cancel.
    private String requestId;

    // Borrow id to return.
    private String borrowId;

    // JavaBean constructor.
    public AssetRequestDTO() {
    }

    // Returns the lines of asset.dat.
    public ArrayList<String> getAssetLineList() {
        return assetLineList;
    }

    // Changes the lines of asset.dat.
    public void setAssetLineList(ArrayList<String> assetLineList) {
        this.assetLineList = assetLineList;
    }

    // Returns the lines of employee.dat.
    public ArrayList<String> getEmployeeLineList() {
        return employeeLineList;
    }

    // Changes the lines of employee.dat.
    public void setEmployeeLineList(ArrayList<String> employeeLineList) {
        this.employeeLineList = employeeLineList;
    }

    // Returns the lines of request.dat.
    public ArrayList<String> getRequestLineList() {
        return requestLineList;
    }

    // Changes the lines of request.dat.
    public void setRequestLineList(ArrayList<String> requestLineList) {
        this.requestLineList = requestLineList;
    }

    // Returns the lines of borrow.dat.
    public ArrayList<String> getBorrowLineList() {
        return borrowLineList;
    }

    // Changes the lines of borrow.dat.
    public void setBorrowLineList(ArrayList<String> borrowLineList) {
        this.borrowLineList = borrowLineList;
    }

    // Returns the employee id.
    public String getEmployeeId() {
        return employeeId;
    }

    // Changes the employee id.
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    // Returns the MD5 hash of the password.
    public String getPassword() {
        return password;
    }

    // Changes the MD5 hash of the password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Returns the search text.
    public String getKeyword() {
        return keyword;
    }

    // Changes the search text.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // Returns the asset id.
    public String getAssetId() {
        return assetId;
    }

    // Changes the asset id.
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    // Returns the quantity.
    public int getQuantity() {
        return quantity;
    }

    // Changes the quantity.
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Returns the request id.
    public String getRequestId() {
        return requestId;
    }

    // Changes the request id.
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    // Returns the borrow id.
    public String getBorrowId() {
        return borrowId;
    }

    // Changes the borrow id.
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
}
