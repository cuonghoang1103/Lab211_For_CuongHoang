package dto;

import java.util.Locale;

/**
 * DTO carrying what main has collected FROM main INTO the controller - a JavaBean
 * (private fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class LoginRequestDTO {

    // The language chosen in the menu.
    private Locale locale;
    // The account number typed by the user.
    private String accountNumber;
    // The password typed by the user.
    private String password;
    // The captcha generated for this login (by CaptchaUtils in main).
    private String captchaGenerate;
    // The captcha characters typed by the user.
    private String captchaInput;

    // JavaBean constructor: an empty request, filled through the setters.
    public LoginRequestDTO() {
    }

    // Returns the chosen language.
    public Locale getLocale() {
        return locale;
    }

    // Sets the chosen language.
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    // Returns the account number.
    public String getAccountNumber() {
        return accountNumber;
    }

    // Sets the account number.
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Returns the password.
    public String getPassword() {
        return password;
    }

    // Sets the password.
    public void setPassword(String password) {
        this.password = password;
    }

    // Returns the generated captcha.
    public String getCaptchaGenerate() {
        return captchaGenerate;
    }

    // Sets the generated captcha.
    public void setCaptchaGenerate(String captchaGenerate) {
        this.captchaGenerate = captchaGenerate;
    }

    // Returns the typed captcha characters.
    public String getCaptchaInput() {
        return captchaInput;
    }

    // Sets the typed captcha characters.
    public void setCaptchaInput(String captchaInput) {
        this.captchaInput = captchaInput;
    }
}
