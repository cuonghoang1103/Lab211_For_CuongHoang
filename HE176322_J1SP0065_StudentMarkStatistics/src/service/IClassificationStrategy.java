package service;

/**
 * STRATEGY (design pattern): the contract of a rule that turns an average into a student
 * type.
 *
 * @author HE176322
 */
public interface IClassificationStrategy {

    // Classifies one average.
    String classify(double average);
}
