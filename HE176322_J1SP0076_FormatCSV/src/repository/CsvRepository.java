package repository;

import constants.Constants;
import constants.Message;
import dto.CsvRequestDTO;
import java.util.ArrayList;
import java.util.Arrays;
import utils.FileUtils;

/**
 * REPOSITORY: holds the data of the program - the brief's "global variable dataCSV" - and
 * only simple CRUD on it: keep what main read, give it back, replace it, save it. No
 * rule, no print, no reading of files.
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

    // Replaces the content (used by the two formats).
    public void setDataCSV(String dataCSV) {
        this.dataCSV = dataCSV;
    }

    // Function 1 (importCSV), last step: the lines main read become dataCSV.
    public void importCSV(CsvRequestDTO requestDTO) {
        dataCSV = String.join(Constants.NEW_LINE, requestDTO.getLineList());
    }

    // Function 4 (exportCSV): writes dataCSV into a new file (an existing file is
    // replaced); the writing itself is FileUtils' job.
    public void exportCSV(String path) throws Exception {
        ArrayList<String> lineList = new ArrayList<>();

        // nothing to export before an import
        if (dataCSV == null) {
            throw new Exception(Message.NO_DATA);
        }

        // one row of dataCSV = one line of the new file
        lineList.addAll(Arrays.asList(dataCSV.split(Constants.LINE_SPLIT)));
        FileUtils.writeLines(path, lineList);
    }
}
