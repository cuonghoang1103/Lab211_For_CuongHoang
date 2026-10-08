package dto;

/**
 * Du lieu dang nhap Main gui vao Controller.
 *
 * @author HE176322
 */
public class LoginRequestDTO {

    // so tai khoan da nhap
    private String accountNumber;

    // mat khau da nhap
    private String password;

    // getter so tai khoan
    public String getAccountNumber() {
        return accountNumber;
    }

    // setter so tai khoan
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // getter mat khau
    public String getPassword() {
        return password;
    }

    // setter mat khau
    public void setPassword(String password) {
        this.password = password;
    }
}
