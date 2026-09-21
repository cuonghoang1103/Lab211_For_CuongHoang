package model;

import constants.Constants;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * MODEL: the content the user typed - a JavaBean (private field, public no-argument
 * constructor, getter/setter), as in MVC of JSP. It only describes the content: no print,
 * no input.
 *
 * @author HE176322
 */
public class Content {

    // The text exactly as the user typed it.
    private String text;

    // JavaBean constructor: an empty content, to be filled with setText.
    public Content() {
        text = "";
    }

    // Wraps a text.
    public Content(String text) {
        this.text = text;
    }

    // Returns the text.
    public String getText() {
        return text;
    }

    // Replaces the text.
    public void setText(String text) {
        this.text = text;
    }

    // Splits the text into words with StringTokenizer (the brief's Guidelines).
    public ArrayList<String> getWords() {
        ArrayList<String> wordList = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(text, Constants.WORD_DELIMITERS);

        // take the words one by one until the text is used up
        while (tokenizer.hasMoreTokens()) {
            wordList.add(tokenizer.nextToken());
        }

        return wordList;
    }

    // Polymorphism: overrides Object.toString() to give the text itself.
    @Override
    public String toString() {
        return text;
    }
}
