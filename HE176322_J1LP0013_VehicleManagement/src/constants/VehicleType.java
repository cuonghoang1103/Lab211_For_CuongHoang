package constants;

/**
 * The kinds of vehicle of the show room: the word saved in the file, the word shown on
 * screen and the option of the add menu.
 *
 * @author HE176322
 */
public enum VehicleType {

    // A car: add menu option 1.
    CAR("CAR", "Car", 1),

    // A motorbike: add menu option 2.
    MOTORBIKE("MOTORBIKE", "Motorbike", 2);

    // Word in the first column of vehicles.txt.
    private final String code;

    // Word in the Kind column of the table.
    private final String label;

    // Option of the add menu that creates this kind.
    private final int menuChoice;

    // Creates one constant; enum constructors are always private.
    VehicleType(String code, String label, int menuChoice) {
        this.code = code;
        this.label = label;
        this.menuChoice = menuChoice;
    }

    // Returns the word saved in the file.
    public String getCode() {
        return code;
    }

    // Returns the word shown on screen.
    public String getLabel() {
        return label;
    }

    // Finds the kind by the word of the file, ignoring case; null when unknown.
    public static VehicleType findByCode(String fileCode) {
        // compare with each kind once
        for (VehicleType type : values()) {
            // same word, whatever the case
            if (type.code.equalsIgnoreCase(fileCode)) {
                return type;
            }
        }

        return null;
    }

    // Finds the kind created by an option of the add menu; null when none.
    public static VehicleType findByMenuChoice(int choice) {
        // compare with each kind once
        for (VehicleType type : values()) {
            // this kind is created by that option
            if (type.menuChoice == choice) {
                return type;
            }
        }

        return null;
    }
}
