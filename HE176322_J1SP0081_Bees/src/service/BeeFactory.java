package service;

import constants.BeeType;
import constants.Message;
import model.Bee;
import model.Drone;
import model.Queen;
import model.Worker;

/**
 * FACTORY (design pattern): the ONE place that knows "bee type X -> new class X".
 *
 * @author HE176322
 */
public class BeeFactory {

    // Creates the factory; it keeps no state.
    public BeeFactory() {
    }

    // Creates a new bee of the given type, at 100% health.
    public Bee createBee(BeeType type) {
        // one case per kind of bee of the brief
        switch (type) {
            // a worker: dead below 70%
            case WORKER:
                return new Worker();
            // a queen: dead below 20%
            case QUEEN:
                return new Queen();
            // a drone: dead below 50%
            case DRONE:
                return new Drone();
            // a new BeeType nobody taught the factory to build
            default:
                throw new IllegalArgumentException(String.format(Message.UNKNOWN_BEE, type));
        }
    }
}
