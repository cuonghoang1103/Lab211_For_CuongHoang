package dto;

import java.util.Date;

/**
 * Du lieu Main gui vao Controller (mat khau da bam MD5).
 *
 * @author HE176322
 */
public class AccountRequestDTO {

    // ten dang nhap
    private String username;

    // mat khau da bam
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

    // mat khau cu da bam (man doi mat khau)
    private String oldPassword;

    // mat khau moi da bam (man doi mat khau)
    private String newPassword;

    // lay ten dang nhap
    public String getUsername() {
        return username;
    }

    // gan ten dang nhap
    public void setUsername(String username) {
        this.username = username;
    }

    // lay mat khau da bam
    public String getPassword() {
        return password;
    }

    // gan mat khau da bam
    public void setPassword(String password) {
        this.password = password;
    }

    // lay ho ten
    public String getName() {
        return name;
    }

    // gan ho ten
    public void setName(String name) {
        this.name = name;
    }

    // lay so dien thoai
    public String getPhone() {
        return phone;
    }

    // gan so dien thoai
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // lay email
    public String getEmail() {
        return email;
    }

    // gan email
    public void setEmail(String email) {
        this.email = email;
    }

    // lay dia chi
    public String getAddress() {
        return address;
    }

    // gan dia chi
    public void setAddress(String address) {
        this.address = address;
    }

    // lay ngay sinh
    public Date getDob() {
        return dob;
    }

    // gan ngay sinh
    public void setDob(Date dob) {
        this.dob = dob;
    }

    // lay mat khau cu da bam
    public String getOldPassword() {
        return oldPassword;
    }

    // gan mat khau cu da bam
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    // lay mat khau moi da bam
    public String getNewPassword() {
        return newPassword;
    }

    // gan mat khau moi da bam
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
