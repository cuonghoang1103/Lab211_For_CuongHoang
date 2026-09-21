package dto;

/**
 * DTO carrying ONE row of a bee table FROM the controller OUT TO the view (inside
 * ColonyResponseDTO).
 *
 * @author HE176322
 */
public class BeeResponseDTO {

    // Row number, from 1.
    private int no;

    // "Worker", "Queen" or "Drone".
    private String type;

    // Damage rolled for this bee in the last attack (attack table only).
    private int damage;

    // Health in percent after the operation.
    private double health;

    // True when the bee is dead.
    private boolean dead;

    // JavaBean constructor: an empty row, filled through the setters.
    public BeeResponseDTO() {
    }

    // Returns the row number.
    public int getNo() {
        return no;
    }

    // Sets the row number.
    public void setNo(int no) {
        this.no = no;
    }

    // Returns the bee type.
    public String getType() {
        return type;
    }

    // Sets the bee type.
    public void setType(String type) {
        this.type = type;
    }

    // Returns the damage rolled in the last attack.
    public int getDamage() {
        return damage;
    }

    // Sets the damage rolled in the last attack.
    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Returns the health.
    public double getHealth() {
        return health;
    }

    // Sets the health.
    public void setHealth(double health) {
        this.health = health;
    }

    // Tells whether the bee is dead ("is" getter: JavaBean naming for a boolean).
    public boolean isDead() {
        return dead;
    }

    // Sets whether the bee is dead.
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
