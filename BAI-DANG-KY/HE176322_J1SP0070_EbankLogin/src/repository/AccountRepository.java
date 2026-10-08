package repository;

import dto.LoginRequestDTO;
import java.util.ArrayList;
import java.util.List;
import model.Account;

/**
 * Luu cac tai khoan da dang nhap trong bo nho.
 *
 * @author HE176322
 */
public class AccountRepository {

    // danh sach tai khoan da dang nhap
    private List<Account> accountList;

    // khoi tao danh sach rong
    public AccountRepository() {
        accountList = new ArrayList<>();
    }

    // them tai khoan vao danh sach
    public void addAccount(LoginRequestDTO loginRequestDTO) {
        // tao doi tuong account tu loginRequestDTO
        Account account = new Account(loginRequestDTO.getAccountNumber(), loginRequestDTO.getPassword());
        accountList.add(account);
    }
}
