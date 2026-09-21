package repository;

import java.util.ArrayList;
import model.Fruit;

/**
 * REPOSITORY: the fruits of the shop, kept in an ArrayList as the brief requires, with
 * simple CRUD on them. No rule, no print.
 *
 * @author HE176322
 */
public class FruitRepository {

    // The "database": fruits in the order they were created (item 1 = index 0).
    private ArrayList<Fruit> fruitList;

    // Creates an empty shop.
    public FruitRepository() {
        fruitList = new ArrayList<>();
    }

    // Counts the fruits.
    public int countFruits() {
        return fruitList.size();
    }

    // Finds a fruit by id with a linear scan, ignoring case (f001 = F001); null when no
    // fruit has it.
    public Fruit findById(String fruitId) {
        // look at every fruit once
        for (Fruit fruit : fruitList) {
            // same id, whatever the case
            if (fruit.getFruitId().equalsIgnoreCase(fruitId)) {
                return fruit;
            }
        }

        return null;
    }

    // Returns the fruit shown as item number itemNumber (1-based, like the screen).
    public Fruit findByItemNumber(int itemNumber) {
        return fruitList.get(itemNumber - 1);
    }

    // Appends a new fruit at the end of the list.
    public void addFruit(Fruit fruit) {
        fruitList.add(fruit);
    }

    // Returns a copy of the list, so a caller can never add or remove stored fruits.
    public ArrayList<Fruit> findAll() {
        return new ArrayList<>(fruitList);
    }
}
