package service;

import constants.Message;
import dto.WordRequestDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.TextFile;
import repository.TextFileRepository;

/**
 * SERVICE and Strategy CONTEXT: the two methods the brief names, countWordInFile and
 * getFileNameContainsWordInDirectory. It counts on the files the repository keeps; no
 * print, no keyboard, no reading of files.
 *
 * @author HE176322
 */
public class WordService {

    // The rule of "a match", chosen by whoever creates this service.
    private IWordMatcher wordMatcher;

    // Keeps the files main read (Service -> Repository -> Model).
    private TextFileRepository textFileRepository;

    // Creates the service with the matching rule it must use and an empty repository.
    public WordService(IWordMatcher wordMatcher) {
        this.wordMatcher = wordMatcher;
        textFileRepository = new TextFileRepository();
    }

    // Keeps the text file main read (option 1).
    public void addTextFile(WordRequestDTO requestDTO) {
        textFileRepository.addTextFile(requestDTO);
    }

    // Keeps the files main read from the folder (option 2).
    public void addFolder(WordRequestDTO requestDTO) {
        textFileRepository.addFolder(requestDTO);
    }

    // Function 1: number of occurrences of a word in the file kept at this path.
    public int countWordInFile(String fileSource, String word) throws Exception {
        TextFile textFile = textFileRepository.getTextFile(fileSource);

        // nothing to look for
        checkWord(word);

        // no file was read at this path
        if (textFile == null) {
            throw new Exception(String.format(Message.FILE_NOT_FOUND, fileSource));
        }

        return countWord(textFile, word);
    }

    // Function 2: names of the files directly inside a folder whose content contains the
    // word (sub-folders are not searched).
    // brief: the return type List<String> is the brief's own signature
    public List<String> getFileNameContainsWordInDirectory(String source, String word)
            throws Exception {
        ArrayList<String> fileNameList = new ArrayList<>();
        ArrayList<TextFile> textFileList = textFileRepository.getTextFileList(source);

        // nothing to look for
        checkWord(word);

        // no folder was read at this path
        if (textFileList == null) {
            throw new Exception(String.format(Message.FOLDER_NOT_FOUND, source));
        }

        // keep the files with at least one match - the same count as option 1
        for (TextFile textFile : textFileList) {
            // a file with no match is left out
            if (countWord(textFile, word) > 0) {
                fileNameList.add(textFile.getName());
            }
        }

        // the folder order differs between machines; sorted A-Z is always the same
        Collections.sort(fileNameList);
        return fileNameList;
    }

    // Adds the matches of every line of the file (the strategy decides what a match is).
    private int countWord(TextFile textFile, String word) {
        int count = 0;

        // add the matches of every line
        for (String line : textFile.getLineList()) {
            count += wordMatcher.countMatches(line, word);
        }

        return count;
    }

    // Refuses a blank word: every line would "contain" it.
    private void checkWord(String word) throws Exception {
        // nothing to look for
        if ((word == null) || word.isEmpty()) {
            throw new Exception(Message.WORD_BLANK);
        }
    }
}
