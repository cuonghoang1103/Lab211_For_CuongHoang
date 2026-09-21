package dto;

import java.util.ArrayList;

/**
 * DTO carrying the answer of one function FROM the controller OUT TO the view: the rows of
 * "Find person info" with the names of the persons with the most and the least money, or
 * the one line of "Copy text".
 *
 * @author HE176322
 */
public class FileResponseDTO {

    // The one line to print ("Copy done..."); null when the answer is the result table.
    private String message;

    // The rows, sorted from the least money to the most; null when the answer is a line.
    private ArrayList<PersonResponseDTO> personList;

    // Name of the person with the most money; null when the list is empty.
    private String maxName;

    // Name of the person with the least money; null when the list is empty.
    private String minName;

    // Creates an empty answer (JavaBean constructor); the service fills it.
    public FileResponseDTO() {
    }

    // Returns the line to print.
    public String getMessage() {
        return message;
    }

    // Sets the line to print.
    public void setMessage(String message) {
        this.message = message;
    }

    // Returns the rows.
    public ArrayList<PersonResponseDTO> getPersonList() {
        return personList;
    }

    // Sets the rows.
    public void setPersonList(ArrayList<PersonResponseDTO> personList) {
        this.personList = personList;
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
