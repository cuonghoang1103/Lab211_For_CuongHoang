package controller;

import constants.Message;
import dto.WordRequestDTO;
import dto.WordResponseDTO;
import java.util.ArrayList;
import service.WholeWordMatcher;
import service.WordService;
import view.WordView;

/**
 * CONTROLLER (and Facade): receives the request from main, calls the brief's method in
 * the service, and hands the result to the view ONCE per flow. No Scanner, no print, no
 * model.
 *
 * @author HE176322
 */
public class WordController {

    // Counts and searches; configured with the whole-word strategy.
    private WordService wordService;

    // Prints the results.
    private WordView wordView;

    // Creates the controller: the service gets WholeWordMatcher as its rule.
    public WordController() {
        wordService = new WordService(new WholeWordMatcher());
        wordView = new WordView();
    }

    // Option 1: keeps the file main read, then counts the word in it.
    public void countWord(WordRequestDTO requestDTO) throws Exception {
        WordResponseDTO responseDTO = new WordResponseDTO();

        // the service keeps the lines main read, then counts the word in that file
        wordService.addTextFile(requestDTO);
        responseDTO.setMessage(String.format(Message.COUNT_RESULT,
                wordService.countWordInFile(requestDTO.getPath(), requestDTO.getWord())));

        // hand the result to the view, then render it - once for the whole flow
        wordView.setResponseDTO(responseDTO);
        wordView.display();
    }

    // Option 2: keeps the files main read from the folder, then lists those that contain
    // the word.
    public void findFile(WordRequestDTO requestDTO) throws Exception {
        WordResponseDTO responseDTO = new WordResponseDTO();
        ArrayList<String> fileNameList = new ArrayList<>();

        // the service keeps the folder's files, then searches them for the word
        wordService.addFolder(requestDTO);
        fileNameList.addAll(wordService.getFileNameContainsWordInDirectory(requestDTO.getPath(),
                requestDTO.getWord()));
        responseDTO.setFileNameList(fileNameList);

        // hand the result to the view, then render it - once for the whole flow
        wordView.setResponseDTO(responseDTO);
        wordView.display();
    }
}
