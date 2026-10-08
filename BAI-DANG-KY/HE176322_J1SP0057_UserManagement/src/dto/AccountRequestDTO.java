package dto;

public class AccountRequestDTO {

    // Ten dang nhap nguoi dung nhap
    private String username;

    // Mat khau nguoi dung nhap
    private String password;

    // Constructor rong: Main dien du lieu qua setter
    public AccountRequestDTO() {
    }

    // Lay ten dang nhap
    public String getUsername() {
        return username;
    }

    // Gan ten dang nhap
    public void setUsername(String username) {
        this.username = username;
    }

    // Lay mat khau
    public String getPassword() {
        return password;
    }

    // Gan mat khau
    public void setPassword(String password) {
        this.password = password;
    }
}
