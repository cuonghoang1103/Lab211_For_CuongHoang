package repository;

import constants.Constants;
import dto.WordRequestDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Word;
import utils.FileUtils;

/**
 * REPOSITORY: holds the dictionary and performs simple CRUD on it, keeping the data file
 * equal to what is in memory. No rule of the screen, no print, no keyboard.
 *
 * @author HE176322
 */
public class DictionaryRepository {

    // The dictionary: lower-case English word -> the pair of words.
    private LinkedHashMap<String, Word> wordMap = new LinkedHashMap<>();

    // Creates an empty dictionary; loadData() fills it from the lines of the file.
    public DictionaryRepository() {
    }

    // The brief's loadData(): every "english=vietnamese" line main read from the data file
    // becomes one pair of the map; no file (no line) leaves the map empty.
    public void loadData(WordRequestDTO requestDTO) {
        // start again from an empty dictionary
        wordMap = new LinkedHashMap<>();

        // one line of the file = one pair of words
        for (String line : requestDTO.getLineList()) {
            // limit 2: only the FIRST "=" separates, the meaning may hold more
            String[] partArray = line.split(Constants.SEPARATOR, Constants.LINE_PARTS);

            // skip blank or broken lines instead of stopping the whole load
            if ((partArray.length == Constants.LINE_PARTS) && !partArray[0].trim().isEmpty()) {
                Word word = new Word(partArray[0].trim(), partArray[1].trim());

                // the key is the English word in lower case
                wordMap.put(toKey(word.getEnglish()), word);
            }
        }
    }

    // Tells whether the English word is in the dictionary.
    public boolean isExistWord(String eng) {
        return wordMap.containsKey(toKey(eng));
    }

    // The brief's addWord: puts the pair into the map (a new word, or a new meaning for
    // an existing word) and overwrites the data file.
    public boolean addWord(String eng, String vi) {
        String key = toKey(eng);
        Word oldWord = wordMap.put(key, new Word(eng, vi));

        // save the change; on failure undo it
        try {
            updateDatabase();
            return true;
        } catch (Exception e) {
            // the file was not written: restore the previous state of the map
            if (oldWord == null) {
                wordMap.remove(key);
            } else {
                // the word existed before: give it back its old meaning
                wordMap.put(key, oldWord);
            }

            return false;
        }
    }

    // The brief's removeWord: removes the pair and overwrites the data file.
    public boolean removeWord(String eng) {
        String key = toKey(eng);
        Word oldWord = wordMap.remove(key);

        // nothing was removed: the word is not in the dictionary
        if (oldWord == null) {
            return false;
        }

        // save the change; on failure undo it
        try {
            updateDatabase();
            return true;
        } catch (Exception e) {
            // the file was not written: the word stays in the dictionary
            wordMap.put(key, oldWord);
            return false;
        }
    }

    // The brief's translate: the Vietnamese meaning of an English word.
    public String translate(String eng) {
        Word word = wordMap.get(toKey(eng));

        // not found: the caller shows "empty"
        if (word == null) {
            return null;
        }

        return word.getVietnamese();
    }

    // The brief's updateDatabase: writes every pair into the data file, replacing the old
    // content (the writing itself is done by utils/FileUtils).
    private void updateDatabase() throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // Word.toString() gives the line "english=vietnamese"
        for (Word word : wordMap.values()) {
            lineList.add(word.toString());
        }

        FileUtils.writeLines(Constants.DATA_FILE, lineList);
    }

    // The key of the map: the English word in lower case, so "Cat", "cat" and "CAT" are
    // the same entry.
    private String toKey(String eng) {
        return eng.trim().toLowerCase();
    }
}
