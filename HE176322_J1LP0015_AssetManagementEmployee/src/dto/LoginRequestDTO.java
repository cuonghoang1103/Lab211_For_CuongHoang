package dto;

/**
 * DTO main -> controller: the id and password typed at login.
 *
 * @author HE176322
 */
public class LoginRequestDTO {

    // Employee id typed.
    private String employeeID;
    // Password typed (plain text; only its MD5 is compared).
    private String password;

    // JavaBean constructor.
    public LoginRequestDTO() {
    }

    // Returns the employee id.
    public String getEmployeeID() {
        return employeeID;
    }

    // Changes the employee id.
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    // Returns the password.
    public String getPassword() {
        return password;
    }

    // Changes the password.
    public void setPassword(String password) {
        this.password = password;
    }
}
