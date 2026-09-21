package repository;

import constants.Constants;
import constants.Message;
import dto.AccountRequestDTO;
import java.util.ArrayList;
import model.Account;
import utils.FileUtils;

/**
 * REPOSITORY: holds the accounts (the Collection of the brief's MARKING list) and performs
 * the simple CRUD on them: load, add, find. No keyboard, no print; writing user.dat goes
 * through utils/FileUtils.
 *
 * @author HE176322
 */
public class AccountRepository {

    // The Collection of the brief: every account of user.dat, in order.
    private ArrayList<Account> accountList;

    // Creates an empty repository; loadData() fills it with the lines of user.dat.
    public AccountRepository() {
        accountList = new ArrayList<>();
    }

    // MARKING "Loading user account from user.dat into Collection": turns every "username
    // password" line main read from the file into an account of the list.
    public void loadData(AccountRequestDTO requestDTO) {
        String[] partArray = null;

        // start from an empty Collection
        accountList.clear();

        // one line of the file = one account
        for (String line : requestDTO.getLineList()) {
            partArray = line.trim().split(Constants.SEPARATOR);

            // skip blank or broken lines instead of stopping the whole load
            if (partArray.length == Constants.LINE_PARTS) {
                accountList.add(new Account(partArray[0], partArray[1]));
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

    // The brief's addAccount: adds a new account to the Collection and appends it at the end
    // of user.dat.
    public void addAccount(AccountRequestDTO requestDTO) throws Exception {
        Account account = new Account(requestDTO.getUsername(), requestDTO.getPassword());

        // the brief: the user name may not coincide with an existing one
        if (isExistUsername(account.getUsername())) {
            throw new Exception(String.format(Message.DUPLICATE_USERNAME, account.getUsername()));
        }

        // MARKING "Checking existing of user.dat file before inserting"
        if (!FileUtils.isFileExist(Constants.DATA_FILE)) {
            FileUtils.createFile(Constants.DATA_FILE);
        }

        // MARKING "Adding a user account into user.dat file": the file first, the Collection
        // only after the file accepted it
        FileUtils.appendLine(Constants.DATA_FILE, account.toString());
        accountList.add(account);
    }

    // The brief's find: MARKING "Search user name and password into Collection" - the search
    // runs on the list, not on the file. True when a stored account has both.
    public boolean find(AccountRequestDTO requestDTO) {
        Account typedAccount = new Account(requestDTO.getUsername(), requestDTO.getPassword());

        // compare the typed pair with every stored account
        for (Account account : accountList) {
            // both fields equal: this is the account
            if (account.isMatch(typedAccount)) {
                return true;
            }
        }

        // no account has both: the controller shows one message for both cases (never
        // reveal which field was wrong)
        return false;
    }
}
