package model;

import java.util.Date;

/**
 * Tai khoan nguoi dung.
 *
 * @author HE176322
 */
public class Account {

    // ma tu tang
    private int id;

    // ten dang nhap
    private String username;

    // mat khau da bam MD5
    private String password;

    // ho ten
    private String name;

    // so dien thoai
    private String phone;

    // email
    private String email;

    // dia chi
    private String address;

    // ngay sinh
    private Date dob;

    // constructor co tham so
    public Account(int id, String username, String password, String name,
            String phone, String email, String address, Date dob) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.dob = dob;
    }

    // getter id
    public int getId() {
        return id;
    }

    // getter username
    public String getUsername() {
        return username;
    }

    // getter mat khau da bam
    public String getPassword() {
        return password;
    }

    // setter mat khau da bam (dung khi doi mat khau)
    public void setPassword(String password) {
        this.password = password;
    }

    // getter ho ten
    public String getName() {
        return name;
    }

    // getter so dien thoai
    public String getPhone() {
        return phone;
    }

    // getter email
    public String getEmail() {
        return email;
    }

    // getter dia chi
    public String getAddress() {
        return address;
    }

    // getter ngay sinh
    public Date getDob() {
        return dob;
    }
}
