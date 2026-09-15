package model;

/**
 * Anything that is one line of a .dat file and is found again by its id: this is what
 * lets one generic FileRepository serve four files.
 *
 * @author HE176322
 */
public interface Identifiable {

    // The primary key: A001, E160052, R001, B001.
    String getId();
}
