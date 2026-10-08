package model;

public class Contact {

    // Ma contact, tu tang (ID cuoi + 1)
    private int id;

    // Ten day du nguoi dung nhap
    private String fullName;

    // Phan ten truoc dau cach dau tien
    private String firstName;

    // Phan ten sau dau cach dau tien
    private String lastName;

    // Nhom cua contact
    private String group;

    // Dia chi cua contact
    private String address;

    // So dien thoai (1 trong 7 dang cua de)
    private String phone;

    // Constructor co tham so: tu tach firstName, lastName tu fullName
    public Contact(int id, String fullName, String group, String address, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.group = group;
        this.address = address;
        this.phone = phone;

        // Tim vi tri dau cach dau tien trong ten
        int spaceIndex = fullName.indexOf(' ');

        // Ten chi co 1 chu: firstName la ca ten, lastName de trong
        if (spaceIndex < 0) {
            this.firstName = fullName;
            this.lastName = "";
        } else {
            // Ten co dau cach: cat o dau cach dau tien
            this.firstName = fullName.substring(0, spaceIndex);
            this.lastName = fullName.substring(spaceIndex + 1).trim();
        }
    }

    // Lay ma contact
    public int getId() {
        return id;
    }

    // Lay ten day du
    public String getFullName() {
        return fullName;
    }

    // Lay first name
    public String getFirstName() {
        return firstName;
    }

    // Lay last name
    public String getLastName() {
        return lastName;
    }

    // Lay nhom
    public String getGroup() {
        return group;
    }

    // Lay dia chi
    public String getAddress() {
        return address;
    }

    // Lay so dien thoai
    public String getPhone() {
        return phone;
    }
}
