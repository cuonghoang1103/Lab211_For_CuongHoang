package repository;

import constants.Constants;
import constants.Message;
import exceptions.ExceptionHandle;
import java.io.IOException;
import java.util.ArrayList;
import model.Config;
import utils.FileUtils;

/**
 * REPOSITORY: the only class that knows the config is stored in the file
 * config.properties and how one line of that file looks ("KEY=value").
 *
 * @author HE176322
 */
public class ConfigRepository {

    // Creates the repository.
    public ConfigRepository() {
    }

    // Tells whether config.properties exists (the brief's first question).
    public boolean isConfigExist() {
        return FileUtils.isFile(Constants.CONFIG_FILE);
    }

    // The brief's readFileConfig: fills the config from config.properties.
    public Config readFileConfig(Config config) throws ExceptionHandle {
        ArrayList<String> lines;
        // any IO failure becomes the brief's message
        try {
            lines = FileUtils.readLines(Constants.CONFIG_FILE);
        } catch (IOException e) {
            // missing, locked or unreadable file
            throw new ExceptionHandle(Message.CANNOT_READ);
        }
        // one line of the file = one setting
        for (String line : lines) {
            String text = line.trim();
            int separator = text.indexOf(Constants.KEY_VALUE_SEPARATOR);
            // skip blank lines, comments and lines without "="
            if (text.isEmpty() || text.startsWith(Constants.COMMENT_PREFIX)
                    || separator < 0) {
                continue;
            }
            String key = text.substring(0, separator).trim();
            // the value may contain "=" itself, so cut at the FIRST one only
            String value = text.substring(separator + 1).trim();
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
        return config;
    }

    // The brief's createFileConfig: writes the three settings into config.properties, in
    // the order the brief lists them.
    public void createFileConfig(Config config) throws ExceptionHandle {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(Constants.KEY_COPY_FOLDER + Constants.KEY_VALUE_SEPARATOR
                + config.getCopyFolder());
        lines.add(Constants.KEY_DATA_TYPE + Constants.KEY_VALUE_SEPARATOR
                + config.getDataType());
        lines.add(Constants.KEY_PATH + Constants.KEY_VALUE_SEPARATOR + config.getPath());
        // any IO failure becomes the brief's message
        try {
            FileUtils.writeLines(Constants.CONFIG_FILE, lines);
        } catch (IOException e) {
            // read-only folder, a folder named config.properties, full disk...
            throw new ExceptionHandle(Message.CANNOT_CREATE);
        }
    }
}
