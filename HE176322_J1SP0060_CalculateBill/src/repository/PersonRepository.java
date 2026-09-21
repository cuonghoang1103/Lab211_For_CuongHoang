package repository;

import dto.BillRequestDTO;
import model.Person;
import model.Wallet;

/**
 * REPOSITORY: holds the data of the program - the user, with his bills and the wallet
 * inside him - and only simple CRUD on it. No computing, no print.
 *
 * @author HE176322
 */
public class PersonRepository {

    // The user the program works on (the model).
    private Person person;

    // Creates the store with a user who has no bills and an empty wallet.
    public PersonRepository() {
        person = new Person();
    }

    // Create: builds the user from the request - his bills and a wallet holding the amount
    // typed - and keeps him.
    public void savePerson(BillRequestDTO requestDTO) {
        person = new Person(requestDTO.getBillArray(), new Wallet(requestDTO.getWalletAmount()));
    }

    // Read: returns the user kept by the last save.
    public Person getPerson() {
        return person;
    }
}
