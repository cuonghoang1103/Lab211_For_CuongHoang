package repository;

import constants.Constants;
import constants.Message;
import java.util.ArrayList;
import java.util.Arrays;
import utils.FileUtils;

/**
 * REPOSITORY: holds the brief's "global variable dataCSV" and loads/saves it - the
 * brief's importCSV and exportCSV.
 *
 * @author HE176322
 */
public class CsvRepository {

    // The brief's dataCSV: the whole file, one row per line; null = nothing imported.
    private String dataCSV;

    // Creates an empty repository (nothing imported yet).
    public CsvRepository() {
    }

    // Returns the current content.
    public String getDataCSV() {
        return dataCSV;
    }

    // Replaces the content (used by the formatters).
    public void setDataCSV(String dataCSV) {
        this.dataCSV = dataCSV;
    }

    // Function 1 (importCSV): reads the file into dataCSV.
    public void importCSV(String path) throws Exception {
        // the brief: "Check file exist or not"
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }
        ArrayList<String> lines = FileUtils.readLines(path);
        dataCSV = String.join(Constants.NEW_LINE, lines);
    }

    // Function 4 (exportCSV): writes dataCSV into a new file (an existing file is
    // replaced).
    public void exportCSV(String path) throws Exception {
        // nothing to export before an import
        if (dataCSV == null) {
            throw new Exception(Message.NO_DATA);
        }
        ArrayList<String> lines = new ArrayList<>(
                Arrays.asList(dataCSV.split(Constants.LINE_SPLIT)));
        FileUtils.writeLines(path, lines);
    }
}
