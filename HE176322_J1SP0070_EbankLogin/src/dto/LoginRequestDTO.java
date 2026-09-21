package dto;

import java.util.Locale;

/**
 * DTO carrying one login FROM main INTO the controller: the chosen language and the three
 * things typed, plus the captcha main generated. One object instead of five parameters.
 *
 * @author HE176322
 */
public class LoginRequestDTO {

    // The language chosen in the menu.
    private Locale locale;

    // The account number typed.
    private String accountNumber;

    // The password typed.
    private String password;

    // The captcha the program generated.
    private String captchaGenerate;

    // The captcha characters typed.
    private String captchaInput;

    // Creates an empty request; main fills it through the setters.
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

    // Returns the account number typed.
    public String getAccountNumber() {
        return accountNumber;
    }

    // Sets the account number typed.
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Returns the password typed.
    public String getPassword() {
        return password;
    }

    // Sets the password typed.
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

    // Returns the captcha characters typed.
    public String getCaptchaInput() {
        return captchaInput;
    }

    // Sets the captcha characters typed.
    public void setCaptchaInput(String captchaInput) {
        this.captchaInput = captchaInput;
    }
}
