package repository;

import constants.Constants;
import constants.Message;
import dto.ConfigRequestDTO;
import exceptions.HandleException;
import java.io.IOException;
import java.util.ArrayList;
import model.Config;
import utils.FileUtils;

/**
 * REPOSITORY: holds the data of the program - the lines of config.properties that main
 * read - and the brief's two simple operations on the config: readFileConfig (lines ->
 * Config) and createFileConfig (Config -> the file; the writing itself is FileUtils' job).
 * It is the only class that knows how one line of that file looks ("KEY=value"). No
 * check, no print, no reading of files.
 *
 * @author HE176322
 */
public class ConfigRepository {

    // The lines of config.properties as main read them; null = the file was not read.
    private ArrayList<String> lineList;

    // Creates the repository; nothing has been read yet.
    public ConfigRepository() {
        lineList = null;
    }

    // Create: keeps the lines main read from config.properties.
    public void loadData(ConfigRequestDTO requestDTO) {
        lineList = requestDTO.getLineList();
    }

    // The brief's readFileConfig: fills the config from the lines of config.properties.
    // brief: public Config readFileConfig(Config config) throws ExceptionHandle
    public Config readFileConfig(Config config) throws HandleException {
        // nothing was read from the file: the brief's first error
        if (lineList == null) {
            throw new HandleException(Message.CANNOT_READ);
        }

        // one line of the file = one setting
        for (String line : lineList) {
            parseSetting(config, line.trim());
        }

        return config;
    }

    // The brief's createFileConfig: writes the three settings into config.properties, in
    // the order the brief lists them.
    // brief: public void createFileConfig(Config config) throws ExceptionHandle
    public void createFileConfig(Config config) throws HandleException {
        ArrayList<String> settingList = new ArrayList<>();

        // one "KEY=value" line per setting, in the brief's order
        settingList.add(String.format(Constants.SETTING_FORMAT, Constants.KEY_COPY_FOLDER,
                config.getCopyFolder()));
        settingList.add(String.format(Constants.SETTING_FORMAT, Constants.KEY_DATA_TYPE,
                config.getDataType()));
        settingList.add(String.format(Constants.SETTING_FORMAT, Constants.KEY_PATH,
                config.getPath()));

        // any IO failure becomes the brief's message
        try {
            FileUtils.writeLines(Constants.CONFIG_FILE, settingList);
        } catch (IOException e) {
            // read-only folder, a folder named config.properties, full disk...
            throw new HandleException(Message.CANNOT_CREATE);
        }
    }

    // Puts the value of one "KEY=value" line into the setting its key names; blank lines,
    // comments and lines without "=" are skipped.
    private void parseSetting(Config config, String text) {
        int separator = text.indexOf(Constants.KEY_VALUE_SEPARATOR);
        String key = "";
        String value = "";

        // skip blank lines, comments and lines without "="
        if (text.isEmpty() || text.startsWith(Constants.COMMENT_PREFIX) || (separator < 0)) {
            return;
        }

        // the value may contain "=" itself, so cut at the FIRST one only
        key = text.substring(0, separator).trim();
        value = text.substring(separator + 1).trim();

        // put the value into the setting its key names
        if (key.equalsIgnoreCase(Constants.KEY_COPY_FOLDER)) {
            config.setCopyFolder(value);
        } else if (key.equalsIgnoreCase(Constants.KEY_DATA_TYPE)) {
            // DATA_TYPE line
            config.setDataType(value);
        } else if (key.equalsIgnoreCase(Constants.KEY_PATH)) {
            // PATH line
            config.setPath(value);
        }
    }
}
