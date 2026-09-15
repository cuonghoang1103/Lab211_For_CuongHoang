package service;

import constants.Constants;
import constants.Message;
import dto.LoginRequestDTO;
import java.util.Locale;
import java.util.ResourceBundle;
import model.Account;

/**
 * SERVICE: the Ebank class the brief asks for - the rules of the login and the language
 * of its texts.
 *
 * @author HE176322
 */
public class Ebank {

    // The texts of the chosen language.
    private ResourceBundle bundle;
    // The account that has logged in; null until the login succeeds.
    private Account account;

    // Creates the Ebank with the English texts (the brief: English is the interface the
    // program starts with).
    public Ebank() {
        bundle = ResourceBundle.getBundle(Constants.BUNDLE_NAME,
                new Locale(Constants.LANGUAGE_EN));
    }

    // The brief's setLocate: switches every text to another language, by loading
    // Language_xx.properties of that locale.
    public void setLocate(Locale locate) {
        bundle = ResourceBundle.getBundle(Constants.BUNDLE_NAME, locate);
    }

    // Returns the text of a key in the chosen language.
    public String getText(String key) {
        return bundle.getString(key);
    }

    // The brief's checkAccountNumber: a number of exactly 10 digits.
    public String checkAccountNumber(String accountNumber) {
        // null or not exactly 10 digits
        if (accountNumber == null || !accountNumber.matches(Constants.ACCOUNT_REGEX)) {
            return bundle.getString(Message.KEY_ACCOUNT_ERROR);
        }
        return Constants.VALID;
    }

    // The brief's checkPassword: 8 to 31 characters, letters AND digits.
    public String checkPassword(String password) {
        // null, wrong length, a symbol, or letters/digits missing
        if (password == null || !password.matches(Constants.PASSWORD_REGEX)) {
            return bundle.getString(Message.KEY_PASSWORD_ERROR);
        }
        return Constants.VALID;
    }

    // The brief's checkCaptcha: the typed characters must be contained in the generated
    // captcha ("use the function contains()").
    public String checkCaptcha(String captchaInput, String captchaGenerate) {
        // nothing typed: contains("") would wrongly say yes
        if (captchaInput == null || captchaInput.isEmpty()) {
            return bundle.getString(Message.KEY_CAPTCHA_ERROR);
        }
        // the typed characters do not appear in the captcha
        if (!captchaGenerate.contains(captchaInput)) {
            return bundle.getString(Message.KEY_CAPTCHA_ERROR);
        }
        return Constants.VALID;
    }

    // The brief's Function 6 "Login", last step: remembers the account once the account
    // number, the password and the captcha have all passed.
    public void login(LoginRequestDTO requestDTO) {
        account = new Account(requestDTO.getAccountNumber(), requestDTO.getPassword());
    }
}
