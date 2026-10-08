package model;

import constants.Base;

public class BaseNumber {

    // Gia tri nguoi dung nhap (dang chuoi, vd "1B")
    private String value;

    // He co so cua gia tri
    private Base base;

    // Constructor day du thuoc tinh
    public BaseNumber(String value, Base base) {
        this.value = value;
        this.base = base;
    }

    // Lay gia tri
    public String getValue() {
        return value;
    }

    // Lay he co so
    public Base getBase() {
        return base;
    }
}
