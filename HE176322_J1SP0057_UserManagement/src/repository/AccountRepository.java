package repository;

import constants.Constants;
import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import java.util.ArrayList;
import model.Account;
import utils.FileUtils;

/**
 * REPOSITORY: holds the accounts (the Collection of the brief's MARKING list) and
 * performs the simple CRUD on them: load, add, find.
 *
 * @author HE176322
 */
public class AccountRepository {

    // The Collection of the brief: every account of user.dat, in order.
    private ArrayList<Account> accountList = new ArrayList<>();

    // Creates an empty repository; loadData() fills it from user.dat.
    public AccountRepository() {
    }

    // MARKING "Loading user account from user.dat into Collection": reads every "username
    // password" line into the list.
    public void loadData() throws Exception {
        accountList = new ArrayList<>();
        // the first run: user.dat has not been created yet
        if (!FileUtils.isFileExist(Constants.DATA_FILE)) {
            return;
        }
        ArrayList<String> lines = FileUtils.readLines(Constants.DATA_FILE);
        // one line of the file = one account
        for (String line : lines) {
            String[] parts = line.trim().split(Constants.SEPARATOR);
            // skip blank or broken lines instead of stopping the whole load
            if (parts.length == Constants.LINE_PARTS) {
                accountList.add(new Account(parts[0], parts[1]));
            }
        }
    }

    // Tells whether a user name is already used.
    private boolean isExistUsername(String username) {
        // compare with every stored account
        for (Account account : accountList) {
            // same name found: stop searching
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // The brief's addAccount: adds a new account to the Collection and appends it at the
    // end of user.dat.
    public void addAccount(AccountRequestDTO requestDTO) throws Exception {
        // the brief: the user name may not coincide with an existing one
        if (isExistUsername(requestDTO.getUsername())) {
            throw new Exception(String.format(Message.DUPLICATE_USERNAME,
                    requestDTO.getUsername()));
        }
        Account account = new Account(requestDTO.getUsername(),
                requestDTO.getPassword());
        // MARKING "Checking existing of user.dat file before inserting"
        if (!FileUtils.isFileExist(Constants.DATA_FILE)) {
            FileUtils.createFile(Constants.DATA_FILE);
        }
        FileUtils.appendLine(Constants.DATA_FILE, account.toString());
        // added to memory only after the file accepted it
        accountList.add(account);
    }

    // The brief's find: MARKING "Search user name and password into Collection" - the
    // search runs on the list, not on the file.
    public AccountResponseDTO find(AccountRequestDTO requestDTO) throws Exception {
        Account typed = new Account(requestDTO.getUsername(),
                requestDTO.getPassword());
        // compare the typed pair with every stored account
        for (Account account : accountList) {
            // both fields equal: this is the account
            if (account.isMatch(typed)) {
                return new AccountResponseDTO(account.getUsername());
            }
        }
        // one message for both cases: never reveal which field was wrong
        throw new Exception(Message.LOGIN_FAIL);
    }
}
