package controller;

import dto.ConfigRequestDTO;
import dto.CopyResponseDTO;
import exceptions.HandleException;
import service.CopyService;
import view.CopyView;

/**
 * CONTROLLER (and Facade): receives the request DTO from main, asks the service to do the
 * work, and hands the result to the view ONCE per flow. No Scanner, no print, no model.
 *
 * @author HE176322
 */
public class CopyController {

    // Checks the config and copies the files.
    private CopyService copyService;

    // Prints the results.
    private CopyView copyView;

    // Creates the controller together with its service and view.
    public CopyController() {
        copyService = new CopyService();
        copyView = new CopyView();
    }

    // Option 2 (and box 2 of option 1): saves the config the user typed; nothing is
    // shown when it works, a failure is thrown to main (box 3).
    public void createFileConfig(ConfigRequestDTO requestDTO) throws HandleException {
        copyService.saveConfig(requestDTO);
    }

    // Option 1: checks the config main read (box 4), copies the files, then lets the view
    // show them (box 5) - once for the whole flow.
    public void copyFile(ConfigRequestDTO requestDTO) throws HandleException {
        CopyResponseDTO responseDTO = new CopyResponseDTO();

        // a config error is thrown to main before anything is shown
        responseDTO.setFileNameList(copyService.copyFiles(requestDTO));

        // hand the copied names to the view, then render it - once for the whole flow
        copyView.setResponseDTO(responseDTO);
        copyView.display();
    }
}
