package repository;

import java.util.ArrayList;
import model.Bee;

/**
 * REPOSITORY: holds the colony - the brief's "single collection" of bees - and performs
 * the simple CRUD on it (clear, add, read).
 *
 * @author HE176322
 */
public class BeeRepository {

    // The "database": every bee, Workers first, then Queens, then Drones.
    private ArrayList<Bee> bees = new ArrayList<>();

    // Creates an empty colony.
    public BeeRepository() {
    }

    // Removes every bee (the brief: "clear the current bee list").
    public void clearBees() {
        bees.clear();
    }

    // Adds one bee at the end of the colony.
    public void addBee(Bee bee) {
        bees.add(bee);
    }

    // Returns the bees.
    public ArrayList<Bee> getBees() {
        return new ArrayList<>(bees);
    }

    // Tells whether no bee list exists yet.
    public boolean isEmpty() {
        return bees.isEmpty();
    }
}
