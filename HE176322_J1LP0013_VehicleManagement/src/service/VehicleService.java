package service;

import constants.Constants;
import constants.Message;
import constants.VehicleType;
import dto.VehicleRequestDTO;
import dto.VehicleResponseDTO;
import dto.VehicleRowDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.ISoundable;
import model.Vehicle;
import repository.VehicleRepository;

/**
 * SERVICE: the rules of the show room - load, add, update, delete, search, sort, store.
 * The only class the controller talks to (Controller -> Service -> Repository -> Model);
 * every function answers with a VehicleResponseDTO for the view (the check-only
 * findVehicleType answers with the kind). No print, no keyboard.
 *
 * @author HE176322
 */
public class VehicleService {

    // The show room (ONE collection of vehicles).
    private VehicleRepository vehicleRepository;

    // Builds the right class for each kind, from the keyboard or from a line of the file.
    private VehicleFactory vehicleFactory;

    // Order of Function 5.1: name descending.
    private Comparator<Vehicle> nameOrder;

    // Order of Function 6.2: price descending.
    private Comparator<Vehicle> priceOrder;

    // Creates the service with its show room, its factory and its two orders.
    public VehicleService() {
        vehicleRepository = new VehicleRepository();
        vehicleFactory = new VehicleFactory();
        nameOrder = new VehicleNameComparator();
        priceOrder = new VehiclePriceComparator();
    }

    // Function 1: the lines main read from vehicles.txt replace the show room; a damaged
    // line (or a repeated id) is counted and left out, it does not stop the load.
    public VehicleResponseDTO loadData(VehicleRequestDTO requestDTO) {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();
        ArrayList<Vehicle> loadedList = new ArrayList<>();
        Vehicle vehicle = null;
        int skipped = 0;

        // one line = one vehicle
        for (String line : requestDTO.getLineList()) {
            // an empty line is not a record
            if (line.trim().isEmpty()) {
                continue;
            }

            // the factory checks the columns with the keyboard's rules
            vehicle = vehicleFactory.createFromLine(line);

            // damaged, or an id already loaded from an earlier line
            if ((vehicle == null) || isLoaded(loadedList, vehicle.getId())) {
                skipped++;
            } else {
                // a good line: keep the vehicle
                loadedList.add(vehicle);
            }
        }

        // the file replaces the collection, then the answer gives the counts
        vehicleRepository.replaceAll(loadedList);
        responseDTO.setMessage(formatLoadMessage(loadedList.size(), skipped));
        return responseDTO;
    }

    // Function 2: builds the vehicle and adds it; an id already used is refused.
    public VehicleResponseDTO addVehicle(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        // the id must be free (c001 and C001 are the same id)
        if (vehicleRepository.findById(requestDTO.getId()) != null) {
            throw new Exception(String.format(Message.ID_EXISTS, requestDTO.getId()));
        }

        // the factory picks Car or Motorbike, the repository keeps it
        vehicleRepository.addVehicle(vehicleFactory.createVehicle(requestDTO));
        responseDTO.setMessage(Message.ADD_SUCCESS);
        return responseDTO;
    }

    // Function 3, first step (check only): the kind of the vehicle with this id, so main
    // asks the right questions; "Vehicle does not exist" when no vehicle has it.
    public VehicleType findVehicleType(VehicleRequestDTO requestDTO) throws Exception {
        return requireVehicle(requestDTO.getId()).getType();
    }

    // Function 3: copies the typed fields (null = keep) and answers with the new vehicle.
    public VehicleResponseDTO updateVehicle(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();
        Vehicle vehicle = requireVehicle(requestDTO.getId());

        // a null field keeps its old value; the vehicle keeps its place in the show room
        vehicleFactory.applyChanges(vehicle, requestDTO);
        vehicleRepository.updateVehicle(vehicle);

        // the brief: "Then system must print out the result of the updating"
        responseDTO.setMessage(Message.UPDATE_SUCCESS);
        responseDTO.setVehicle(convertToRow(vehicle));
        return responseDTO;
    }

    // Function 4: deletes the vehicle when the user confirmed; the answer is success,
    // fail (no vehicle has this id) or cancelled.
    public VehicleResponseDTO deleteVehicle(VehicleRequestDTO requestDTO) {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        // N to the confirm message: nothing is deleted
        if (!requestDTO.isConfirmed()) {
            responseDTO.setMessage(Message.DELETE_CANCELLED);
            return responseDTO;
        }

        // the brief: "Show the result of the delete: success or fail"
        if (vehicleRepository.deleteVehicle(requestDTO.getId())) {
            responseDTO.setMessage(Message.DELETE_SUCCESS);
        } else {
            // no vehicle has this id
            responseDTO.setMessage(Message.DELETE_FAILED);
        }

        return responseDTO;
    }

