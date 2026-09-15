package model;

/**
 * MODEL: the bank account that has logged in - a JavaBean (private fields, public
 * no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class Account {

    // The 10-digit account number.
    private String accountNumber;
    // The password (8-31 letters and digits).
    private String password;

    // JavaBean constructor: an empty account.
    public Account() {
    }

    // Creates an account with both fields filled in.
    public Account(String accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.password = password;
    }

    // Returns the account number.
    public String getAccountNumber() {
        return accountNumber;
    }

    // Changes the account number.
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Returns the password.
    public String getPassword() {
        return password;
    }

    // Changes the password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Polymorphism: overrides Object.toString().
    @Override
    public String toString() {
        return accountNumber;
    }
}
