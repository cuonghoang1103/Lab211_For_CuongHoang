package dto;

public class ContactRequestDTO {

    // Ten day du nguoi dung nhap
    private String fullName;

    // Nhom nguoi dung nhap
    private String group;

    // Dia chi nguoi dung nhap
    private String address;

    // So dien thoai nguoi dung nhap (da dung dinh dang)
    private String phone;

    // Constructor rong: Main dien du lieu qua setter
    public ContactRequestDTO() {
    }

    // Lay ten day du
    public String getFullName() {
        return fullName;
    }

    // Gan ten day du
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Lay nhom
    public String getGroup() {
        return group;
    }

    // Gan nhom
    public void setGroup(String group) {
        this.group = group;
    }

    // Lay dia chi
    public String getAddress() {
        return address;
    }

    // Gan dia chi
    public void setAddress(String address) {
        this.address = address;
    }

    // Lay so dien thoai
    public String getPhone() {
        return phone;
    }

    // Gan so dien thoai
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
