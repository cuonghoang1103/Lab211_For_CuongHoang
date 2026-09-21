package dto;

/**
 * DTO carrying the result FROM the controller OUT TO the view: the persons sorted by
 * salary ascending, as rows the view is allowed to see.
 *
 * @author HE176322
 */
public class PersonResponseDTO {

    // The sorted persons, lowest salary first.
    private PersonDTO[] personArray;

    // Creates an empty response (JavaBean constructor); filled through the setter.
    public PersonResponseDTO() {
        personArray = new PersonDTO[0];
    }

    // Returns the sorted persons.
    public PersonDTO[] getPersonArray() {
        return personArray;
    }

    // Sets the sorted persons.
    public void setPersonArray(PersonDTO[] personArray) {
        this.personArray = personArray;
    }
}
