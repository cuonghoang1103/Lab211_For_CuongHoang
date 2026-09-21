package dto;

/**
 * DTO service -> controller: who has just logged in (a copy of a Person, so the
 * controller never touches the model).
 *
 * @author HE176322
 */
public class PersonDTO {

    // Full name.
    private String name;

    // Employee or Manager.
    private String title;

    // JavaBean constructor.
    public PersonDTO() {
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
