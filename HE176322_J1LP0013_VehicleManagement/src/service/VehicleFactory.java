package service;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;
import constants.VehicleType;
import dto.VehicleRequestDTO;
import model.Car;
import model.Motorbike;
import model.Vehicle;
import utils.Validation;

/**
 * FACTORY: the one class that knows which class builds which kind of vehicle, so adding
 * a kind (the brief: "adding a new vehicle is easy") starts here. It builds a vehicle
 * from what the user typed and from one line of vehicles.txt.
 *
 * @author HE176322
 */
public class VehicleFactory {

    // Creates the factory; it keeps no state.
    public VehicleFactory() {
    }

    // Builds the vehicle the request describes, with every field filled in.
    public Vehicle createVehicle(VehicleRequestDTO requestDTO) throws Exception {
        Vehicle vehicle = null;

        // the kind decides the class
        switch (requestDTO.getVehicleType()) {
            // add menu option 1, or CAR in the file
            case CAR:
                vehicle = new Car();
                break;

            // add menu option 2, or MOTORBIKE in the file
            case MOTORBIKE:
                vehicle = new Motorbike();
                break;

            // a kind added to VehicleType but not here yet
            default:
                throw new Exception(Message.UNKNOWN_TYPE);
        }

        // the id is kept in capitals, every other field comes from the request
        vehicle.setId(requestDTO.getId().toUpperCase());
        applyChanges(vehicle, requestDTO);
        return vehicle;
    }

    // Function 1: builds the vehicle of one line of vehicles.txt, checked with the same
    // rules as the keyboard; null when the line is damaged.
    public Vehicle createFromLine(String line) {
        String[] partArray = line.split(Constants.DATA_SEPARATOR, -1);
        VehicleRequestDTO requestDTO = new VehicleRequestDTO();

        // a record has exactly 8 columns
        if (partArray.length != Constants.DATA_COLUMNS) {
            return null;
        }

        // any bad column makes the whole line damaged
        try {
            requestDTO.setVehicleType(VehicleType.findByCode(
                    partArray[Constants.COL_KIND].trim()));
            requestDTO.setId(Validation.checkText(partArray[Constants.COL_ID], TextField.ID));
            requestDTO.setName(Validation.checkText(partArray[Constants.COL_NAME],
                    TextField.NAME));
            requestDTO.setColor(Validation.checkText(partArray[Constants.COL_COLOR],
                    TextField.COLOR));
            requestDTO.setPrice(Validation.checkNumber(partArray[Constants.COL_PRICE],
                    NumberField.PRICE));
            requestDTO.setBrand(Validation.checkText(partArray[Constants.COL_BRAND],
                    TextField.BRAND));
            readDetails(partArray, requestDTO);
            return createVehicle(requestDTO);
        } catch (Exception e) {
            // one damaged line costs one line, not the whole load
            return null;
        }
    }

    // Copies every field that is not null into the vehicle (update: null = keep).
    public void applyChanges(Vehicle vehicle, VehicleRequestDTO requestDTO) {
        // a new name was typed
        if (requestDTO.getName() != null) {
            vehicle.setName(requestDTO.getName());
        }

        // a new color was typed
        if (requestDTO.getColor() != null) {
            vehicle.setColor(requestDTO.getColor());
        }

        // a new price was typed
        if (requestDTO.getPrice() != null) {
            vehicle.setPrice(requestDTO.getPrice());
        }

        // a new brand was typed
        if (requestDTO.getBrand() != null) {
            vehicle.setBrand(requestDTO.getBrand());
        }

        // the fields only a car has
        if (vehicle instanceof Car) {
            applyCarChanges((Car) vehicle, requestDTO);
        }

        // the fields only a motorbike has
        if (vehicle instanceof Motorbike) {
            applyMotorbikeChanges((Motorbike) vehicle, requestDTO);
        }
    }

    // Reads the two detail columns of a line, whose meaning depends on the kind.
    private void readDetails(String[] partArray, VehicleRequestDTO requestDTO)
            throws Exception {
        // an unknown word in the first column
        if (requestDTO.getVehicleType() == null) {
            throw new Exception(Message.DAMAGED_LINE);
        }

        // the kind decides what the two columns are
        switch (requestDTO.getVehicleType()) {
            // car: type, year
            case CAR:
                requestDTO.setCarType(Validation.checkText(partArray[Constants.COL_DETAIL_1],
                        TextField.CAR_TYPE));
                requestDTO.setYearOfManufacture((int) Validation.checkNumber(
                        partArray[Constants.COL_DETAIL_2], NumberField.YEAR));
                break;

            // motorbike: speed, license
            case MOTORBIKE:
                requestDTO.setSpeed(Validation.checkNumber(partArray[Constants.COL_DETAIL_1],
                        NumberField.SPEED));
                requestDTO.setRequireLicense(Validation.checkBoolean(
                        partArray[Constants.COL_DETAIL_2]));
                break;

            // a kind added to VehicleType but not here yet
            default:
                throw new Exception(Message.DAMAGED_LINE);
        }
    }

    // Copies the type and the year when they were typed.
    private void applyCarChanges(Car car, VehicleRequestDTO requestDTO) {
        StringBuilder typeBuilder = new StringBuilder();

        // a new type was typed: stored as "Travel" whatever the case typed
        if (requestDTO.getCarType() != null) {
            typeBuilder.append(requestDTO.getCarType().substring(0, 1).toUpperCase());
            typeBuilder.append(requestDTO.getCarType().substring(1).toLowerCase());
            car.setCarType(typeBuilder.toString());
        }

        // a new year was typed
        if (requestDTO.getYearOfManufacture() != null) {
            car.setYearOfManufacture(requestDTO.getYearOfManufacture());
        }
    }

    // Copies the speed and the license when they were typed.
    private void applyMotorbikeChanges(Motorbike motorbike, VehicleRequestDTO requestDTO) {
        // a new speed was typed
        if (requestDTO.getSpeed() != null) {
            motorbike.setSpeed(requestDTO.getSpeed());
        }

        // a new license answer was typed
        if (requestDTO.getRequireLicense() != null) {
            motorbike.setRequireLicense(requestDTO.getRequireLicense());
        }
    }
}
