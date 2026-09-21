package controller;

import dto.ConvertRequestDTO;
import dto.ConvertResponseDTO;
import service.ConvertService;
import service.PositionalBaseStrategy;
import view.ConvertView;

/**
 * CONTROLLER: takes the request from main, asks the service to convert, and hands the
 * result to the view once. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class ConvertController {

    // Converts; configured with the hand-written positional strategy (Controller ->
    // Service -> Repository -> Model).
    private ConvertService convertService;

    // Prints the result.
    private ConvertView convertView;

    // Creates the controller: the service gets the positional strategy.
    public ConvertController() {
        convertService = new ConvertService(new PositionalBaseStrategy());
        convertView = new ConvertView();
    }

    // One conversion (the only workflow): the service converts, the view shows the result
    // ONCE. A value too big for the program comes back to main as an exception.
    public void convert(ConvertRequestDTO requestDTO) throws Exception {
        ConvertResponseDTO responseDTO = convertService.convert(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        convertView.setResponseDTO(responseDTO);
        convertView.display();
    }
}
