package controller;

import constants.Message;
import dto.LoginRequestDTO;
import service.Ebank;
import view.EbankView;

/**
 * CONTROLLER (and Facade): receives the request DTO from main, asks Ebank for the rules
 * and the words, and hands the words to the view.
 *
 * @author HE176322
 */
public class EbankController {

    // The rules and the language of the login.
    private Ebank ebank;
    // Prints the translated texts.
    private EbankView ebankView;

    // Creates the controller together with its service and view.
    public EbankController() {
        ebank = new Ebank();
        ebankView = new EbankView();
    }

    // Menu options 1 and 2: switches the language of every later text.
    public void setLocate(LoginRequestDTO requestDTO) {
        ebank.setLocate(requestDTO.getLocale());
    }

    // Shows a prompt of the login in the chosen language.
    public void showPrompt(String key) {
        ebankView.showPrompt(ebank.getText(key));
    }

    // Step 1 of the login: checks the account number just typed.
    public void checkAccountNumber(LoginRequestDTO requestDTO) throws Exception {
        String error = ebank.checkAccountNumber(requestDTO.getAccountNumber());
        // a non-empty answer is the error to show
        if (!error.isEmpty()) {
            throw new Exception(error);
        }
    }

    // Step 2 of the login: checks the password just typed.
    public void checkPassword(LoginRequestDTO requestDTO) throws Exception {
        String error = ebank.checkPassword(requestDTO.getPassword());
        // a non-empty answer is the error to show
        if (!error.isEmpty()) {
            throw new Exception(error);
        }
    }

    // Step 3 of the login: shows the generated captcha with its label.
    public void showCaptcha(LoginRequestDTO requestDTO) {
        ebankView.showMessage(ebank.getText(Message.KEY_CAPTCHA_LABEL)
                + requestDTO.getCaptchaGenerate());
    }

    // Last step: checks the captcha characters and, when right, shows the success line.
    public void login(LoginRequestDTO requestDTO) throws Exception {
        String error = ebank.checkCaptcha(requestDTO.getCaptchaInput(),
                requestDTO.getCaptchaGenerate());
        // a non-empty answer is the error to show
        if (!error.isEmpty()) {
            throw new Exception(error);
        }
        ebank.login(requestDTO);
        ebankView.showMessage(ebank.getText(Message.KEY_LOGIN_SUCCESS));
    }
}
