package dto;

import constants.Constants;
import java.util.Locale;

/**
 * DTO carrying ONE ROW of the result table FROM the controller OUT TO the view.
 *
 * @author HE176322
 */
public class PersonResponseDTO {

    // Name shown in the first column.
    private String name;

    // Address shown in the second column.
    private String address;

    // Money shown in the last column.
    private double money;

    // Creates an empty row (JavaBean constructor).
    public PersonResponseDTO() {
    }

    // Creates a row with every column filled in.
    public PersonResponseDTO(String name, String address, double money) {
        this.name = name;
        this.address = address;
        this.money = money;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Sets the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Returns the money.
    public double getMoney() {
        return money;
    }

    // Sets the money.
    public void setMoney(double money) {
        this.money = money;
    }

    // One row of the brief's table, "Nghia\t\tHa Noi\t\t1000.0".
    @Override
    public String toString() {
        return String.format(Locale.US, Constants.ROW_FORMAT, name, address, money);
    }
}
