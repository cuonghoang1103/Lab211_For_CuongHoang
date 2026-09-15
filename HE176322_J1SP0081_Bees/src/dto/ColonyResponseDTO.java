package dto;

import java.util.ArrayList;

/**
 * DTO carrying a whole bee table FROM the controller OUT TO the view: the rows and the
 * "Alive / Dead" summary.
 *
 * @author HE176322
 */
public class ColonyResponseDTO {

    // One row per bee, in colony order.
    private ArrayList<BeeResponseDTO> bees;
    // How many bees are alive.
    private int aliveCount;
    // How many bees are dead.
    private int deadCount;

    // JavaBean constructor: an empty table, filled through the setters.
    public ColonyResponseDTO() {
        bees = new ArrayList<>();
    }

    // Returns the rows.
    public ArrayList<BeeResponseDTO> getBees() {
        return bees;
    }

    // Sets the rows.
    public void setBees(ArrayList<BeeResponseDTO> bees) {
        this.bees = bees;
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
