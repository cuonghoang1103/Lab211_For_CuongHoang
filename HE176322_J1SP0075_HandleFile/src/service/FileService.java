package service;

import constants.Message;
import dto.FileRequestDTO;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.TextFile;
import repository.TextFileRepository;
import utils.FileUtils;

/**
 * SERVICE: the five methods the brief names (checkInputPath,
 * getAllFileNameJavaInDirectory, getFileWithSizeGreaterThanInput, appendContentToFile,
 * countCharacter). Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class FileService {

    // Keeps the text files main read and adds content to files (Service -> Repository).
    private TextFileRepository textFileRepository;

    // Creates the service with an empty repository.
    public FileService() {
        textFileRepository = new TextFileRepository();
    }

    // Function 1: tells what the path is.
    public void checkInputPath(String path) throws Exception {
        // nothing at this path
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        // it exists and is a file
        if (FileUtils.isFile(path)) {
            throw new Exception(Message.PATH_TO_FILE);
        }

        // it exists and is not a file: a directory
        throw new Exception(Message.PATH_TO_DIRECTORY);
    }

    // Function 2: the names of the .java files directly inside a directory.
    // brief: the return type List<String> is the brief's own signature
    public List<String> getAllFileNameJavaInDirectory(String path) throws Exception {
        // the brief's error
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        return FileUtils.listFileNames(path, new JavaFileFilter());
    }

    // Function 3: the files of a directory bigger than size KB.
    // brief: public static File[] getFileWithSizeGreaterThanInput(String path, int size) -
    // an object method here (the lecturer: static only in utils, constants and main)
    public File[] getFileWithSizeGreaterThanInput(String path, int size)
            throws Exception {
        // the brief's error
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        return FileUtils.listFiles(path, new SizeFileFilter(size));
    }

    // Function 4: adds the content as a new line at the end of the file.
    public boolean appendContentToFile(String path, String contentInput)
            throws Exception {
        // the brief: a missing file is an error, never silently created
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        // the repository writes it ("Cannot write to file" for a folder)
        textFileRepository.appendContent(path, contentInput);
        return true;
    }

    // Keeps the text file main read (option 5), before its words are counted.
    public void addTextFile(FileRequestDTO requestDTO) {
        textFileRepository.addTextFile(requestDTO);
    }

    // Function 5: counts the words ("character which are separated by a whitespace") of the
    // text file kept at this path.
    public int countCharacter(String path) throws Exception {
        TextFile textFile = textFileRepository.getTextFile(path);

        // the brief's error: no file was read at this path
        if (textFile == null) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        return textFile.countWords();
    }

    // Takes the names of the files, which is all the screen shows.
    public ArrayList<String> getFileNameList(File[] fileArray) {
        ArrayList<String> fileNameList = new ArrayList<>();

        // one name per file
        for (File file : fileArray) {
            fileNameList.add(file.getName());
        }

        // the disk order differs between machines; sorted is always the same
        Collections.sort(fileNameList);
        return fileNameList;
    }
}
