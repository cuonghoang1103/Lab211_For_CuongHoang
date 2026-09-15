package model;

import constants.Constants;
import constants.Message;
import constants.VehicleType;
import java.util.Locale;

/**
 * MODEL: a motorbike - a Vehicle plus the speed and the license, and it can be heard.
 *
 * @author HE176322
 */
public class Motorbike extends Vehicle implements Soundable {

    // Speed in km/h, 1..400.
    private double speed;
    // True when a driving license is required.
    private boolean requireLicense;

    // JavaBean constructor: an empty motorbike, filled through the setters.
    public Motorbike() {
    }

    // A motorbike is always of kind MOTORBIKE.
    @Override
    public VehicleType getType() {
        return VehicleType.MOTORBIKE;
    }

    // "Speed: 150.0km/h, License: Yes" (Locale.US keeps the dot on every machine).
    @Override
    public String getDetails() {
        return String.format(Locale.US, Message.MOTORBIKE_DETAILS, speed,
                requireLicense ? Message.YES_TEXT : Message.NO_TEXT);
    }

    // "150.0,true" - the last two columns of the file.
    @Override
    protected String getDetailData() {
        return speed + Constants.DATA_SEPARATOR + requireLicense;
    }

    // The brief: a motorbike makes the sound "Tin tin tin".
    @Override
    public String makeSound() {
        return Message.MOTORBIKE_SOUND;
    }

    // Returns the speed.
    public double getSpeed() {
        return speed;
    }

    // Changes the speed.
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Tells whether a license is required.
    public boolean isRequireLicense() {
        return requireLicense;
    }

    // Changes whether a license is required.
    public void setRequireLicense(boolean requireLicense) {
        this.requireLicense = requireLicense;
    }
}
