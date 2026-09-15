package model;

import java.util.Arrays;

/**
 * MODEL: the user - the bills he has to pay and the wallet he pays from.
 *
 * @author HE176322
 */
public class Person {

    // Values of the user's bills, one element per bill.
    private int[] bills;
    // The user's wallet; lives inside the person, as the brief asks.
    private Wallet wallet;

    // Creates a user with no bills and an empty wallet (JavaBean: public no-argument
    // constructor), to be filled through the setters.
    public Person() {
        this.bills = new int[0];
        this.wallet = new Wallet();
    }

    // Creates a user with his bills and his wallet.
    public Person(int[] bills, Wallet wallet) {
        this.bills = bills;
        this.wallet = wallet;
    }

    // Returns the bills.
    public int[] getBills() {
        return bills;
    }

    // Changes the bills.
    public void setBills(int[] bills) {
        this.bills = bills;
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
        return Arrays.toString(bills) + " " + wallet;
    }
}
