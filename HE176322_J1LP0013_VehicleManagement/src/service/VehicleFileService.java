package service;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import constants.VehicleType;
import dto.FileResponseDTO;
import dto.VehicleRequestDTO;
import java.util.ArrayList;
import model.Vehicle;
import repository.VehicleRepository;
import utils.FileUtils;
import utils.Validation;

/**
 * SERVICE: Functions 1 and 7 - vehicles.txt to the show room and back.
 *
 * @author HE176322
 */
public class VehicleFileService {

    // The show room.
    private VehicleRepository vehicleRepository;
    // Builds the right class for the kind written in each line.
    private VehicleFactory vehicleFactory;

    // Creates the service with its dependencies (constructor injection).
    public VehicleFileService(VehicleRepository vehicleRepository,
            VehicleFactory vehicleFactory) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
    }

    // Function 1: every valid line becomes a vehicle; a damaged line is counted, not fatal.
    public FileResponseDTO loadFromFile() throws Exception {
        ArrayList<String> lines = FileUtils.readLines(Constants.DATA_FILE);
        ArrayList<Vehicle> loaded = new ArrayList<>();
        int skipped = 0;
        // one line = one vehicle
        for (String line : lines) {
            // an empty line is not a record
            if (line.trim().isEmpty()) {
                continue;
            }
            Vehicle vehicle = parseLine(line);
            // damaged, or an id already loaded from an earlier line
            if (vehicle == null || isLoaded(loaded, vehicle.getId())) {
                skipped++;
            } else {
                // a good line: keep the vehicle
                loaded.add(vehicle);
            }
        }
        vehicleRepository.replaceAll(loaded);
        return toResult(loaded.size(), skipped);
    }

    // Function 7: writes every vehicle as one line (the file is replaced, not appended).
    public FileResponseDTO storeToFile() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // toDataLine() is the Template Method of Vehicle
        for (Vehicle vehicle : vehicleRepository.findAll()) {
            lines.add(vehicle.toDataLine());
        }
        FileUtils.writeLines(Constants.DATA_FILE, lines);
        vehicleRepository.markStored();
        return toResult(lines.size(), 0);
    }

    // Tells whether there are changes not stored yet.
    public boolean hasUnsavedChanges() {
        return vehicleRepository.isChanged();
    }

    // One line -> one vehicle, checked with the same rules as the keyboard; null if bad.
    private Vehicle parseLine(String line) {
        String[] parts = line.split(Constants.DATA_SEPARATOR, -1);
        // a record has exactly 8 columns
        if (parts.length != Constants.DATA_COLUMNS) {
            return null;
        }
        // any bad column makes the whole line damaged
        try {
            VehicleRequestDTO dto = new VehicleRequestDTO();
            dto.setVehicleType(VehicleType.fromCode(parts[Constants.COL_KIND].trim()));
            dto.setId(Validation.checkText(parts[Constants.COL_ID], TextField.ID));
            dto.setName(Validation.checkText(parts[Constants.COL_NAME], TextField.NAME));
            dto.setColor(Validation.checkText(parts[Constants.COL_COLOR], TextField.COLOR));
            dto.setPrice(Validation.checkNumber(parts[Constants.COL_PRICE], NumberField.PRICE));
            dto.setBrand(Validation.checkText(parts[Constants.COL_BRAND], TextField.BRAND));
            readDetails(parts, dto);
            return vehicleFactory.createVehicle(dto);
        } catch (Exception e) {
            // one damaged line costs one line, not the whole load
            return null;
        }
    }

    // Reads the two detail columns, whose meaning depends on the kind.
    private void readDetails(String[] parts, VehicleRequestDTO dto) throws Exception {
        // an unknown word in the first column
        if (dto.getVehicleType() == null) {
            throw new Exception(Message.DAMAGED_LINE);
        }
        // the kind decides what the two columns are
        switch (dto.getVehicleType()) {
            // car: type, year
            case CAR:
                dto.setCarType(Validation.checkText(parts[Constants.COL_DETAIL_1],
                        TextField.CAR_TYPE));
                dto.setYearOfManufacture((int) Validation.checkNumber(
                        parts[Constants.COL_DETAIL_2], NumberField.YEAR));
                break;
            // motorbike: speed, license
            case MOTORBIKE:
                dto.setSpeed(Validation.checkNumber(parts[Constants.COL_DETAIL_1],
                        NumberField.SPEED));
                dto.setRequireLicense(Validation.checkBoolean(parts[Constants.COL_DETAIL_2]));
                break;
            // a kind added to VehicleType but not here yet
            default:
                throw new Exception(Message.DAMAGED_LINE);
        }
    }

    // Tells whether an id is already among the vehicles loaded so far.
    private boolean isLoaded(ArrayList<Vehicle> loaded, String id) {
        // look at every vehicle loaded so far
        for (Vehicle vehicle : loaded) {
            // two lines with one id: the second is refused
            if (vehicle.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    // Wraps the counts for the view.
    private FileResponseDTO toResult(int count, int skipped) {
        FileResponseDTO result = new FileResponseDTO();
        result.setFileName(Constants.DATA_FILE);
        result.setCount(count);
        result.setSkipped(skipped);
        return result;
    }
}
