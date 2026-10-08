package controller;

import constants.Message;
import dto.AccountRequestDTO;
import dto.AccountResponseDTO;
import java.util.ArrayList;
import repository.AccountRepository;
import view.AccountView;

public class AccountController {

    // Khai bao repository luu tai khoan
    private AccountRepository accountRepository = new AccountRepository();

    // Khai bao view in ket qua
    private AccountView accountView = new AccountView();

    // Constructor
    public AccountController() {
    }

    // Nap du lieu doc tu file vao repository
    public void loadData(ArrayList<String> lineList) {
        this.accountRepository.loadData(lineList);
    }

    // Function 1: tao tai khoan moi
    public void addAccount(AccountRequestDTO dto) throws Exception {
        this.accountRepository.addAccount(dto);

        // Dua ket qua vao view va in ra
        this.accountView.setAccountResponse(new AccountResponseDTO(Message.CREATE_SUCCESS));
        this.accountView.display();
    }

    // Function 2: dang nhap
    public void login(AccountRequestDTO dto) throws Exception {
        // Khong tim thay tai khoan thi bao loi
        if (!this.accountRepository.find(dto)) {
            throw new Exception(Message.LOGIN_FAIL);
        }

        // Dua ket qua vao view va in ra
        this.accountView.setAccountResponse(new AccountResponseDTO(Message.LOGIN_SUCCESS));
        this.accountView.display();
    }
}
