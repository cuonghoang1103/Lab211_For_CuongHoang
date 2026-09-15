package repository;

import java.util.ArrayList;
import model.Fruit;

/**
 * REPOSITORY: the fruits of the shop, kept in an ArrayList as the brief requires.
 *
 * @author HE176322
 */
public class FruitRepository {

    // The "database": fruits in the order they were created (item 1 = index 0).
    private ArrayList<Fruit> fruits = new ArrayList<>();

    // Creates an empty shop.
    public FruitRepository() {
    }

    // Counts the fruits.
    public int countFruits() {
        return fruits.size();
    }

    // Finds a fruit by id with a linear scan, ignoring case (f001 = F001).
    public Fruit findById(String fruitId) {
        // look at every fruit once
        for (Fruit fruit : fruits) {
            // same id, whatever the case
            if (fruit.getFruitId().equalsIgnoreCase(fruitId)) {
                return fruit;
            }
        }
        return null;
    }

    // Returns the fruit shown as item number itemNumber (1-based, like the screen).
    public Fruit findByItemNumber(int itemNumber) {
        return fruits.get(itemNumber - 1);
    }

    // Appends a new fruit at the end of the list.
    public void addFruit(Fruit fruit) {
        fruits.add(fruit);
    }

    // Returns a copy of the list, so a caller can never add or remove stored fruits.
    public ArrayList<Fruit> findAll() {
        return new ArrayList<>(fruits);
    }
}
