package dto;

public class ExpenseResponseDTO {

    // Ma chi tieu
    private int id;

    // Ngay chi tieu
    private String date;

    // So tien da dinh dang san (100 hoac 100.1)
    private String amount;

    // Noi dung chi tieu
    private String content;

    // Constructor day du thuoc tinh
    public ExpenseResponseDTO(int id, String date, String amount, String content) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.content = content;
    }

    // In 1 dong cua bang theo cot co dinh
    @Override
    public String toString() {
        return String.format("%-4d %-13s %-12s %s", id, date, amount, content);
    }
}
