package controller;

import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import repository.AccountRepository;
import view.AccountView;

/**
 * CONTROLLER (and FACADE for main): receives a request DTO from main, asks the repository
 * to do the work, and hands the answer to the view - one render per flow. No Scanner, no
 * print, no model, no hashing.
 *
 * @author HE176322
 */
public class AccountController {

    // Where the accounts are stored (Controller -> Repository -> Model).
    private AccountRepository accountRepository;

    // Where the results are printed.
    private AccountView accountView;

    // Creates the controller together with its repository and view.
    public AccountController() {
        accountRepository = new AccountRepository();
        accountView = new AccountView();
    }

    // Option 1 (the brief's addAccount): stores the account, then the view prints "Account
    // [NghiaNV] has been added with id 1." - once.
    public void addAccount(AccountRequestDTO requestDTO) throws Exception {
        AccountResponseDTO responseDTO = new AccountResponseDTO();
        int id = 0;

        // the repository throws when the username already exists; else it gives the new id
        id = accountRepository.addAccount(requestDTO);

        // stored: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(String.format(Message.ADD_SUCCESS, requestDTO.getUsername(), id));
        accountView.setResponseDTO(responseDTO);
        accountView.display();
    }

    // Option 2 (the brief's login): when Account and Password are right, the view prints the
    // welcome screen - the title, "Hello <username>" and the change-password question - once.
    public void login(AccountRequestDTO requestDTO) throws Exception {
        AccountResponseDTO responseDTO = new AccountResponseDTO();

        // wrong username or wrong password: the brief's "login fail", one message for both
        if (!accountRepository.login(requestDTO.getUsername(), requestDTO.getPassword())) {
            throw new Exception(Message.LOGIN_FAIL);
        }

        // right: the stored spelling of the username and the name, for the greeting
        responseDTO.setUsername(accountRepository.findUsername(requestDTO.getUsername()));
        responseDTO.setName(accountRepository.findName(requestDTO.getUsername()));

        // hand the answer to the view, then render it - once for the whole flow
        accountView.setResponseDTO(responseDTO);
        accountView.display();
    }

    // The flow opened by "Y" on the welcome screen: the repository checks the old password
    // and stores the new digest, then the view prints "Password has been changed." - once.
    public void changePassword(AccountRequestDTO requestDTO) throws Exception {
        AccountResponseDTO responseDTO = new AccountResponseDTO();

        // the repository throws when the old password is not the stored one
        accountRepository.changePassword(requestDTO);

        // changed: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.CHANGE_SUCCESS);
        accountView.setResponseDTO(responseDTO);
        accountView.display();
    }
}
