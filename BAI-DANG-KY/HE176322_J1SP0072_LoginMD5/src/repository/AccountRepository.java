package repository;

import constants.Message;
import dto.AccountRequestDTO;
import java.util.ArrayList;
import java.util.List;
import model.Account;

/**
 * Luu danh sach tai khoan trong bo nho, chi luu va so chuoi da bam.
 *
 * @author HE176322
 */
public class AccountRepository {

    // danh sach tai khoan
    private List<Account> accountList;

    // id cuoi cung da cap, dung de tu tang
    private int lastId;

    // khoi tao danh sach rong
    public AccountRepository() {
        accountList = new ArrayList<>();
        lastId = 0;
    }

    // tim tai khoan theo username (khong phan biet hoa thuong), khong co tra null
    private Account findByUsername(String username) {
        // duyet tung tai khoan
        for (Account account : accountList) {
            // trung username thi tra ve
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    // them tai khoan, tra ve id vua cap
    public int addAccount(AccountRequestDTO accountRequestDTO) throws Exception {
        Account existAccount = findByUsername(accountRequestDTO.getUsername());
        // username da ton tai
        if (existAccount != null) {
            throw new Exception(String.format(Message.USERNAME_EXIST, existAccount.getUsername()));
        }
        // tao account tu dto roi them vao danh sach
        Account account = new Account(++lastId, accountRequestDTO.getUsername(),
                accountRequestDTO.getPassword(), accountRequestDTO.getName(),
                accountRequestDTO.getPhone(), accountRequestDTO.getEmail(),
                accountRequestDTO.getAddress(), accountRequestDTO.getDob());
        accountList.add(account);
        return lastId;
    }

    // dang nhap: dung username va dung chuoi bam thi tra true
    public Boolean login(String username, String password) {
        Account account = findByUsername(username);
        // khong co username nay
        if (account == null) {
            return false;
        }
        return account.getPassword().equals(password);
    }

    // lay username dung nhu luc them (goi sau khi dang nhap dung)
    public String findUsername(String username) {
        return findByUsername(username).getUsername();
    }

    // lay ho ten (goi sau khi dang nhap dung)
    public String findName(String username) {
        return findByUsername(username).getName();
    }

    // doi mat khau: mat khau cu phai dung roi moi luu mat khau moi
    public void changePassword(AccountRequestDTO accountRequestDTO) throws Exception {
        Account account = findByUsername(accountRequestDTO.getUsername());
        // mat khau cu khong dung
        if (!account.getPassword().equals(accountRequestDTO.getOldPassword())) {
            throw new Exception(Message.OLD_PASSWORD_WRONG);
        }
        account.setPassword(accountRequestDTO.getNewPassword());
    }
}
