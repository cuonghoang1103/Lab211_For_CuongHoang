package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- menu -----
    // Menu option: exit; also the smallest option.
    public static final int MENU_EXIT = 0;
    // Menu option: create a new bee list.
    public static final int MENU_CREATE = 1;
    // Menu option: attack the bees; also the largest option.
    public static final int MENU_ATTACK = 2;

    // ----- the colony (brief) -----
    // 10 instances of each type, 30 bees in total.
    public static final int BEES_PER_TYPE = 10;
    // Health of a new bee: 100 (percent).
    public static final double FULL_HEALTH = 100.0;
    // Smallest percent Damage() accepts.
    public static final int MIN_PERCENT = 0;
    // Largest percent Damage() accepts; also "all of it" in the formula.
    public static final int MAX_PERCENT = 100;
    // Divisor that turns a percent into a fraction; a double on purpose.
    public static final double PERCENT_BASE = 100.0;
    // Largest random damage of one attack: a value in [0, 80].
    public static final int MAX_DAMAGE = 80;
    // A worker is dead below 70% (it can no longer fly).
    public static final int WORKER_THRESHOLD = 70;
    // A queen is dead below 20%.
    public static final int QUEEN_THRESHOLD = 20;
    // A drone is dead below 50%.
    public static final int DRONE_THRESHOLD = 50;

    // ----- table layout, measured on the brief's sample output -----
    // Health with two decimals and a percent sign.
    public static final String HEALTH_FORMAT = "%.2f %%";
    // Colony table header / row: no, type, health, status.
    public static final String COLONY_FORMAT = "%s  %-8s%-11s%s";
    // Attack table header / row: no, type, damage, health, status.
    public static final String ATTACK_FORMAT = "%s  %-9s%3s   %-11s%s";
    // Short text of one bee: type and health (Bee.toString).
    public static final String BEE_TEXT = "%s %s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
