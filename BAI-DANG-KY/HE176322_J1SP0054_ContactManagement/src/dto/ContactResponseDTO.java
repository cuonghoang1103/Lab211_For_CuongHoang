package dto;

public class ContactResponseDTO {

    // Ma contact
    private int id;

    // Ten day du
    private String fullName;

    // First name
    private String firstName;

    // Last name
    private String lastName;

    // Nhom
    private String group;

    // Dia chi
    private String address;

    // So dien thoai
    private String phone;

    // Constructor co tham so: repository dien du lieu tu model
    public ContactResponseDTO(int id, String fullName, String firstName, String lastName,
            String group, String address, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.address = address;
        this.phone = phone;
    }

    // Tra ve 1 dong cua bang Display theo cot co dinh
    @Override
    public String toString() {
        return String.format("%-4d%-18s%-12s%-12s%-8s%-12s%s",
                id, fullName, firstName, lastName, group, address, phone);
    }
}
