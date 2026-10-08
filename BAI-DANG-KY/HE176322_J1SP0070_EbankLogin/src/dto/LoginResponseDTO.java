package dto;

/**
 * Ket qua dang nhap Controller gui sang View.
 *
 * @author HE176322
 */
public class LoginResponseDTO {

    // cau thong bao ket qua (da theo ngon ngu da chon)
    private String message;

    // constructor co tham so
    public LoginResponseDTO(String message) {
        this.message = message;
    }

    // hien thi ket qua
    @Override
    public String toString() {
        return message;
    }
}
