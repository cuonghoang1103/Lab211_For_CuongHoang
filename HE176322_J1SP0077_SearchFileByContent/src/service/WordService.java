package service;

import constants.Message;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.TextFile;
import utils.FileUtils;

/**
 * SERVICE and Strategy CONTEXT: the two methods the brief names, countWordInFile and
 * getFileNameContainsWordInDirectory.
 *
 * @author HE176322
 */
public class WordService {

    // The rule of "a match", chosen by whoever creates this service.
    private WordMatcher wordMatcher;

    // Creates the service with the matching rule it must use.
    public WordService(WordMatcher wordMatcher) {
        this.wordMatcher = wordMatcher;
    }

    // Function 1: number of occurrences of a word in a file.
    public int countWordInFile(String fileSource, String word) throws Exception {
        checkWord(word);
        // nothing at this path
        if (!FileUtils.isExist(fileSource)) {
            throw new Exception(String.format(Message.FILE_NOT_FOUND, fileSource));
        }
        // a folder has no lines to count
        if (!FileUtils.isFile(fileSource)) {
            throw new Exception(String.format(Message.NOT_A_FILE, fileSource));
        }
        TextFile textFile = new TextFile(new File(fileSource).getName(),
                FileUtils.readLines(fileSource));
        int count = 0;
        // add the matches of every line
        for (String line : textFile.getLines()) {
            count += wordMatcher.countMatches(line, word);
        }
        return count;
    }

    // Function 2: names of the files directly inside a folder whose content contains the
    // word (sub-folders are not searched).
    // brief: the return type List<String> is the brief's own signature
    public List<String> getFileNameContainsWordInDirectory(String source, String word)
            throws Exception {
        checkWord(word);
        // nothing at this path
        if (!FileUtils.isExist(source)) {
            throw new Exception(String.format(Message.FOLDER_NOT_FOUND, source));
        }
        // a file is not a folder to search in
        if (FileUtils.isFile(source)) {
            throw new Exception(String.format(Message.NOT_A_FOLDER, source));
        }
        ArrayList<String> names = new ArrayList<>();
        // look inside every entry of the folder
        for (File file : FileUtils.listFiles(source)) {
            // only files, and only those with at least one match
            if (file.isFile() && countWordInFile(file.getPath(), word) > 0) {
                names.add(file.getName());
            }
        }
        Collections.sort(names);
        return names;
    }

    // Refuses a blank word: every line would "contain" it.
    private void checkWord(String word) throws Exception {
        // nothing to look for
        if (word == null || word.isEmpty()) {
            throw new Exception(Message.WORD_BLANK);
        }
    }
}
