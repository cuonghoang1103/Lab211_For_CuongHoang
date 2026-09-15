package repository;

import constants.Constants;
import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import java.util.ArrayList;
import java.util.Date;
import model.Account;
import model.AccountBuilder;
import utils.MD5Utils;
import utils.Validation;

/**
 * REPOSITORY: holds the accounts and performs the brief's functions on them - addAccount
 * and login - plus the change-password flow of the brief's screen.
 *
 * @author HE176322
 */
public class AccountRepository {

    // The "database" of accounts, in the order they were added.
    private ArrayList<Account> accounts = new ArrayList<>();
    // The ID the next account will get.
    private int nextId = Constants.FIRST_ID;

    // Creates an empty repository.
    public AccountRepository() {
    }

    // The brief's addAccount: checks every value, hashes the password, then stores the
    // account.
    public int addAccount(AccountRequestDTO requestDTO) throws Exception {
        String username = Validation.getRequired(requestDTO.getUsername(),
                Message.USERNAME_EMPTY);
        Account existing = findByUsername(username);
        // the brief: the username must not already exist in the DB
        if (existing != null) {
            throw new Exception(String.format(Message.USERNAME_EXIST,
                    existing.getUsername()));
        }
        String password = Validation.getRequired(requestDTO.getPassword(),
                Message.PASSWORD_EMPTY);
        String name = Validation.getRequired(requestDTO.getName(), Message.NAME_EMPTY);
        String phone = Validation.getPhone(requestDTO.getPhone());
        String email = Validation.getEmail(requestDTO.getEmail());
        Date dob = Validation.getDob(requestDTO.getDob());
        Account account = new AccountBuilder()
                .withId(nextId)
                .withUsername(username)
                .withPassword(MD5Utils.hash(password))
                .withName(name)
                .withPhone(phone)
                .withEmail(email)
                .withAddress(Validation.getText(requestDTO.getAddress()))
                .withDob(dob)
                .build();
        accounts.add(account);
        nextId++;
        return account.getId();
    }

    // The brief's login: true when the username exists and the password's MD5 digest
    // equals the stored one.
    public Boolean login(AccountRequestDTO requestDTO) {
        Account account = findByUsername(Validation.getText(requestDTO.getUsername()));
        // no such username: login fails
        if (account == null) {
            return false;
        }
        return isPasswordCorrect(account, requestDTO.getPassword());
    }

    // Returns what the welcome screen shows about an account.
    public AccountResponseDTO findAccount(AccountRequestDTO requestDTO) {
        Account account = findByUsername(Validation.getText(requestDTO.getUsername()));
        // nothing to show for an unknown username
        if (account == null) {
            return null;
        }
        AccountResponseDTO response = new AccountResponseDTO();
        response.setUsername(account.getUsername());
        response.setName(account.getName());
        return response;
    }

    // The change-password flow of the brief's screen: old password, new password, new
    // password again.
    public void changePassword(AccountRequestDTO requestDTO) throws Exception {
        Account account = findByUsername(Validation.getText(requestDTO.getUsername()));
        // only reachable if the account vanished after login
        if (account == null) {
            throw new Exception(Message.ACCOUNT_NOT_EXIST);
        }
        // the old password proves it is the owner
        if (!isPasswordCorrect(account, requestDTO.getOldPassword())) {
            throw new Exception(Message.OLD_PASSWORD_WRONG);
        }
        String newPassword = Validation.getRequired(requestDTO.getNewPassword(),
                Message.NEW_PASSWORD_EMPTY);
        // the second typing must match the first
        if (!newPassword.equals(Validation.getText(requestDTO.getRenewPassword()))) {
            throw new Exception(Message.PASSWORD_MISMATCH);
        }
        account.setPassword(MD5Utils.hash(newPassword));
    }

    // Finds the account with this username, ignoring upper/lower case.
    private Account findByUsername(String username) {
        // look at every account once
        for (Account account : accounts) {
            // same username, whatever the case
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    // Hashes the attempt and compares digests; the stored digest is never turned back
    // into a password (a hash cannot be).
    private boolean isPasswordCorrect(Account account, String password) {
        return account.getPassword().equals(MD5Utils.hash(Validation.getText(password)));
    }
}
