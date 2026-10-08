package controller;

import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import repository.AccountRepository;
import view.AccountView;

/**
 * Nhan yeu cau tu Main, goi Repository, dua ket qua sang View.
 *
 * @author HE176322
 */
public class AccountController {

    // noi luu tai khoan
    private AccountRepository accountRepository;

    // view in ket qua
    private AccountView accountView;

    // khoi tao repository va view
    public AccountController() {
        accountRepository = new AccountRepository();
        accountView = new AccountView();
    }

    // them tai khoan va in ket qua
    public void addAccount(AccountRequestDTO accountRequestDTO) throws Exception {
        int id = accountRepository.addAccount(accountRequestDTO);
        String message = String.format(Message.ADD_SUCCESS, accountRequestDTO.getUsername(), id);
        accountView.setAccountResponseDTO(new AccountResponseDTO(message));
        accountView.displayMessage();
    }

    // dang nhap: sai thi bao loi, dung thi in man chao
    public void login(AccountRequestDTO accountRequestDTO) throws Exception {
        String username = accountRequestDTO.getUsername();
        // sai username hoac mat khau
        if (!accountRepository.login(username, accountRequestDTO.getPassword())) {
            throw new Exception(Message.LOGIN_FAIL);
        }
        String message = String.format(Message.WELCOME,
                accountRepository.findUsername(username), accountRepository.findName(username));
        accountView.setAccountResponseDTO(new AccountResponseDTO(message));
        accountView.displayWelcome();
    }

    // doi mat khau va in ket qua
    public void changePassword(AccountRequestDTO accountRequestDTO) throws Exception {
        accountRepository.changePassword(accountRequestDTO);
        accountView.setAccountResponseDTO(new AccountResponseDTO(Message.CHANGE_SUCCESS));
        accountView.displayMessage();
    }
}
