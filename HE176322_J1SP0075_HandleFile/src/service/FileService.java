package service;

import constants.Message;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.TextFile;
import utils.FileUtils;

/**
 * SERVICE: the five methods the brief names (checkInputPath,
 * getAllFileNameJavaInDirectory, getFileWithSizeGreaterThanInput, appendContentToFile,
 * countCharacter).
 *
 * @author HE176322
 */
public class FileService {

    // Creates the service; it holds no data.
    public FileService() {
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
        FileUtils.appendLine(path, contentInput);
        return true;
    }

    // Function 5: counts the words ("character which are separated by a whitespace") of a
    // text file.
    public int countCharacter(String path) throws Exception {
        // the brief's error
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }
        TextFile textFile = new TextFile(path, FileUtils.readLines(path));
        return textFile.countWords();
    }

    // Takes the names of the files, which is all the screen shows.
    public ArrayList<String> getFileNames(File[] files) {
        ArrayList<String> names = new ArrayList<>();
        // one name per file
        for (File file : files) {
            names.add(file.getName());
        }
        Collections.sort(names);
        return names;
    }
}
