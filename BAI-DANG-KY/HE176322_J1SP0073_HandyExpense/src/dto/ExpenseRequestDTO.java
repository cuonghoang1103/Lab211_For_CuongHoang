package dto;

public class ExpenseRequestDTO {

    // Ngay nguoi dung nhap (da kiem hop le)
    private String date;

    // So tien nguoi dung nhap (da kiem > 0)
    private double amount;

    // Noi dung nguoi dung nhap (khong rong)
    private String content;

    // Constructor rong, Main dien du lieu qua setter
    public ExpenseRequestDTO() {
    }

    // Lay ngay
    public String getDate() {
        return date;
    }

    // Gan ngay
    public void setDate(String date) {
        this.date = date;
    }

    // Lay so tien
    public double getAmount() {
        return amount;
    }

    // Gan so tien
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Lay noi dung
    public String getContent() {
        return content;
    }

    // Gan noi dung
    public void setContent(String content) {
        this.content = content;
    }
}
