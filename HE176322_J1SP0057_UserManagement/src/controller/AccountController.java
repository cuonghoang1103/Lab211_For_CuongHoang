package controller;

import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import repository.AccountRepository;
import view.AccountView;

/**
 * CONTROLLER (Facade): receives a request DTO from main, asks the repository to do the work,
 * and hands the answer to the view - one render per menu option. No Scanner, no print, no
 * model, no file.
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

    // Start of the program: hands the lines main read from user.dat to the repository, which
    // turns them into the Collection of accounts. Nothing is shown.
    public void loadData(AccountRequestDTO requestDTO) {
        accountRepository.loadData(requestDTO);
    }

    // Option 1 (the brief's addAccount): stores the new account in the Collection and at the
    // end of user.dat, then the view prints "Create account successfully!" - once.
    public void addAccount(AccountRequestDTO requestDTO) throws Exception {
        AccountResponseDTO responseDTO = new AccountResponseDTO();

        // the repository throws when the user name is taken or user.dat cannot be written
        accountRepository.addAccount(requestDTO);

        // stored: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.CREATE_SUCCESS);
        accountView.setResponseDTO(responseDTO);
        accountView.display();
    }

    // Option 2 (the brief's find): logs in, then the view prints "Login successful!" - once.
    public void login(AccountRequestDTO requestDTO) throws Exception {
        AccountResponseDTO responseDTO = new AccountResponseDTO();

        // no stored account has this user name AND password: the brief's failure message
        if (!accountRepository.find(requestDTO)) {
            throw new Exception(Message.LOGIN_FAIL);
        }

        // found: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.LOGIN_SUCCESS);
        accountView.setResponseDTO(responseDTO);
        accountView.display();
    }
}
