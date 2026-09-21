package repository;

import constants.Constants;
import constants.Message;
import dto.AccountRequestDTO;
import java.util.ArrayList;
import model.Account;
import model.AccountBuilder;

/**
 * REPOSITORY: holds the accounts (the brief's "DB") and the simple CRUD on them - add, find,
 * and the update of a password. No keyboard, no print, no hashing: every password it
 * receives is already the MD5 digest main computed, so it only stores and compares digests.
 *
 * @author HE176322
 */
public class AccountRepository {

    // The brief's DB: every account, in the order it was added.
    private ArrayList<Account> accountList;

    // The id the next account will get (last id + 1).
    private int nextId;

    // Creates an empty repository; the first account gets id 1.
    public AccountRepository() {
        accountList = new ArrayList<>();
        nextId = Constants.FIRST_ID;
    }

    // brief: public int addAccount(String username, String password, String name,
    // String phone, String email, String address, String dob) throws Exception.
    // The seven values come in one RequestDTO (checklist 1.1: parameters only when fewer
    // than 3), already checked by main, the password already hashed. Stores the account and
    // returns its id.
    public int addAccount(AccountRequestDTO requestDTO) throws Exception {
        Account existing = findByUsername(requestDTO.getUsername());
        Account account = null;

        // the brief: the username may not already exist in the DB (whatever the case)
        if (existing != null) {
            throw new Exception(String.format(Message.USERNAME_EXIST, existing.getUsername()));
        }

        // Builder: every value is named where it is set; the password is the MD5 digest
        account = new AccountBuilder()
                .setId(nextId)
                .setUsername(requestDTO.getUsername())
                .setPassword(requestDTO.getPassword())
                .setName(requestDTO.getName())
                .setPhone(requestDTO.getPhone())
                .setEmail(requestDTO.getEmail())
                .setAddress(requestDTO.getAddress())
                .setDob(requestDTO.getDob())
                .build();
        accountList.add(account);
        nextId++;
        return account.getId();
    }

    // The brief's login, with the brief's signature (2 parameters). The password is the MD5
    // digest of what was typed: true when the username exists (whatever the case) and the
    // stored digest is the same one.
    public Boolean login(String username, String password) {
        Account account = findByUsername(username);

        // no such username: login fails
        if (account == null) {
            return false;
        }

        // two digests are compared: a hash can never be turned back into the password
        return account.getPassword().equals(password);
    }

    // Returns the username as it was stored (login ignores the case: "nghianv" greets
    // "NghiaNV"). Called only after a successful login.
    public String findUsername(String username) {
        return findByUsername(username).getUsername();
    }

    // Returns the name of the account, for "Hi <name>, ...". Called only after a successful
    // login.
    public String findName(String username) {
        return findByUsername(username).getName();
    }

    // The change-password flow of the brief's screen: the old digest must be the stored one,
    // then the new digest replaces it.
    public void changePassword(AccountRequestDTO requestDTO) throws Exception {
        Account account = findByUsername(requestDTO.getUsername());

        // only reachable if the account vanished after the login
        if (account == null) {
            throw new Exception(Message.ACCOUNT_NOT_EXIST);
        }

        // the old password proves it is the owner
        if (!account.getPassword().equals(requestDTO.getOldPassword())) {
            throw new Exception(Message.OLD_PASSWORD_WRONG);
        }

        account.setPassword(requestDTO.getNewPassword());
    }

    // Finds the account with this username, ignoring upper/lower case; null when none.
    private Account findByUsername(String username) {
        // look at every account once
        for (Account account : accountList) {
            // same username, whatever the case
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }

        return null;
    }
}
