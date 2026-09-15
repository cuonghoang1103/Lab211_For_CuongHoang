package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: a worker bee - below 70% it can no longer fly, so it is dead.
 *
 * @author HE176322
 */
public class Worker extends Bee {

    // JavaBean constructor: a worker at 100% health.
    public Worker() {
        super();
    }

    // Template step filled in: a worker is dead below 70%.
    @Override
    protected int getThreshold() {
        return Constants.WORKER_THRESHOLD;
    }

    // The name shown in the report.
    @Override
    public String getType() {
        return Message.TYPE_WORKER;
    }
}
