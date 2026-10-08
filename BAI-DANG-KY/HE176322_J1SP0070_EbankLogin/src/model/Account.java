package model;

/**
 * Tai khoan da dang nhap.
 *
 * @author HE176322
 */
public class Account {

    // so tai khoan 10 chu so (kieu String de giu so 0 dau)
    private String accountNumber;

    // mat khau
    private String password;

    // constructor co tham so
    public Account(String accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
    }

    // lay so tai khoan
    public String getAccountNumber() {
        return accountNumber;
    }

    // lay mat khau
    public String getPassword() {
        return password;
    }
}
