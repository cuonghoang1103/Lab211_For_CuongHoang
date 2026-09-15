package controller;

import constants.Message;
import dto.FileResponseDTO;
import dto.VehicleRequestDTO;
import dto.VehicleResponseDTO;
import repository.VehicleRepository;
import service.VehicleFactory;
import service.VehicleFileService;
import service.VehicleService;
import view.VehicleView;

/**
 * CONTROLLER (and Facade): receives a request DTO from main, asks a service to do the
 * work, and hands the result to the view.
 *
 * @author HE176322
 */
public class VehicleController {

    // Add, update, delete, search, sort.
    private VehicleService vehicleService;
    // Load and store.
    private VehicleFileService vehicleFileService;
    // Prints every result.
    private VehicleView vehicleView;

    // Wires the program: ONE show room and ONE factory shared by both services.
    public VehicleController() {
        VehicleRepository repository = new VehicleRepository();
        VehicleFactory factory = new VehicleFactory();
        vehicleService = new VehicleService(repository, factory);
        vehicleFileService = new VehicleFileService(repository, factory);
        vehicleView = new VehicleView();
    }

    // Function 1: loads vehicles.txt and says how many vehicles (and damaged lines).
    public void loadFromFile() throws Exception {
        FileResponseDTO result = vehicleFileService.loadFromFile();
        vehicleView.showMessage(String.format(Message.LOAD_SUCCESS, result.getCount(),
                result.getFileName()));
        // some lines of the file were not valid vehicles
        if (result.getSkipped() > 0) {
            vehicleView.showMessage(String.format(Message.LOAD_SKIPPED, result.getSkipped()));
        }
    }

    // Function 2, first step: refuses an id already used.
    public void checkNewId(VehicleRequestDTO requestDTO) throws Exception {
        vehicleService.checkNewId(requestDTO);
    }

    // Function 2: adds the vehicle.
    public void addVehicle(VehicleRequestDTO requestDTO) throws Exception {
        vehicleService.addVehicle(requestDTO);
        vehicleView.showMessage(Message.ADD_SUCCESS);
    }

    // Functions 3, 4 and 5.2: shows the vehicle with this id and returns it to main.
    public VehicleResponseDTO findVehicle(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO found = vehicleService.findVehicle(requestDTO);
        vehicleView.displayVehicle(found);
        return found;
    }

    // Function 3: updates the vehicle and shows the result.
    public void updateVehicle(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO updated = vehicleService.updateVehicle(requestDTO);
        vehicleView.showMessage(Message.UPDATE_SUCCESS);
        vehicleView.displayVehicle(updated);
    }

    // Function 4: deletes the vehicle and says whether it worked.
    public void deleteVehicle(VehicleRequestDTO requestDTO) {
        // the brief: "Show the result of the delete: success or fail"
        if (vehicleService.deleteVehicle(requestDTO)) {
            vehicleView.showMessage(Message.DELETE_SUCCESS);
        } else {
            // the vehicle disappeared between the confirmation and the delete
            vehicleView.showMessage(Message.DELETE_FAILED);
        }
    }

    // Function 5.1: the vehicles whose name contains the text, name descending.
    public void searchByName(VehicleRequestDTO requestDTO) throws Exception {
        vehicleView.displayList(vehicleService.searchByName(requestDTO));
    }

    // Function 6.1: every vehicle.
    public void showAll() throws Exception {
        vehicleView.displayList(vehicleService.getAllVehicles());
    }

    // Function 6.2: every vehicle by price descending; motorbikes make their sound.
    public void showAllByPriceDescending() throws Exception {
        vehicleView.displaySoundList(vehicleService.getAllByPriceDescending());
    }

    // Function 7: stores the show room and says how many vehicles.
    public void storeToFile() throws Exception {
        FileResponseDTO result = vehicleFileService.storeToFile();
        vehicleView.showMessage(String.format(Message.STORE_SUCCESS, result.getCount(),
                result.getFileName()));
    }

    // Tells main whether Quit should offer to store.
    public boolean hasUnsavedChanges() {
        return vehicleFileService.hasUnsavedChanges();
    }
}
