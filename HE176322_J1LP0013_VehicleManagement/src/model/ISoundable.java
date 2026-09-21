package model;

/**
 * Something that can be heard. The brief's makeSound arrives through this interface, so
 * the price list asks "can it be heard?" instead of "is it a Motorbike?". The name starts
 * with "I" because the checklist (1.3) wants every interface named that way.
 *
 * @author HE176322
 */
public interface ISoundable {

    // Returns the sound of this vehicle; the view prints it (a model never prints).
    // brief: makeSound "print out the message Tin tin tin" - checklist 1.1 forbids a print
    // in a model, so the name stays and the text is returned for the view.
    String makeSound();
}
