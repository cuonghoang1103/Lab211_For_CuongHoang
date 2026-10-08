package view;

import dto.AccountResponseDTO;

public class AccountView {

    // Ket qua da duoc xu ly, controller dua vao
    private AccountResponseDTO accountResponse;

    // Constructor
    public AccountView() {
    }

    // Nhan du lieu tu controller
    public void setAccountResponse(AccountResponseDTO accountResponse) {
        this.accountResponse = accountResponse;
    }

    // In cau ket qua ra man hinh
    public void display() {
        System.out.println(this.accountResponse);
    }
}
