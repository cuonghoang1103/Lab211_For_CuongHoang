package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: a drone bee - moderately resilient, dead below 50%.
 *
 * @author HE176322
 */
public class Drone extends Bee {

    // JavaBean constructor: a drone at 100% health.
    public Drone() {
        super();
    }

    // Template step filled in: a drone is dead below 50%.
    @Override
    protected int getThreshold() {
        return Constants.DRONE_THRESHOLD;
    }

    // The name shown in the report.
    @Override
    public String getType() {
        return Message.TYPE_DRONE;
    }
}
