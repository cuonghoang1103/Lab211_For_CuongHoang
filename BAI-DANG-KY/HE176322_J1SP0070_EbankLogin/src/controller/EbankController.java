package controller;

import dto.LoginRequestDTO;
import java.util.Locale;
import service.Ebank;
import view.EbankView;

/**
 * Nhan yeu cau tu Main, goi Ebank xu ly, dua ket qua sang View.
 *
 * @author HE176322
 */
public class EbankController {

    // service xu ly dang nhap
    private Ebank ebank;

    // view in ket qua
    private EbankView ebankView;

    // khoi tao service va view
    public EbankController() {
        ebank = new Ebank();
        ebankView = new EbankView();
    }

    // doi ngon ngu theo lua chon menu
    public void setLocate(Locale locate) {
        ebank.setLocate(locate);
    }

    // lay cau chu theo ngon ngu dang chon
    public String getText(String key) {
        return ebank.getText(key);
    }

    // kiem tra so tai khoan
    public String checkAccountNumber(String accountNumber) {
        return ebank.checkAccountNumber(accountNumber);
    }

    // kiem tra mat khau
    public String checkPassword(String password) {
        return ebank.checkPassword(password);
    }

    // sinh captcha
    public String generateCaptcha() {
        return ebank.generateCaptcha();
    }

    // kiem tra captcha
    public String checkCaptcha(String captchaInput, String captchaGenerate) {
        return ebank.checkCaptcha(captchaInput, captchaGenerate);
    }

    // dang nhap va hien thi ket qua
    public void login(LoginRequestDTO loginRequestDTO) {
        ebankView.setLoginResponseDTO(ebank.login(loginRequestDTO));
        ebankView.display();
    }
}
