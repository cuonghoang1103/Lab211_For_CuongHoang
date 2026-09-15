package controller;

import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import repository.AccountRepository;
import view.AccountView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the result to the view.
 *
 * @author HE176322
 */
public class AccountController {

    // Where the accounts are stored; the controller owns its repository.
    private AccountRepository accountRepository;
    // Where the results are printed.
    private AccountView accountView;

    // Creates the controller together with its repository and view.
    public AccountController() {
        accountRepository = new AccountRepository();
        accountView = new AccountView();
    }

    // Loads user.dat into the Collection; main calls it once, at start.
    public void loadData() throws Exception {
        accountRepository.loadData();
    }

    // Option 1: creates a new account.
    public void addAccount(AccountRequestDTO requestDTO) throws Exception {
        accountRepository.addAccount(requestDTO);
        accountView.showMessage(Message.CREATE_SUCCESS);
    }

    // Option 2: logs in - finds the account and lets the view greet it.
    public void login(AccountRequestDTO requestDTO) throws Exception {
        AccountResponseDTO account = accountRepository.find(requestDTO);
        accountView.setAccount(account);
        accountView.display();
    }
}
