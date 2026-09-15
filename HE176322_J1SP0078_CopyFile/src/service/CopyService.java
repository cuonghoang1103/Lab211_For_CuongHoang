package service;

import constants.Message;
import dto.ConfigRequestDTO;
import dto.CopyResponseDTO;
import exceptions.ExceptionHandle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Config;
import repository.ConfigRepository;
import utils.FileUtils;

/**
 * SERVICE: the business of the copy job - check the config (checkConfig) and copy the
 * accepted files (copyFile), the two methods of the brief that are more than loading and
 * saving.
 *
 * @author HE176322
 */
public class CopyService {

    // Loads and saves config.properties.
    private ConfigRepository configRepository;
    // The config that passed checkConfig; copyFiles() copies with it.
    private Config config;

    // Creates the service with its repository and an empty config.
    public CopyService() {
        configRepository = new ConfigRepository();
        config = new Config();
    }

    // Tells whether config.properties exists.
    public boolean isConfigExist() {
        return configRepository.isConfigExist();
    }

    // Saves the config the user typed into config.properties.
    public void saveConfig(ConfigRequestDTO requestDTO) throws ExceptionHandle {
        Config typed = new Config(requestDTO.getCopyFolder(), requestDTO.getDataType(),
                requestDTO.getPath());
        configRepository.createFileConfig(typed);
    }

    // Prepares the copy: saves a freshly typed config first, then reads the config FILE
    // (the brief: "perform next steps with existed file") and checks it.
    public void loadConfig(ConfigRequestDTO requestDTO) throws ExceptionHandle {
        // the brief: no config file -> create it from what the user typed
        if (requestDTO.isNewConfig()) {
            saveConfig(requestDTO);
        }
        config = configRepository.readFileConfig(new Config());
        checkConfig(config);
    }

    // The brief's checkConfig, in the order of the brief's error box.
    public void checkConfig(Config config) throws ExceptionHandle {
        // COPY_FOLDER: not input yet
        if (isBlank(config.getCopyFolder())) {
            throw new ExceptionHandle(Message.SOURCE_NOT_INPUT);
        }
        // COPY_FOLDER: the source folder does not exist
        if (!FileUtils.isFolder(config.getCopyFolder())) {
            throw new ExceptionHandle(Message.SOURCE_NOT_FOUND);
        }
        // DATA_TYPE: not input yet
        if (isBlank(config.getDataType())) {
            throw new ExceptionHandle(Message.DATA_TYPE_NOT_INPUT);
        }
        // PATH: not input yet
        if (isBlank(config.getPath())) {
            throw new ExceptionHandle(Message.DESTINATION_NOT_INPUT);
        }
        // PATH: input but not existed -> create it; failing that is an error
        if (!FileUtils.makeFolder(config.getPath())) {
            throw new ExceptionHandle(Message.CANNOT_MAKE_DESTINATION);
        }
        // copying a folder onto itself would empty every file: refuse it
        if (isSameFolder(config)) {
            throw new ExceptionHandle(Message.SAME_FOLDER);
        }
    }

    // The brief's copyFile: copies every accepted file of COPY_FOLDER into PATH, as
    // bytes, and keeps the name of each file that arrived intact.
    // brief: public List<String> copyFile(Config config)
    public List<String> copyFile(Config config) {
        ArrayList<String> copied = new ArrayList<>();
        DataTypeFilter filter = new DataTypeFilter(config.getDataType());
        // one accepted file of the source folder per turn, in name order
        for (File source : FileUtils.listFiles(config.getCopyFolder(), filter)) {
            File target = new File(config.getPath(), source.getName());
            // a failed or different copy is left out of the list
            try {
                FileUtils.copyBinary(source, target);
                // listed only when the copy is proved identical, byte for byte
                if (FileUtils.isSameContent(source, target)) {
                    copied.add(source.getName());
                }
            } catch (IOException e) {
                // unreadable source or unwritable target: skip this file
                continue;
            }
        }
        return copied;
    }

    // Runs copyFile with the config that passed loadConfig and packs the names for the
    // view.
    public CopyResponseDTO copyFiles() {
        CopyResponseDTO response = new CopyResponseDTO();
        response.setFileNames(new ArrayList<>(copyFile(config)));
        return response;
    }

    // Tells whether a setting was left empty.
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Tells whether COPY_FOLDER and PATH are the same folder on the disk.
    private boolean isSameFolder(Config config) throws ExceptionHandle {
        // canonical paths may fail for a broken path
        try {
            return FileUtils.isSamePath(config.getCopyFolder(), config.getPath());
        } catch (IOException e) {
            // the path cannot be resolved: treat it as a missing source
            throw new ExceptionHandle(Message.SOURCE_NOT_FOUND);
        }
    }
}
