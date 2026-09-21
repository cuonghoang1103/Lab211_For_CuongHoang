package repository;

import dto.LoginRequestDTO;
import java.util.ArrayList;
import model.Account;

/**
 * REPOSITORY: holds the data of the program - the accounts that have logged in - and only
 * simple CRUD on it. No rule, no print.
 *
 * @author HE176322
 */
public class AccountRepository {

    // Every account that has logged in during this run, in order.
    private ArrayList<Account> accountList;

    // Creates an empty store.
    public AccountRepository() {
        accountList = new ArrayList<>();
    }

    // Create: turns the request into an Account (the model) and stores it.
    public void addAccount(LoginRequestDTO requestDTO) {
        Account account = new Account(requestDTO.getAccountNumber(), requestDTO.getPassword());

        // keep it in the list of this run
        accountList.add(account);
    }
}
