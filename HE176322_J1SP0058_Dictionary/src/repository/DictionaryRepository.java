package repository;

import constants.Constants;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Word;
import utils.FileUtils;

/**
 * REPOSITORY: holds the dictionary and performs simple CRUD on it, keeping the data file
 * equal to what is in memory.
 *
 * @author HE176322
 */
public class DictionaryRepository {

    // The dictionary: lower-case English word -> the pair of words.
    private LinkedHashMap<String, Word> wordMap = new LinkedHashMap<>();

    // Creates an empty dictionary; loadData() fills it from the file.
    public DictionaryRepository() {
    }

    // The brief's loadData(): if the data file exists, loads every "english=vietnamese"
    // line into the map; if not, the map stays empty.
    public void loadData() throws Exception {
        wordMap = new LinkedHashMap<>();
        // the brief: no data file yet -> start with an empty dictionary
        if (!FileUtils.isFileExist(Constants.DATA_FILE)) {
            return;
        }
        ArrayList<String> lines = FileUtils.readLines(Constants.DATA_FILE);
        // one line of the file = one pair of words
        for (String line : lines) {
            // limit 2: only the FIRST "=" separates, the meaning may hold more
            String[] parts = line.split(Constants.SEPARATOR, Constants.LINE_PARTS);
            // skip blank or broken lines instead of stopping the whole load
            if (parts.length == Constants.LINE_PARTS && !parts[0].trim().isEmpty()) {
                Word word = new Word(parts[0].trim(), parts[1].trim());
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
    // content.
    private void updateDatabase() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        // Word.toString() gives the line "english=vietnamese"
        for (Word word : wordMap.values()) {
            lines.add(word.toString());
        }
        FileUtils.writeLines(Constants.DATA_FILE, lines);
    }

    // The key of the map: the English word in lower case, so "Cat", "cat" and "CAT" are
    // the same entry.
    private String toKey(String eng) {
        return eng.trim().toLowerCase();
    }
}
