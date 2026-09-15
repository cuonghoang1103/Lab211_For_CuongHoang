package controller;

import constants.Message;
import dto.WordRequestDTO;
import dto.WordResponseDTO;
import repository.DictionaryRepository;
import view.DictionaryView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the result to the view.
 *
 * @author HE176322
 */
public class DictionaryController {

    // Where the words are stored; the controller owns its repository.
    private DictionaryRepository dictionaryRepository;
    // Where the results are printed.
    private DictionaryView dictionaryView;

    // Creates the controller together with its repository and view.
    public DictionaryController() {
        dictionaryRepository = new DictionaryRepository();
        dictionaryView = new DictionaryView();
    }

    // Loads the data file into the dictionary; main calls it once, when the program
    // starts (the brief: "every turn on the program ...
    public void loadData() throws Exception {
        dictionaryRepository.loadData();
    }

    // Pre-check used by main's add screen: tells whether the English word is already
    // stored, so main knows it must ask "update its meaning (Y/N)?".
    public boolean checkExistWord(WordRequestDTO requestDTO) {
        return dictionaryRepository.isExistWord(requestDTO.getEnglish());
    }

    // Option 1 (addWord): adds a new pair, or replaces the meaning of an existing word
    // when the user answered Y.
    public void addWord(WordRequestDTO requestDTO) throws Exception {
        // the word exists and the user answered N: keep the old meaning
        if (dictionaryRepository.isExistWord(requestDTO.getEnglish())
                && !requestDTO.isOverwrite()) {
            dictionaryView.showMessage(Message.NOT_UPDATED);
            return;
        }
        // addWord returns false only when the file could not be written
        if (!dictionaryRepository.addWord(requestDTO.getEnglish(),
                requestDTO.getVietnamese())) {
            throw new Exception(Message.CANNOT_WRITE);
        }
        dictionaryView.showMessage(Message.SUCCESS);
    }

    // Option 2 (removeWord): removes the pair of the given English word.
    public void removeWord(WordRequestDTO requestDTO) throws Exception {
        // the brief: "If not found, the notification does not exist"
        if (!dictionaryRepository.isExistWord(requestDTO.getEnglish())) {
            throw new Exception(Message.KEY_NOT_EXIST);
        }
        // the word exists, so false can only mean the file was not written
        if (!dictionaryRepository.removeWord(requestDTO.getEnglish())) {
            throw new Exception(Message.CANNOT_WRITE);
        }
        dictionaryView.showMessage(Message.SUCCESS);
    }

    // Option 3 (translate): finds the meaning and lets the view print it.
    public void translate(WordRequestDTO requestDTO) {
        String vietnamese = dictionaryRepository.translate(requestDTO.getEnglish());
        WordResponseDTO response = new WordResponseDTO(requestDTO.getEnglish(),
                vietnamese);
        dictionaryView.setWord(response);
        dictionaryView.display();
    }
}
