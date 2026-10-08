package dto;

/**
 * Ket qua Controller gui sang View.
 *
 * @author HE176322
 */
public class AccountResponseDTO {

    // cau ket qua da ghep san
    private String message;

    // constructor co tham so
    public AccountResponseDTO(String message) {
        this.message = message;
    }

    // hien thi ket qua
    @Override
    public String toString() {
        return message;
    }
}
