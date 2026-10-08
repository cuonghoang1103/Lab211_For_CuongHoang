package repository;

import constants.Constants;
import constants.Message;
import dto.AccountRequestDTO;
import java.util.ArrayList;
import model.Account;
import utils.FileUtils;

public class AccountRepository {

    // Danh sach tai khoan nap tu file user.dat
    private ArrayList<Account> accountList = new ArrayList<>();

    // Constructor
    public AccountRepository() {
    }

    // Nap cac dong doc tu file vao danh sach tai khoan
    public void loadData(ArrayList<String> lineList) {
        // Duyet tung dong cua file
        for (String line : lineList) {
            String[] partArray = line.trim().split(Constants.SEPARATOR);

            // Dong dung dang "username password" moi nap vao
            if (partArray.length == 2) {
                this.accountList.add(new Account(partArray[0], partArray[1]));
            }
        }
    }

    // Them tai khoan moi: ghi vao cuoi file roi them vao danh sach
    public void addAccount(AccountRequestDTO dto) throws Exception {
        // Username da ton tai thi bao loi
        if (isExistUsername(dto.getUsername())) {
            throw new Exception(String.format(Message.DUPLICATE_USERNAME, dto.getUsername()));
        }

        Account account = new Account(dto.getUsername(), dto.getPassword());
        FileUtils.appendLine(Constants.DATA_FILE, account.toString());
        this.accountList.add(account);
    }

    // Tim tai khoan trung ca username va password
    public boolean find(AccountRequestDTO dto) {
        // Duyet tung tai khoan trong danh sach
        for (Account account : this.accountList) {
            // Trung ca hai thi dang nhap dung
            if (account.getUsername().equals(dto.getUsername())
                    && account.getPassword().equals(dto.getPassword())) {
                return true;
            }
        }
        return false;
    }

    // Kiem tra username da co trong danh sach chua
    private boolean isExistUsername(String username) {
        // Duyet tung tai khoan trong danh sach
        for (Account account : this.accountList) {
            // Trung username thi da ton tai
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
