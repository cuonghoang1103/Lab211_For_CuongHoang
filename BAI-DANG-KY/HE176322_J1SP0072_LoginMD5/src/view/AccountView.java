package view;

import dto.AccountResponseDTO;

/**
 * In ket qua cho nguoi dung.
 *
 * @author HE176322
 */
public class AccountView {

    // ket qua nhan tu controller
    private AccountResponseDTO accountResponseDTO;

    // nhan ket qua tu controller
    public void setAccountResponseDTO(AccountResponseDTO accountResponseDTO) {
        this.accountResponseDTO = accountResponseDTO;
    }

    // in cau ket qua roi xuong dong
    public void displayMessage() {
        System.out.println(accountResponseDTO);
    }

    // in man chao, khong xuong dong vi cuoi la cau hoi Y/N
    public void displayWelcome() {
        System.out.print(accountResponseDTO);
    }
}
