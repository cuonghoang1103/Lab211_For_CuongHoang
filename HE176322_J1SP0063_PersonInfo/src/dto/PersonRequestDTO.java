package dto;

/**
 * DTO carrying what the user typed FROM main INTO the controller: the brief's array of 3
 * persons, in the order they were typed.
 *
 * @author HE176322
 */
public class PersonRequestDTO {

    // The persons typed by the user, first typed first.
    private PersonDTO[] personArray;

    // Creates an empty request; main fills it through the setter.
    public PersonRequestDTO() {
        personArray = new PersonDTO[0];
    }

    // Returns the persons typed.
    public PersonDTO[] getPersonArray() {
        return personArray;
    }

    // Sets the persons typed.
    public void setPersonArray(PersonDTO[] personArray) {
        this.personArray = personArray;
    }
}
