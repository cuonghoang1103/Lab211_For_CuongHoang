package service;

import constants.Constants;
import constants.Message;
import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import java.util.Locale;
import repository.AccountRepository;
import utils.LanguageUtils;
import utils.Validation;

/**
 * SERVICE: the Ebank class the brief asks for - the language of the texts and the rules of
 * the login. Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class Ebank {

    // The language every text of Ebank is read in.
    private Locale locale;

    // Keeps the accounts that have logged in (Service -> Repository -> Model).
    private AccountRepository accountRepository;

    // Creates the Ebank in English, the language the program starts in.
    public Ebank() {
        locale = new Locale(Constants.LANGUAGE_EN);
        accountRepository = new AccountRepository();
    }

    // The brief's setLocate: every later text is read in this language.
    public void setLocate(Locale locate) {
        locale = locate;
    }

    // The brief's checkAccountNumber: empty when valid, else the error in the language.
    public String checkAccountNumber(String accountNumber) {
        // not exactly 10 digits
        if (!Validation.isMatch(accountNumber, Constants.ACCOUNT_REGEX)) {
            return LanguageUtils.getText(locale, Message.KEY_ACCOUNT_ERROR);
        }

        return Constants.VALID;
    }

    // The brief's checkPassword: empty when valid, else the error in the language.
    public String checkPassword(String password) {
        // wrong length, a symbol, or letters/digits missing
        if (!Validation.isMatch(password, Constants.PASSWORD_REGEX)) {
            return LanguageUtils.getText(locale, Message.KEY_PASSWORD_ERROR);
        }

        return Constants.VALID;
    }

    // The brief's checkCaptcha: empty when valid, else the error in the language.
    public String checkCaptcha(String captchaInput, String captchaGenerate) {
        // nothing typed, or characters that are not in the captcha
        if (!Validation.isCaptchaMatch(captchaInput, captchaGenerate)) {
            return LanguageUtils.getText(locale, Message.KEY_CAPTCHA_ERROR);
        }

        return Constants.VALID;
    }

    // The brief's Function 6, last step: checks the whole request once more and, when every
    // check passes, stores the account and answers with the success line.
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        String error = "";

        // answer in the language chosen in the menu, then run the brief's three checks
        setLocate(requestDTO.getLocale());
        error = findFirstError(requestDTO);

        // a check failed: the answer is its error, and nothing is stored
        if (!error.isEmpty()) {
            responseDTO.setMessage(error);
            return responseDTO;
        }

        // every check passed: store the account, answer with the success line
        accountRepository.addAccount(requestDTO);
        responseDTO.setMessage(LanguageUtils.getText(locale, Message.KEY_LOGIN_SUCCESS));
        return responseDTO;
    }

    // Runs the three checks in the order of the screen; empty when all of them pass.
    private String findFirstError(LoginRequestDTO requestDTO) {
        String error = checkAccountNumber(requestDTO.getAccountNumber());

        // the account number passed: check the password
        if (error.isEmpty()) {
            error = checkPassword(requestDTO.getPassword());
        }

        // the password passed too: check the captcha
        if (error.isEmpty()) {
            error = checkCaptcha(requestDTO.getCaptchaInput(), requestDTO.getCaptchaGenerate());
        }

        return error;
    }
}
