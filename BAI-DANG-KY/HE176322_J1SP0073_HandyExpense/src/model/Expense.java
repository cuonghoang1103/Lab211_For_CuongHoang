package model;

public class Expense {

    // Ma chi tieu (tu tang)
    private int id;

    // Ngay chi tieu, dang dd-MMM-yyyy (vd 11-Apr-2009)
    private String date;

    // So tien
    private double amount;

    // Noi dung chi tieu
    private String content;

    // Constructor day du thuoc tinh
    public Expense(int id, String date, double amount, String content) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.content = content;
    }

    // Lay ma chi tieu
    public int getId() {
        return id;
    }

    // Lay ngay
    public String getDate() {
        return date;
    }

    // Lay so tien
    public double getAmount() {
        return amount;
    }

    // Lay noi dung
    public String getContent() {
        return content;
    }
}
