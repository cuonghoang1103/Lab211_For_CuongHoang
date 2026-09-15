package model;

import constants.Constants;
import constants.Message;
import java.util.Locale;

/**
 * MODEL: every bee, and every rule that is the same for every bee.
 *
 * @author HE176322
 */
public abstract class Bee {

    // Health in percent: 100 at creation; read-only from outside.
    private double health;

    // Sets the health of every new bee to 100 (brief).
    protected Bee() {
        health = Constants.FULL_HEALTH;
    }

    // Template step: the health below which this kind of bee is dead.
    protected abstract int getThreshold();

    // The name of this kind of bee, shown in the report.
    public abstract String getType();

    // Returns the health (read-only - there is no setHealth, on purpose).
    public double getHealth() {
        return health;
    }

    // The brief's "dead property": true as soon as the health falls BELOW the threshold
    // of this bee's type.
    public boolean isDead() {
        return health < getThreshold();
    }

    // The brief's Damage(): reduces the health by percent% of the CURRENT health (not of
    // the original 100): two hits of 20 leave 64, not 60.
    public void Damage(int percent) {
        // the brief: a dead bee's health is frozen, and the call is no error
        if (isDead()) {
            return;
        }
        // the brief: the parameter is between 0 and 100
        if (percent < Constants.MIN_PERCENT || percent > Constants.MAX_PERCENT) {
            throw new IllegalArgumentException(String.format(Message.INVALID_DAMAGE,
                    Constants.MIN_PERCENT, Constants.MAX_PERCENT));
        }
        health = health * (Constants.MAX_PERCENT - percent) / Constants.PERCENT_BASE;
    }

    // Polymorphism: overrides Object.toString(); getType() inside it runs the subclass's
    // version.
    @Override
    public String toString() {
        return String.format(Constants.BEE_TEXT, getType(),
                String.format(Locale.US, Constants.HEALTH_FORMAT, health));
    }
}
