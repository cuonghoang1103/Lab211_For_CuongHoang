package model;

import constants.Constants;
import java.util.Arrays;

/**
 * MODEL: the user - the bills he has to pay and the wallet he pays from.
 *
 * @author HE176322
 */
public class Person {

    // Values of the user's bills, one element per bill.
    private int[] billArray;

    // The user's wallet; lives inside the person, as the brief asks.
    private Wallet wallet;

    // Creates a user with no bills and an empty wallet (JavaBean: public no-argument
    // constructor), to be filled through the setters.
    public Person() {
        this.billArray = new int[0];
        this.wallet = new Wallet();
    }

    // Creates a user with his bills and his wallet.
    public Person(int[] billArray, Wallet wallet) {
        this.billArray = billArray;
        this.wallet = wallet;
    }

    // Returns the bills.
    public int[] getBillArray() {
        return billArray;
    }

    // Changes the bills.
    public void setBillArray(int[] billArray) {
        this.billArray = billArray;
    }

    // Returns the wallet.
    public Wallet getWallet() {
        return wallet;
    }

    // Changes the wallet.
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    // Polymorphism: overrides Object.toString(); returns text, never prints.
    @Override
    public String toString() {
        return String.format(Constants.PERSON_FORMAT, Arrays.toString(billArray), wallet);
    }
}
