package repository;

import java.util.ArrayList;
import model.Bee;

/**
 * REPOSITORY: holds the colony - the brief's "single collection" of bees - and performs
 * the simple CRUD on it (clear, add, read). No rule, no print.
 *
 * @author HE176322
 */
public class BeeRepository {

    // The "database": every bee, Workers first, then Queens, then Drones.
    private ArrayList<Bee> beeList;

    // Creates an empty colony.
    public BeeRepository() {
        beeList = new ArrayList<>();
    }

    // Delete: removes every bee (the brief: "clear the current bee list").
    public void clearBees() {
        beeList.clear();
    }

    // Create: adds one bee at the end of the colony.
    public void addBee(Bee bee) {
        beeList.add(bee);
    }

    // Read: returns the bees (a copy of the list: the caller may damage the bees, but not
    // add or remove any behind the repository's back).
    public ArrayList<Bee> getBeeList() {
        return new ArrayList<>(beeList);
    }

    // Tells whether no bee list exists yet.
    public boolean isEmpty() {
        return beeList.isEmpty();
    }
}
