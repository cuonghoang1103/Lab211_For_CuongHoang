package model;

/**
 * Something that can be heard. The brief's makeSound arrives through this interface, so
 * the price list asks "can it be heard?" instead of "is it a Motorbike?".
 *
 * @author HE176322
 */
public interface Soundable {

    // Returns the sound of this vehicle; the view prints it (a model never prints).
    String makeSound();
}
