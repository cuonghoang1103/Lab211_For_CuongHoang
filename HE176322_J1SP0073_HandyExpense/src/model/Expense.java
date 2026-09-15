package model;

import java.util.Date;

/**
 * MODEL: one expense - ID, date, amount of money, content.
 *
 * @author HE176322
 */
public class Expense {

    // Unique ID, given automatically: largest ID + 1, the first is 1.
    private int id;
    // The day the money was spent.
    private Date date;
    // Amount of money; greater than 0.
    private double amount;
    // What the money was spent on.
    private String content;

    // JavaBean constructor: an empty expense, filled through the setters.
    public Expense() {
    }

    // Creates an expense with every field filled in.
    public Expense(int id, Date date, double amount, String content) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.content = content;
    }

    // Returns the ID.
    public int getId() {
        return id;
    }

    // Changes the ID.
    public void setId(int id) {
        this.id = id;
    }

    // Returns the date.
    public Date getDate() {
        return date;
    }

    // Changes the date.
    public void setDate(Date date) {
        this.date = date;
    }

    // Returns the amount.
    public double getAmount() {
        return amount;
    }

    // Changes the amount.
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Returns the content.
    public String getContent() {
        return content;
    }

    // Changes the content.
    public void setContent(String content) {
        this.content = content;
    }

    // Polymorphism: overrides Object.toString(); returns the text, the view prints.
    @Override
    public String toString() {
        return id + " " + amount + " " + content;
    }
}
