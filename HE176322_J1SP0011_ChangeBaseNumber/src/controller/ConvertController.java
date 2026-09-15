package controller;

import dto.ConvertRequestDTO;
import dto.ConvertResponseDTO;
import service.ConvertService;
import service.PositionalBaseStrategy;
import view.ConvertView;

/**
 * CONTROLLER: takes the request from main, asks the service to convert, and hands the
 * result to the view.
 *
 * @author HE176322
 */
public class ConvertController {

    // Converts; configured with the hand-written positional strategy.
    private ConvertService convertService;
    // Prints the result.
    private ConvertView convertView;

    // Creates the controller: the service gets the positional strategy.
    public ConvertController() {
        convertService = new ConvertService(new PositionalBaseStrategy());
        convertView = new ConvertView();
    }

    // One conversion: service computes, view displays.
    public void convert(ConvertRequestDTO requestDTO) throws Exception {
        ConvertResponseDTO response = convertService.convert(requestDTO);
        convertView.setResponse(response);
        convertView.display();
    }
}
