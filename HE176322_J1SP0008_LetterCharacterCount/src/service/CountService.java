package service;

import constants.Constants;
import dto.CountRequestDTO;
import dto.CountResponseDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Content;
import repository.ContentRepository;

/**
 * SERVICE: counts the words and the characters of the content. It keeps the content in
 * the repository and counts what the repository holds. Called only by the controller; no
 * print, no keyboard.
 *
 * @author HE176322
 */
public class CountService {

    // Keeps the content the service counts (Service -> Repository -> Model).
    private ContentRepository contentRepository;

    // Creates the service with an empty repository.
    public CountService() {
        contentRepository = new ContentRepository();
    }

    // Words first, then characters - the two lines of the brief's screen.
    public CountResponseDTO countContent(CountRequestDTO requestDTO) {
        CountResponseDTO responseDTO = new CountResponseDTO();
        Content content = null;

        // keep the typed text in the repository, then work on the content it holds
        contentRepository.saveContent(requestDTO.getContent());
        content = contentRepository.getContent();

        // line 1: every word and its count; line 2: every character and its count
        responseDTO.addResult(countUnits(content.getWords()).toString());
        responseDTO.addResult(countUnits(splitCharacters(content)).toString());
        return responseDTO;
    }

    // Tallies the units in the order they first appear: unit -> how many times.
    private LinkedHashMap<String, Integer> countUnits(ArrayList<String> unitList) {
        LinkedHashMap<String, Integer> countMap = new LinkedHashMap<>();

        // count the units one by one
        for (String unit : unitList) {
            // seen before: one more
            if (countMap.containsKey(unit)) {
                countMap.put(unit, countMap.get(unit) + 1);
            } else {
                // first time this unit appears
                countMap.put(unit, Constants.FIRST_COUNT);
            }
        }

        return countMap;
    }

    // Every character of every word (spaces were removed by the tokenizer).
    private ArrayList<String> splitCharacters(Content content) {
        ArrayList<String> characterList = new ArrayList<>();

        // take the words in order
        for (String word : content.getWords()) {
            // every character of this word, left to right
            for (int i = 0; i < word.length(); i++) {
                characterList.add(String.valueOf(word.charAt(i)));
            }
        }

        return characterList;
    }
}
