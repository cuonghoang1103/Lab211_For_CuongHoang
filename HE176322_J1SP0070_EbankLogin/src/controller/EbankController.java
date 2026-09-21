package controller;

import dto.LoginRequestDTO;
import dto.LoginResponseDTO;
import service.Ebank;
import view.EbankView;

/**
 * CONTROLLER (Facade): takes the login request from main, lets Ebank decide, and hands the
 * answer to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class EbankController {

    // The brief's Ebank: the language of the texts and the rules of the login.
    private Ebank ebank;

    // Prints the answer of the login.
    private EbankView ebankView;

    // Creates the controller together with its service and its view.
    public EbankController() {
        ebank = new Ebank();
        ebankView = new EbankView();
    }

    // The only workflow: Ebank checks the request, the view shows the answer ONCE.
    public void login(LoginRequestDTO requestDTO) {
        LoginResponseDTO responseDTO = ebank.login(requestDTO);

        // hand the answer to the view, then render it - once for the whole flow
        ebankView.setResponseDTO(responseDTO);
        ebankView.display();
    }
}
