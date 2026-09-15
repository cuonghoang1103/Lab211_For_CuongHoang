package service;

/**
 * STRATEGY (design pattern): the common contract of every normalization rule of the
 * brief.
 *
 * @author HE176322
 */
public interface NormalizeRule {

    // Applies this rule to the text.
    String apply(String text);
}
