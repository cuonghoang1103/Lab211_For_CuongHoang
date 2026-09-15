package service;

import constants.Constants;
import dto.CountRequestDTO;
import dto.CountResponseDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Content;

/**
 * Service: counts the words and the characters of the content.
 *
 * @author HE176322
 */
public class CountService {

    // words first, then characters - the two lines of the brief's screen
    public CountResponseDTO countContent(CountRequestDTO requestDTO) {
        Content content = new Content(requestDTO.getContent());
        CountResponseDTO response = new CountResponseDTO();
        response.addResult(countUnits(content.getWords()).toString());
        response.addResult(countUnits(splitCharacters(content)).toString());
        return response;
    }

    // tally the units in the order they first appear: unit -> how many times
    private LinkedHashMap<String, Integer> countUnits(ArrayList<String> units) {
        LinkedHashMap<String, Integer> counts = new LinkedHashMap<>();
        // count the units one by one
        for (String unit : units) {
            // seen before: one more
            if (counts.containsKey(unit)) {
                counts.put(unit, counts.get(unit) + 1);
            } else {
                // first time this unit appears
                counts.put(unit, Constants.FIRST_COUNT);
            }
        }
        return counts;
    }

    // every character of every word (spaces were removed by the tokenizer)
    private ArrayList<String> splitCharacters(Content content) {
        ArrayList<String> characters = new ArrayList<>();
        // take the words in order
        for (String word : content.getWords()) {
            // every character of this word, left to right
            for (int i = 0; i < word.length(); i++) {
                characters.add(String.valueOf(word.charAt(i)));
            }
        }
        return characters;
    }
}
