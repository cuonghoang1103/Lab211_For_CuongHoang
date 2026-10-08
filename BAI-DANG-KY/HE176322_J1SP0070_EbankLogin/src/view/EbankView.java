package view;

import dto.LoginResponseDTO;

/**
 * In ket qua dang nhap.
 *
 * @author HE176322
 */
public class EbankView {

    // ket qua nhan tu controller
    private LoginResponseDTO loginResponseDTO;

    // nhan ket qua tu controller
    public void setLoginResponseDTO(LoginResponseDTO loginResponseDTO) {
        this.loginResponseDTO = loginResponseDTO;
    }

    // in ket qua dang nhap
    public void display() {
        System.out.println(loginResponseDTO);
    }
}
