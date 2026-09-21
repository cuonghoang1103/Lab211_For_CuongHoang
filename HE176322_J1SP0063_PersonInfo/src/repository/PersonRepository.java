package repository;

import model.Person;

/**
 * REPOSITORY: holds the data of the program - the brief's array of 3 persons - and only
 * simple CRUD on it. No sorting, no print.
 *
 * @author HE176322
 */
public class PersonRepository {

    // The persons the program works on (the model), in the order they were typed.
    private Person[] personArray;

    // Creates an empty store.
    public PersonRepository() {
        personArray = new Person[0];
    }

    // Create: keeps the array of persons built from what the user typed.
    public void savePersonArray(Person[] typedArray) {
        personArray = typedArray;
    }

    // Read: returns the array kept by the last save.
    public Person[] getPersonArray() {
        return personArray;
    }
}
