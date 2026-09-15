package dto;

/**
 * DTO controller -> view: who has just logged in.
 *
 * @author HE176322
 */
public class LoginResponseDTO {

    // Full name.
    private String name;
    // Employee or Manager.
    private String title;

    // JavaBean constructor.
    public LoginResponseDTO() {
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Changes the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the title.
    public String getTitle() {
        return title;
    }

    // Changes the title.
    public void setTitle(String title) {
        this.title = title;
    }
}
