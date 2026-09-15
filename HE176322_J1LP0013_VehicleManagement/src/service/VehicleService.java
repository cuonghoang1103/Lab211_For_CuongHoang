package service;

import constants.Message;
import dto.VehicleRequestDTO;
import dto.VehicleResponseDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Soundable;
import model.Vehicle;
import repository.VehicleRepository;

/**
 * SERVICE: the rules of the show room - add, update, delete, search and sort.
 *
 * @author HE176322
 */
public class VehicleService {

    // The show room.
    private VehicleRepository vehicleRepository;
    // Builds the right class for each kind.
    private VehicleFactory vehicleFactory;
    // Order of Function 5.1: name descending.
    private Comparator<Vehicle> nameOrder;
    // Order of Function 6.2: price descending.
    private Comparator<Vehicle> priceOrder;

    // Creates the service with its dependencies (constructor injection).
    public VehicleService(VehicleRepository vehicleRepository, VehicleFactory vehicleFactory) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
        this.nameOrder = new VehicleNameComparator();
        this.priceOrder = new VehiclePriceComparator();
    }

    // Refuses an id another vehicle already has (asked right after the id is typed).
    public void checkNewId(VehicleRequestDTO requestDTO) throws Exception {
        // taken id: the user types another one
        if (vehicleRepository.findById(requestDTO.getId()) != null) {
            throw new Exception(String.format(Message.ID_EXISTS, requestDTO.getId()));
        }
    }

    // Function 2: builds the vehicle and adds it to the show room.
    public void addVehicle(VehicleRequestDTO requestDTO) throws Exception {
        checkNewId(requestDTO);
        vehicleRepository.addVehicle(vehicleFactory.createVehicle(requestDTO));
    }

    // Functions 3, 4 and 5.2: the vehicle with this id, or "Vehicle does not exist".
    public VehicleResponseDTO findVehicle(VehicleRequestDTO requestDTO) throws Exception {
        return toResponse(requireVehicle(requestDTO.getId()));
    }

    // Function 3: copies the typed fields (null = keep) and puts the vehicle back.
    public VehicleResponseDTO updateVehicle(VehicleRequestDTO requestDTO) throws Exception {
        Vehicle vehicle = requireVehicle(requestDTO.getId());
        vehicleFactory.applyChanges(vehicle, requestDTO);
        vehicleRepository.updateVehicle(vehicle);
        return toResponse(vehicle);
    }

    // Function 4: removes the vehicle; false when it was not there.
    public boolean deleteVehicle(VehicleRequestDTO requestDTO) {
        return vehicleRepository.deleteVehicle(requestDTO.getId());
    }

    // Function 5.1: every vehicle whose name contains the text, name descending.
    public ArrayList<VehicleResponseDTO> searchByName(VehicleRequestDTO requestDTO)
            throws Exception {
        String text = requestDTO.getKeyword().toLowerCase();
        ArrayList<Vehicle> found = new ArrayList<>();
        // keep the vehicles whose name contains the text, ignoring case
        for (Vehicle vehicle : vehicleRepository.findAll()) {
            // "cam" finds "Camry"
            if (vehicle.getName().toLowerCase().contains(text)) {
                found.add(vehicle);
            }
        }
        // nothing matched
        if (found.isEmpty()) {
            throw new Exception(Message.NOT_FOUND);
        }
        Collections.sort(found, nameOrder);
        return toResponseList(found);
    }

    // Function 6.1: every vehicle, in the order they were added.
    public ArrayList<VehicleResponseDTO> getAllVehicles() throws Exception {
        checkNotEmpty();
        return toResponseList(vehicleRepository.findAll());
    }

    // Function 6.2: every vehicle, the most expensive first.
    public ArrayList<VehicleResponseDTO> getAllByPriceDescending() throws Exception {
        checkNotEmpty();
        ArrayList<Vehicle> sorted = vehicleRepository.findAll();
        Collections.sort(sorted, priceOrder);
        return toResponseList(sorted);
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

    // Copies a list of vehicles into rows for the view.
    private ArrayList<VehicleResponseDTO> toResponseList(ArrayList<Vehicle> vehicles) {
        ArrayList<VehicleResponseDTO> rows = new ArrayList<>();
        // one row per vehicle, same order
        for (Vehicle vehicle : vehicles) {
            rows.add(toResponse(vehicle));
        }
        return rows;
    }

    // Copies one vehicle into a row; getDetails() is polymorphic (Car or Motorbike).
    private VehicleResponseDTO toResponse(Vehicle vehicle) {
        VehicleResponseDTO row = new VehicleResponseDTO();
        row.setVehicleType(vehicle.getType());
        row.setId(vehicle.getId());
        row.setName(vehicle.getName());
        row.setColor(vehicle.getColor());
        row.setPrice(vehicle.getPrice());
        row.setBrand(vehicle.getBrand());
        row.setDetails(vehicle.getDetails());
        // only a vehicle that can be heard has a sound (asked by capability, not by class)
        if (vehicle instanceof Soundable) {
            row.setSound(((Soundable) vehicle).makeSound());
        }
        return row;
    }
}
