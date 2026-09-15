package dto;

import java.util.ArrayList;

/**
 * DTO carrying the whole result of "Find person info" FROM the controller OUT TO the
 * view: the rows of the table, and the names of the persons with the most and the least
 * money.
 *
 * @author HE176322
 */
public class ReportResponseDTO {

    // The rows, sorted from the least money to the most.
    private ArrayList<PersonResponseDTO> persons = new ArrayList<>();
    // Name of the person with the most money; null when the list is empty.
    private String maxName;
    // Name of the person with the least money; null when the list is empty.
    private String minName;

    // Creates an empty report (JavaBean constructor).
    public ReportResponseDTO() {
    }

    // Returns the rows.
    public ArrayList<PersonResponseDTO> getPersons() {
        return persons;
    }

    // Sets the rows.
    public void setPersons(ArrayList<PersonResponseDTO> persons) {
        this.persons = persons;
    }

    // Returns the name of the person with the most money.
    public String getMaxName() {
        return maxName;
    }

    // Sets the name of the person with the most money.
    public void setMaxName(String maxName) {
        this.maxName = maxName;
    }

    // Returns the name of the person with the least money.
    public String getMinName() {
        return minName;
    }

    // Sets the name of the person with the least money.
    public void setMinName(String minName) {
        this.minName = minName;
    }
}
