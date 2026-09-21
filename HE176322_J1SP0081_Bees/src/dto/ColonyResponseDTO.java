package dto;

import java.util.ArrayList;

/**
 * DTO carrying a whole bee table FROM the controller OUT TO the view: the line of the
 * function, the rows and the "Alive / Dead" summary.
 *
 * @author HE176322
 */
public class ColonyResponseDTO {

    // The line above the table: "New bee list created: ..." or "Attacking all bees ...".
    private String message;

    // True for the attack table (it has the Dmg column), false for a new colony.
    private boolean attack;

    // One row per bee, in colony order.
    private ArrayList<BeeResponseDTO> rowList;

    // How many bees are alive.
    private int aliveCount;

    // How many bees are dead.
    private int deadCount;

    // JavaBean constructor: an empty table, filled through the setters.
    public ColonyResponseDTO() {
        rowList = new ArrayList<>();
    }

    // Returns the line above the table.
    public String getMessage() {
        return message;
    }

    // Sets the line above the table.
    public void setMessage(String message) {
        this.message = message;
    }

    // Tells whether this is the attack table ("is" getter: JavaBean naming for a boolean).
    public boolean isAttack() {
        return attack;
    }

    // Sets whether this is the attack table.
    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    // Returns the rows.
    public ArrayList<BeeResponseDTO> getRowList() {
        return rowList;
    }

    // Sets the rows.
    public void setRowList(ArrayList<BeeResponseDTO> rowList) {
        this.rowList = rowList;
    }

    // Returns the number of living bees.
    public int getAliveCount() {
        return aliveCount;
    }

    // Sets the number of living bees.
    public void setAliveCount(int aliveCount) {
        this.aliveCount = aliveCount;
    }

    // Returns the number of dead bees.
    public int getDeadCount() {
        return deadCount;
    }

    // Sets the number of dead bees.
    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }
}
