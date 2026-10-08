package service;

import constants.Constants;
import constants.Message;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import repository.AccountRepository;

/**
 * Lop Ebank de bat: doi ngon ngu, kiem tra tai khoan, mat khau, captcha.
 *
 * @author HE176322
 */
public class Ebank {

    // bo cau chu cua ngon ngu dang chon (doc tu file .properties)
    private ResourceBundle languageBundle;

    // noi luu tai khoan da dang nhap
    private AccountRepository accountRepository;

    // khoi tao: mac dinh tieng Anh
    public Ebank() {
        Locale english = new Locale(Constants.LANGUAGE_EN);
        languageBundle = ResourceBundle.getBundle(Constants.BUNDLE_NAME, english);
        accountRepository = new AccountRepository();
    }

    // Function 1: doi ngon ngu, nap file Language_vi hoac Language_en
    public void setLocate(Locale locate) {
        languageBundle = ResourceBundle.getBundle(Constants.BUNDLE_NAME, locate);
    }

    // lay cau chu theo khoa, dung ngon ngu dang chon
    public String getText(String key) {
        return languageBundle.getString(key);
    }

    // Function 2: dung thi tra chuoi rong, sai thi tra cau loi
    public String checkAccountNumber(String accountNumber) {
        // so tai khoan khong phai dung 10 chu so
        if (!accountNumber.matches(Constants.ACCOUNT_REGEX)) {
            return getText(Message.KEY_ACCOUNT_ERROR);
        }
        return "";
    }

    // Function 3: dung thi tra chuoi rong, sai thi tra cau loi
    public String checkPassword(String password) {
        // mat khau sai do dai hoac thieu chu/so
        if (!password.matches(Constants.PASSWORD_REGEX)) {
            return getText(Message.KEY_PASSWORD_ERROR);
        }
        return "";
    }

    // Function 4: sinh captcha ngau nhien 5 ky tu
    public String generateCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        // moi vong lay 1 ky tu ngau nhien
        for (int i = 0; i < Constants.CAPTCHA_LENGTH; i++) {
            int position = random.nextInt(Constants.CAPTCHA_CHARACTERS.length());
            captcha.append(Constants.CAPTCHA_CHARACTERS.charAt(position));
        }
        return captcha.toString();
    }

    // Function 5: captcha dung neu captcha sinh ra chua chuoi nhap vao
    public String checkCaptcha(String captchaInput, String captchaGenerate) {
        // chuoi rong luon bi contains coi la dung nen phai chan truoc
        if (captchaInput.isEmpty()) {
            return getText(Message.KEY_CAPTCHA_ERROR);
        }
        // captcha khong chua chuoi nhap vao
        if (!captchaGenerate.contains(captchaInput)) {
            return getText(Message.KEY_CAPTCHA_ERROR);
        }
        return "";
    }

    // Function 6: dang nhap thanh cong thi luu tai khoan va tra ket qua
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        accountRepository.addAccount(loginRequestDTO);
        return new LoginResponseDTO(getText(Message.KEY_LOGIN_SUCCESS));
    }
}
