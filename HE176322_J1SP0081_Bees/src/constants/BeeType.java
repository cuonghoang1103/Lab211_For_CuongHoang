package constants;

/**
 * The three kinds of bee of the brief, in the order the colony lists them (10 Workers,
 * then 10 Queens, then 10 Drones).
 *
 * @author HE176322
 */
public enum BeeType {

    // A worker bee: dead below 70%.
    WORKER,
    // A queen bee: dead below 20%.
    QUEEN,
    // A drone bee: dead below 50%.
    DRONE
}
