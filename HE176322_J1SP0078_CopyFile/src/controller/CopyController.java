package controller;

import constants.Message;
import dto.ConfigRequestDTO;
import dto.CopyResponseDTO;
import exceptions.ExceptionHandle;
import service.CopyService;
import view.CopyView;

/**
 * CONTROLLER (and Facade): receives the request DTO from main, asks the service to do the
 * work, and hands the result to the view.
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

    // Pre-check used by main: is there a config file already?
    public boolean isConfigExist() {
        return copyService.isConfigExist();
    }

    // Option 2: saves a config the user typed.
    public void createFileConfig(ConfigRequestDTO requestDTO) throws ExceptionHandle {
        copyService.saveConfig(requestDTO);
    }

    // Option 1: check the config (box 4 of the brief), then copy and show the copied
    // files (box 5).
    public void copyFile(ConfigRequestDTO requestDTO) throws ExceptionHandle {
        copyView.showMessage(Message.TITLE_CHECK);
        copyService.loadConfig(requestDTO);
        copyView.showMessage(Message.COPY_RUNNING);
        CopyResponseDTO response = copyService.copyFiles();
        copyView.setResponse(response);
        copyView.display();
    }
}
