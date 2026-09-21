package service;

/**
 * STRATEGY (design pattern): the common contract of every normalization rule of the
 * brief.
 *
 * @author HE176322
 */
public interface INormalizeRule {

    // Applies this rule to the text and returns the new text.
    String apply(String text);
}
