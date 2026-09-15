package view;

import constants.Message;
import dto.WordResponseDTO;

/**
 * VIEW: the only place (with main) allowed to print results.
 *
 * @author HE176322
 */
public class DictionaryView {

    // The translation to display, handed over by the controller.
    private WordResponseDTO word;

    // Receives the translation the next display() call will print.
    public void setWord(WordResponseDTO word) {
        this.word = word;
    }

    // Prints the translation: "Vietnamese: Con Meo" (the brief's screen), or the "empty"
    // line when the word is not in the dictionary.
    public void display() {
        // the brief: "If not found, display empty"
        if (word == null || !word.isFound()) {
            System.out.println(Message.TRANSLATE_EMPTY);
            return;
        }
        System.out.println(Message.LABEL_VIETNAMESE + word.getVietnamese());
    }

    // Prints a one-line result such as "Successful".
    public void showMessage(String message) {
        System.out.println(message);
    }
}
