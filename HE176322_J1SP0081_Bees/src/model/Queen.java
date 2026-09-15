package model;

import constants.Constants;
import constants.Message;

/**
 * MODEL: a queen bee - the most resilient, dead only below 20%.
 *
 * @author HE176322
 */
public class Queen extends Bee {

    // JavaBean constructor: a queen at 100% health.
    public Queen() {
        super();
    }

    // Template step filled in: a queen is dead below 20%.
    @Override
    protected int getThreshold() {
        return Constants.QUEEN_THRESHOLD;
    }

    // The name shown in the report.
    @Override
    public String getType() {
        return Message.TYPE_QUEEN;
    }
}
