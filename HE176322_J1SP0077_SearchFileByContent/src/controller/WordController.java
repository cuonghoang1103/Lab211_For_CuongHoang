package controller;

import dto.WordRequestDTO;
import dto.WordResponseDTO;
import java.util.ArrayList;
import service.WholeWordMatcher;
import service.WordService;
import view.WordView;

/**
 * CONTROLLER (and Facade): receives the request from main, calls the brief's method in
 * the service, and hands the result to the view.
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

    // Option 1: counts the word in a file.
    public void countWord(WordRequestDTO requestDTO) throws Exception {
        WordResponseDTO response = new WordResponseDTO();
        response.setCount(wordService.countWordInFile(requestDTO.getPath(),
                requestDTO.getWord()));
        wordView.setResponse(response);
        wordView.displayCount();
    }

    // Option 2: lists the files of a folder that contain the word.
    public void findFile(WordRequestDTO requestDTO) throws Exception {
        WordResponseDTO response = new WordResponseDTO();
        response.setFileNames(new ArrayList<>(wordService
                .getFileNameContainsWordInDirectory(requestDTO.getPath(),
                        requestDTO.getWord())));
        wordView.setResponse(response);
        wordView.displayFileNames();
    }
}
