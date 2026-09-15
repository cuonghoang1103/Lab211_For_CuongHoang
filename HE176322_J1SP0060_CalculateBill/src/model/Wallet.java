package model;

/**
 * MODEL: the user's wallet - the amount of money in it, and the one question a wallet can
 * answer about itself: is there enough money to pay this total?
 *
 * @author HE176322
 */
public class Wallet {

    // Money in the wallet.
    private int amount;

    // Creates an empty wallet (JavaBean: public no-argument constructor).
    public Wallet() {
    }

    // Creates a wallet holding the given amount.
    public Wallet(int amount) {
        this.amount = amount;
    }

    // Returns the money in the wallet.
    public int getAmount() {
        return amount;
    }

    // Changes the money in the wallet.
    public void setAmount(int amount) {
        this.amount = amount;
    }

    // The brief's payMoney: can the wallet pay this total?
    public boolean payMoney(int total) {
        return amount >= total;
    }

    // Polymorphism: overrides Object.toString(); returns the text instead of printing it,
    // because the model is not allowed to print.
    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
