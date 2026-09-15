package controller;

import constants.Message;
import dto.CarRequestDTO;
import exceptions.ExceptionCar;
import service.ShowroomPriceStrategy;
import service.ShowroomService;
import view.ShowroomView;

/**
 * CONTROLLER: takes the customer's request from main, asks the service to check it, and
 * lets the view say "Sell Car".
 *
 * @author HE176322
 */
public class ShowroomController {

    // Checks requests; configured with the brief's price rule.
    private ShowroomService showroomService;
    // Prints the result.
    private ShowroomView showroomView;

    // Creates the controller: the service gets the brief's price rule.
    public ShowroomController() {
        showroomService = new ShowroomService(new ShowroomPriceStrategy());
        showroomView = new ShowroomView();
    }

    // The workflow of one request: the service checks, the view shows "Sell Car".
    public void checkCar(CarRequestDTO requestDTO) throws ExceptionCar {
        showroomService.checkCar(requestDTO);
        showroomView.showMessage(Message.SELL_CAR);
    }
}
