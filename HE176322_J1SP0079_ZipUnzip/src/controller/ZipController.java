package controller;

import dto.ZipRequestDTO;
import dto.ZipResponseDTO;
import service.ZipService;
import view.ZipView;

/**
 * CONTROLLER (and Facade): receives the request DTO from main, asks the service to
 * zip/unzip, and hands the result to the view - one render per menu option. No Scanner, no
 * print, no model.
 *
 * @author HE176322
 */
public class ZipController {

    // Zips and unzips (Controller -> Service -> Repository -> Model).
    private ZipService zipService;

    // Prints the result.
    private ZipView zipView;

    // Creates the controller together with its service and view.
    public ZipController() {
        zipService = new ZipService();
        zipView = new ZipView();
    }

    // Option 1 (Compression): zip, then the view prints the result - once.
    public void compress(ZipRequestDTO requestDTO) {
        ZipResponseDTO responseDTO = zipService.compress(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        zipView.setResponseDTO(responseDTO);
        zipView.display();
    }

    // Option 2 (Extraction): unzip, then the view prints the result - once.
    public void extract(ZipRequestDTO requestDTO) {
        ZipResponseDTO responseDTO = zipService.extract(requestDTO);

        // hand the result to the view, then render it - once for the whole flow
        zipView.setResponseDTO(responseDTO);
        zipView.display();
    }
}
