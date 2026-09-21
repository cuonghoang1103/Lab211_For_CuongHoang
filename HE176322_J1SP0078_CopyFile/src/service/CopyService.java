package service;

import constants.Message;
import dto.ConfigRequestDTO;
import exceptions.HandleException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Config;
import repository.ConfigRepository;
import utils.FileUtils;

/**
 * SERVICE: the business of the copy job - check the config (checkConfig) and copy the
 * accepted files (copyFile), the two methods of the brief that are more than reading and
 * saving the config. Called only by the controller; no print, no keyboard.
 *
 * @author HE176322
 */
public class CopyService {

    // Keeps the lines of config.properties and saves the config (Service -> Repository).
    private ConfigRepository configRepository;

    // Creates the service with its repository.
    public CopyService() {
        configRepository = new ConfigRepository();
    }

    // Saves the config the user typed into config.properties.
    public void saveConfig(ConfigRequestDTO requestDTO) throws HandleException {
        Config config = new Config(requestDTO.getCopyFolder(), requestDTO.getDataType(),
                requestDTO.getPath());

        // the repository writes it ("File Configure cannot create" when it cannot)
        configRepository.createFileConfig(config);
    }

    // Option 1: the repository keeps the lines main read, readFileConfig turns them into
    // the config, checkConfig checks it, then copyFile copies; the names come back sorted.
    public ArrayList<String> copyFiles(ConfigRequestDTO requestDTO) throws HandleException {
        Config config = new Config();

        // the brief's order: read, check (the first error stops here), copy
        configRepository.loadData(requestDTO);
        config = configRepository.readFileConfig(config);
        checkConfig(config);
        return new ArrayList<>(copyFile(config));
    }

    // The brief's checkConfig, in the order of the brief's error box.
    // brief: public void checkConfig(Config config) throws ExceptionHandle
    public void checkConfig(Config config) throws HandleException {
        // COPY_FOLDER: not input yet
        if (isBlank(config.getCopyFolder())) {
            throw new HandleException(Message.SOURCE_NOT_INPUT);
        }

        // COPY_FOLDER: the source folder does not exist
        if (!FileUtils.isFolder(config.getCopyFolder())) {
            throw new HandleException(Message.SOURCE_NOT_FOUND);
        }

        // DATA_TYPE: not input yet
        if (isBlank(config.getDataType())) {
            throw new HandleException(Message.DATA_TYPE_NOT_INPUT);
        }

        // PATH: not input yet
        if (isBlank(config.getPath())) {
            throw new HandleException(Message.DESTINATION_NOT_INPUT);
        }

        // PATH: input but not existed -> create it; failing that is an error
        if (!FileUtils.makeFolder(config.getPath())) {
            throw new HandleException(Message.CANNOT_MAKE_DESTINATION);
        }

        // copying a folder onto itself would empty every file: refuse it
        if (isSameFolder(config)) {
            throw new HandleException(Message.SAME_FOLDER);
        }
    }

    // The brief's copyFile: copies every accepted file of COPY_FOLDER into PATH, as
    // bytes, and keeps the name of each file that arrived intact.
    // brief: public List<String> copyFile(Config config)
    public List<String> copyFile(Config config) {
        ArrayList<String> fileNameList = new ArrayList<>();
        DataTypeFilter filter = new DataTypeFilter(config.getDataType());

        // one accepted file of the source folder per turn, in name order
        for (File source : FileUtils.listFiles(config.getCopyFolder(), filter)) {
            // listed only when the copy is proved identical, byte for byte
            if (copyOneFile(source, new File(config.getPath(), source.getName()))) {
                fileNameList.add(source.getName());
            }
        }

        return fileNameList;
    }

    // Copies one file as bytes; true only when the copy is identical to the source.
    private boolean copyOneFile(File source, File target) {
        // a failed or different copy is left out of the list
        try {
            FileUtils.copyBinary(source, target);
            return FileUtils.isSameContent(source, target);
        } catch (IOException e) {
            // unreadable source or unwritable target: skip this file
            return false;
        }
    }

    // Tells whether a setting was left empty.
    private boolean isBlank(String value) {
        return (value == null) || value.trim().isEmpty();
    }

    // Tells whether COPY_FOLDER and PATH are the same folder on the disk.
    private boolean isSameFolder(Config config) throws HandleException {
        // canonical paths may fail for a broken path
        try {
            return FileUtils.isSamePath(config.getCopyFolder(), config.getPath());
        } catch (IOException e) {
            // the path cannot be resolved: treat it as a missing source
            throw new HandleException(Message.SOURCE_NOT_FOUND);
        }
    }
}
