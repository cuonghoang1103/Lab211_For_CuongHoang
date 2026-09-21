package model;

/**
 * Anything that is one line of a .dat file and is found again by its id: this is what
 * lets one generic FileRepository serve four files. An interface, so its name starts
 * with "I" (checklist 1.3).
 *
 * @author HE176322
 */
public interface IRecord {

    // The primary key: A001, E160052, R001, B001.
    String getId();
}
