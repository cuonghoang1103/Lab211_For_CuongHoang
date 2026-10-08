package dto;

public class AccountResponseDTO {

    // Cau ket qua de view in ra
    private String message;

    // Constructor co tham so
    public AccountResponseDTO(String message) {
        this.message = message;
    }

    // Tra ve cau ket qua
    @Override
    public String toString() {
        return message;
    }
}
