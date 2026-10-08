package model;

import constants.Constants;

public class Account {

    // Ten dang nhap
    private String username;

    // Mat khau
    private String password;

    // Constructor co tham so
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Lay ten dang nhap
    public String getUsername() {
        return username;
    }

    // Lay mat khau
    public String getPassword() {
        return password;
    }

    // Tra ve 1 dong de ghi vao file: "username password"
    @Override
    public String toString() {
        return username + Constants.SEPARATOR + password;
    }
}
