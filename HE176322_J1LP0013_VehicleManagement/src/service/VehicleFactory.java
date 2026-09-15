package service;

import constants.Message;
import dto.VehicleRequestDTO;
import model.Car;
import model.Motorbike;
import model.Vehicle;

/**
 * FACTORY: the one class that knows which class builds which kind of vehicle, so adding
 * a kind (the brief: "adding a new vehicle is easy") starts here.
 *
 * @author HE176322
 */
public class VehicleFactory {

    // Creates the factory; it keeps no state.
    public VehicleFactory() {
    }

    // Builds the vehicle the request describes, with every field filled in.
    public Vehicle createVehicle(VehicleRequestDTO requestDTO) throws Exception {
        Vehicle vehicle;
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
        vehicle.setId(requestDTO.getId().toUpperCase());
        applyChanges(vehicle, requestDTO);
        return vehicle;
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

    // Copies the type and the year when they were typed.
    private void applyCarChanges(Car car, VehicleRequestDTO requestDTO) {
        // a new type was typed: stored as "Travel" whatever the case typed
        if (requestDTO.getCarType() != null) {
            String type = requestDTO.getCarType();
            car.setCarType(type.substring(0, 1).toUpperCase()
                    + type.substring(1).toLowerCase());
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
