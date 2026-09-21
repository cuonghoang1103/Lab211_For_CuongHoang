package model;

import constants.Constants;

/**
 * MODEL: one pair of words of the dictionary - an English word and its Vietnamese meaning
 * - and nothing else.
 *
 * @author HE176322
 */
public class Word {

    // The English word, as the user typed it.
    private String english;

    // The Vietnamese meaning.
    private String vietnamese;

    // Creates an empty word, to be filled through the setters.
    public Word() {
    }

    // Creates a word with both fields filled in.
    public Word(String english, String vietnamese) {
        this.english = english;
        this.vietnamese = vietnamese;
    }

    // Returns the English word.
    public String getEnglish() {
        return english;
    }

    // Changes the English word.
    public void setEnglish(String english) {
        this.english = english;
    }

    // Returns the Vietnamese meaning.
    public String getVietnamese() {
        return vietnamese;
    }

    // Changes the Vietnamese meaning (used when the user answers Y).
    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    // Polymorphism: overrides Object.toString() to give the word as ONE LINE OF THE DATA
    // FILE, "english=vietnamese".
    @Override
    public String toString() {
        return String.format(Constants.LINE_FORMAT, english, vietnamese);
    }
}
