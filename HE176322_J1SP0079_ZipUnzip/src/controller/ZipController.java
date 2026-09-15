package controller;

import dto.ZipRequestDTO;
import dto.ZipResponseDTO;
import service.ZipService;
import view.ZipView;

/**
 * CONTROLLER (and Facade): receives the request DTO from main, asks the service to
 * zip/unzip, and hands the result to the view.
 *
 * @author HE176322
 */
public class ZipController {

    // Zips and unzips.
    private ZipService zipService;
    // Prints the result.
    private ZipView zipView;

    // Creates the controller together with its service and view.
    public ZipController() {
        zipService = new ZipService();
        zipView = new ZipView();
    }

    // Option 1 (Compression): zip, then show the result.
    public void compress(ZipRequestDTO requestDTO) {
        ZipResponseDTO response = zipService.compress(requestDTO);
        zipView.setResponse(response);
        zipView.display();
    }

    // Option 2 (Extraction): unzip, then show the result.
    public void extract(ZipRequestDTO requestDTO) {
        ZipResponseDTO response = zipService.extract(requestDTO);
        zipView.setResponse(response);
        zipView.display();
    }
}
