package repository;

/**
 * INTERFACE (DIP): the whole store of candidates - the write side (ICandidateSaver) and
 * the read side (ICandidateFinder) together. Only the controller, which wires the program,
 * holds the store whole; each service sees just the half it needs (ISP).
 *
 * @author HE176322
 */
public interface ICandidateRepository extends ICandidateSaver, ICandidateFinder {
}
