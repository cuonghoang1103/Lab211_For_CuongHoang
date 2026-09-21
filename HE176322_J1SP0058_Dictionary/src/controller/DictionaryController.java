package controller;

import constants.Message;
import dto.WordRequestDTO;
import dto.WordResponseDTO;
import repository.DictionaryRepository;
import view.DictionaryView;

/**
 * CONTROLLER: receives a request DTO from main, asks the repository to do the work, and
 * hands the answer to the view - one render per menu option. No Scanner, no print, no
 * model; a broken rule is thrown as an Exception(Message.X) for main to print.
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

    // The brief's loadData: the lines main read from the data file become the dictionary;
    // main calls it once, when the program starts. Nothing is printed.
    public void loadData(WordRequestDTO requestDTO) {
        dictionaryRepository.loadData(requestDTO);
    }

    // Check only (no render), used by main's add screen: tells whether the English word is
    // already stored, so main knows it must ask "update its meaning (Y/N)?".
    public boolean checkExistWord(WordRequestDTO requestDTO) {
        return dictionaryRepository.isExistWord(requestDTO.getEnglish());
    }

    // Option 1 (addWord): adds a new pair, or replaces the meaning of an existing word when
    // the user answered Y, then the view prints the answer - once.
    public void addWord(WordRequestDTO requestDTO) throws Exception {
        WordResponseDTO responseDTO = new WordResponseDTO();

        // the word exists and the user answered N: keep the old meaning
        if (dictionaryRepository.isExistWord(requestDTO.getEnglish()) &&
                !requestDTO.isOverwrite()) {
            responseDTO.setMessage(Message.NOT_UPDATED);
        } else {
            // addWord returns false only when the file could not be written
            if (!dictionaryRepository.addWord(requestDTO.getEnglish(),
                    requestDTO.getVietnamese())) {
                throw new Exception(Message.CANNOT_WRITE);
            }

            // a new word, or a new meaning after Y
            responseDTO.setMessage(Message.SUCCESS);
        }

        // hand the answer to the view, then render it - once for the whole flow
        dictionaryView.setResponseDTO(responseDTO);
        dictionaryView.display();
    }

    // Option 2 (removeWord): removes the pair of the given English word, then the view
    // prints "Successful" - once.
    public void removeWord(WordRequestDTO requestDTO) throws Exception {
        WordResponseDTO responseDTO = new WordResponseDTO();

        // the brief: "If not found, the notification does not exist"
        if (!dictionaryRepository.isExistWord(requestDTO.getEnglish())) {
            throw new Exception(Message.KEY_NOT_EXIST);
        }

        // the word exists, so false can only mean the file was not written
        if (!dictionaryRepository.removeWord(requestDTO.getEnglish())) {
            throw new Exception(Message.CANNOT_WRITE);
        }

        // removed: hand the answer to the view, then render it - once for the whole flow
        responseDTO.setMessage(Message.SUCCESS);
        dictionaryView.setResponseDTO(responseDTO);
        dictionaryView.display();
    }

    // Option 3 (translate): finds the meaning, then the view prints it - once.
    public void translate(WordRequestDTO requestDTO) {
        WordResponseDTO responseDTO = new WordResponseDTO();
        String vietnamese = dictionaryRepository.translate(requestDTO.getEnglish());

        // the brief: "If not found, display empty"
        if (vietnamese == null) {
            responseDTO.setMessage(Message.TRANSLATE_EMPTY);
        } else {
            // found: the view prints "Vietnamese: <meaning>"
            responseDTO.setVietnamese(vietnamese);
        }

        // hand the answer to the view, then render it - once for the whole flow
        dictionaryView.setResponseDTO(responseDTO);
        dictionaryView.display();
    }
}
