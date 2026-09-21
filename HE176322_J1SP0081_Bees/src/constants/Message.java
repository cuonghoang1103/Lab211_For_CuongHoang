package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title, options and closing line of the menu (brief).
    public static final String MENU = "============ BEE SIMULATION ============\n"
            + "1. Create bee list\n"
            + "2. Attack bees\n"
            + "0. Exit\n"
            + "========================================";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- validation errors -----
    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // ----- business errors -----
    // Option 2 before any bee list was created.
    public static final String NO_BEE_LIST = "There is no bee list yet. Choose 1 first.";

    // damage() (the brief's Damage()) called with a percent outside 0..100; %d are the
    // bounds.
    public static final String INVALID_DAMAGE = "Damage percent must be between %d and %d.";

    // A BeeType was added without a case in BeeFactory; %s is the type.
    public static final String UNKNOWN_BEE = "Unknown bee type: %s";

    // ----- results (brief) -----
    // Line before the new colony; %d are the counts of each type.
    public static final String CREATED
            = "New bee list created: %d Workers, %d Queens, %d Drones.";

    // Line before the attack report; %d are the damage bounds.
    public static final String ATTACKING
            = "Attacking all bees (random damage %d-%d%% each)...";

    // Summary under both tables; %d are the counts.
    public static final String SUMMARY = "Alive: %d   Dead: %d";

    // Separator of the colony table (30 dashes, as in the brief).
    public static final String LINE_COLONY = "------------------------------";

    // Separator of the attack table (37 dashes, as in the brief).
    public static final String LINE_ATTACK = "-------------------------------------";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // ----- table labels -----
    // Header label of the number column.
    public static final String LABEL_NO = "No";

    // Header label of the type column.
    public static final String LABEL_TYPE = "Type";

    // Header label of the damage column.
    public static final String LABEL_DAMAGE = "Dmg";

    // Header label of the health column.
    public static final String LABEL_HEALTH = "Health";

    // Header label of the status column.
    public static final String LABEL_STATUS = "Status";

    // Status of a living bee.
    public static final String STATUS_ALIVE = "Alive";

    // Status of a dead bee.
    public static final String STATUS_DEAD = "Dead";

    // ----- bee types -----
    // Name of a worker bee.
    public static final String TYPE_WORKER = "Worker";

    // Name of a queen bee.
    public static final String TYPE_QUEEN = "Queen";

    // Name of a drone bee.
    public static final String TYPE_DRONE = "Drone";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
