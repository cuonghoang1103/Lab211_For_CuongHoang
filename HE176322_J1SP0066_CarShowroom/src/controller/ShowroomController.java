package controller;

import constants.Message;
import dto.CarRequestDTO;
import dto.CarResponseDTO;
import exceptions.CarException;
import service.ShowroomPriceStrategy;
import service.ShowroomService;
import view.ShowroomView;

/**
 * CONTROLLER (Facade): takes the customer's request from main, asks the service to check
 * it, and hands the answer to the view once. No Scanner, no print, no model.
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

    // The workflow of one request: the service checks it, the view shows "Sell Car" once.
    public void checkCar(CarRequestDTO requestDTO) throws CarException {
        CarResponseDTO responseDTO = new CarResponseDTO();

        // a refused request throws CarException to main before anything is printed
        showroomService.checkCar(requestDTO);

        // matched: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.SELL_CAR);
        showroomView.setResponseDTO(responseDTO);
        showroomView.display();
    }
}
