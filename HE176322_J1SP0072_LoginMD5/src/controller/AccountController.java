package controller;

import constants.Message;
import dto.AccountRequestDTO;
import repository.AccountRepository;
import view.AccountView;

/**
 * CONTROLLER (and FACADE for main): receives a request DTO from main, asks the repository
 * to do the work, and hands the result to the view.
 *
 * @author HE176322
 */
public class AccountController {

    // Where the accounts are stored.
    private AccountRepository accountRepository;
    // Where the results are printed.
    private AccountView accountView;

    // Creates the controller together with its repository and view.
    public AccountController() {
        accountRepository = new AccountRepository();
        accountView = new AccountView();
    }

    // Option 1: adds an account and shows its new ID.
    public void addAccount(AccountRequestDTO requestDTO) throws Exception {
        int id = accountRepository.addAccount(requestDTO);
        accountView.showMessage(String.format(Message.ADD_SUCCESS,
                requestDTO.getUsername(), id));
    }

    // Option 2: logs in.
    public Boolean login(AccountRequestDTO requestDTO) {
        Boolean success = accountRepository.login(requestDTO);
        // right username and password: greet
        if (success) {
            accountView.setAccount(accountRepository.findAccount(requestDTO));
            accountView.display();
        } else {
            // wrong username or password: one message for both
            accountView.showMessage(Message.LOGIN_FAIL);
        }
        return success;
    }

    // The change-password screen: replaces the stored digest.
    public void changePassword(AccountRequestDTO requestDTO) throws Exception {
        accountRepository.changePassword(requestDTO);
        accountView.showMessage(Message.CHANGE_SUCCESS);
    }
}
