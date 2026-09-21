package controller;

import constants.VehicleType;
import dto.VehicleRequestDTO;
import dto.VehicleResponseDTO;
import service.VehicleService;
import view.VehicleView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks the service to do the
 * work, and hands the answer to the view - one render per function. No Scanner, no print,
 * no model; a broken rule is thrown as an Exception(Message.X) for main to print.
 *
 * @author HE176322
 */
public class VehicleController {

    // Every rule of the show room (Controller -> Service -> Repository -> Model).
    private VehicleService vehicleService;

    // Prints every result.
    private VehicleView vehicleView;

    // Creates the controller together with its service and view.
    public VehicleController() {
        vehicleService = new VehicleService();
        vehicleView = new VehicleView();
    }

    // Function 1: the lines main read from vehicles.txt replace the show room, then the
    // view prints how many vehicles were loaded - once.
    public void loadData(VehicleRequestDTO requestDTO) {
        VehicleResponseDTO responseDTO = vehicleService.loadData(requestDTO);

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 2: adds the vehicle (an id already used is thrown), then the view prints
    // "Add successfully!" - once.
    public void addVehicle(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.addVehicle(requestDTO);

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 3, check only (no render): "Vehicle does not exist" is thrown for an unknown
    // id; a known id answers with its kind, so main asks the right questions.
    public VehicleType findVehicleType(VehicleRequestDTO requestDTO) throws Exception {
        return vehicleService.findVehicleType(requestDTO);
    }

    // Function 3: copies the new values (blank = keep), then the view prints "Update
    // successfully!" and the vehicle - once.
    public void updateVehicle(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.updateVehicle(requestDTO);

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 4: deletes the vehicle when the user confirmed, then the view prints the
    // result (success, fail or cancelled) - once.
    public void deleteVehicle(VehicleRequestDTO requestDTO) {
        VehicleResponseDTO responseDTO = vehicleService.deleteVehicle(requestDTO);

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 5.1: the vehicles whose name contains the text, name descending - once.
    public void searchByName(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.searchByName(requestDTO);

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 5.2: the vehicle whose id is the text typed - once.
    public void searchById(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.searchById(requestDTO);

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 6.1: every vehicle - once.
    public void showAll() throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.getAllVehicles();

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 6.2: every vehicle by price descending; motorbikes make their sound - once.
    public void showAllByPriceDescending() throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.getAllByPriceDescending();

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }

    // Function 7: stores the show room in vehicles.txt, then the view prints how many
    // vehicles were stored - once.
    public void storeData() throws Exception {
        VehicleResponseDTO responseDTO = vehicleService.storeData();

        // one render for the whole flow
        vehicleView.setResponseDTO(responseDTO);
        vehicleView.display();
    }
}