    // Function 5.1: every vehicle whose name contains the text, name descending.
    public VehicleResponseDTO searchByName(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();
        ArrayList<Vehicle> foundList = new ArrayList<>();
        String keyword = requestDTO.getKeyword().toLowerCase();

        // keep the vehicles whose name contains the text, ignoring case
        for (Vehicle vehicle : vehicleRepository.findAll()) {
            // "cam" finds "Camry"
            if (vehicle.getName().toLowerCase().contains(keyword)) {
                foundList.add(vehicle);
            }
        }

        // nothing matched
        if (foundList.isEmpty()) {
            throw new Exception(Message.NOT_FOUND);
        }

        // the brief's "(descending)": name Z to A, then the rows go to the view
        Collections.sort(foundList, nameOrder);
        responseDTO.setVehicleList(convertToRowList(foundList));
        return responseDTO;
    }

    // Function 5.2: the vehicle whose id is the text typed, or "Vehicle does not exist".
    public VehicleResponseDTO searchById(VehicleRequestDTO requestDTO) throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        // one vehicle: the view shows the header and its row
        responseDTO.setVehicle(convertToRow(requireVehicle(requestDTO.getId())));
        return responseDTO;
    }

    // Function 6.1: every vehicle, in the order they were added.
    public VehicleResponseDTO getAllVehicles() throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();

        // nothing to list until something is loaded or added
        checkNotEmpty();
        responseDTO.setVehicleList(convertToRowList(vehicleRepository.findAll()));
        return responseDTO;
    }

    // Function 6.2: every vehicle, the most expensive first; the brief: "If vehicle is a
    // motorbike type then call the makeSound function".
    public VehicleResponseDTO getAllByPriceDescending() throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();
        ArrayList<Vehicle> sortedList = vehicleRepository.findAll();
        ArrayList<VehicleRowDTO> rowList = new ArrayList<>();
        VehicleRowDTO row = null;

        // nothing to list until something is loaded or added
        checkNotEmpty();
        Collections.sort(sortedList, priceOrder);

        // one row per vehicle, most expensive first
        for (Vehicle vehicle : sortedList) {
            row = convertToRow(vehicle);

            // asked by capability, not by class: a vehicle that can be heard makes its sound
            if (vehicle instanceof ISoundable) {
                row.setSound(((ISoundable) vehicle).makeSound());
            }

            // the row joins the list in price order
            rowList.add(row);
        }

        // the view prints the rows, the sounds and the total
        responseDTO.setVehicleList(rowList);
        return responseDTO;
    }

    // Function 7: stores the show room in vehicles.txt and says how many vehicles went.
    public VehicleResponseDTO storeData() throws Exception {
        VehicleResponseDTO responseDTO = new VehicleResponseDTO();
        int count = vehicleRepository.storeToFile();

        // the answer names the count and the file
        responseDTO.setMessage(String.format(Message.STORE_SUCCESS, count, Constants.DATA_FILE));
        return responseDTO;
    }

    // Returns the vehicle with this id, or throws the brief's message.
    private Vehicle requireVehicle(String id) throws Exception {
        Vehicle vehicle = vehicleRepository.findById(id);

        // no vehicle has this id
        if (vehicle == null) {
            throw new Exception(Message.NOT_EXIST);
        }

        return vehicle;
    }

    // Refuses a list of an empty show room.
    private void checkNotEmpty() throws Exception {
        // nothing loaded or added yet
        if (vehicleRepository.countVehicles() == 0) {
            throw new Exception(Message.SHOW_ROOM_EMPTY);
        }
    }

    // Tells whether an id is already among the vehicles loaded so far.
    private boolean isLoaded(ArrayList<Vehicle> loadedList, String id) {
        // look at every vehicle loaded so far
        for (Vehicle vehicle : loadedList) {
            // two lines with one id: the second is refused
            if (vehicle.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

    // The answer of Function 1: the count and the file, plus the damaged lines when any.
    private String formatLoadMessage(int count, int skipped) {
        // every line was a good vehicle
        if (skipped == 0) {
            return String.format(Message.LOAD_SUCCESS, count, Constants.DATA_FILE);
        }

        return String.format(Message.LOAD_SKIPPED, count, Constants.DATA_FILE, skipped);
    }

    // Copies a list of vehicles into rows for the view, in the same order.
    private ArrayList<VehicleRowDTO> convertToRowList(ArrayList<Vehicle> vehicleList) {
        ArrayList<VehicleRowDTO> rowList = new ArrayList<>();

        // one row per vehicle, same order
        for (Vehicle vehicle : vehicleList) {
            rowList.add(convertToRow(vehicle));
        }

        return rowList;
    }

    // Copies one vehicle into a row; getDetails() is polymorphic (Car or Motorbike).
    private VehicleRowDTO convertToRow(Vehicle vehicle) {
        VehicleRowDTO row = new VehicleRowDTO();

        // the common columns, then the Details column each kind writes for itself
        row.setVehicleType(vehicle.getType());
        row.setId(vehicle.getId());
        row.setName(vehicle.getName());
        row.setColor(vehicle.getColor());
        row.setPrice(vehicle.getPrice());
        row.setBrand(vehicle.getBrand());
        row.setDetails(vehicle.getDetails());
        return row;
    }
}
