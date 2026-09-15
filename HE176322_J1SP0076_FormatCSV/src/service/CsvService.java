package service;

import constants.Constants;
import constants.Message;
import dto.CsvResponseDTO;
import model.Customer;
import repository.CsvRepository;

/**
 * SERVICE: the brief's formatAddress and formatName (string manipulation of dataCSV), and
 * the way from the controller to the repository.
 *
 * @author HE176322
 */
public class CsvService {

    // Holds dataCSV; this service reads it and sets the formatted text.
    private CsvRepository csvRepository = new CsvRepository();
    // Strategy of option 2.
    private CustomerFormatter addressFormatter = new AddressFormatter();
    // Strategy of option 3.
    private CustomerFormatter nameFormatter = new NameFormatter();

    // Creates the service with an empty repository.
    public CsvService() {
    }

    // Option 1: loads the file (the repository's importCSV).
    public void importCSV(String path) throws Exception {
        csvRepository.importCSV(path);
    }

    // Option 4: saves dataCSV (the repository's exportCSV).
    public void exportCSV(String path) throws Exception {
        csvRepository.exportCSV(path);
    }

    // Returns the current dataCSV.
    public String getDataCSV() {
        return csvRepository.getDataCSV();
    }

    // Packs the current dataCSV for the view.
    public CsvResponseDTO getData() {
        return new CsvResponseDTO(csvRepository.getDataCSV());
    }

    // Function 2 (formatAddress): removes the redundant whitespace of every Address and
    // sets the result to dataCSV.
    public void formatAddress(String dataCSV) throws Exception {
        csvRepository.setDataCSV(formatData(dataCSV, addressFormatter));
    }

    // Function 3 (formatName): removes the redundant whitespace of every Name,
    // capitalises each word, and sets the result to dataCSV.
    public String formatName(String dataCSV) throws Exception {
        String result = formatData(dataCSV, nameFormatter);
        csvRepository.setDataCSV(result);
        return result;
    }

    // Applies one formatter to every row.
    private String formatData(String dataCSV, CustomerFormatter formatter)
            throws Exception {
        // nothing to format before an import
        if (dataCSV == null) {
            throw new Exception(Message.NO_DATA);
        }
        StringBuilder result = new StringBuilder();
        String[] lines = dataCSV.split(Constants.LINE_SPLIT);
        // every row of the file, in order
        for (int i = 0; i < lines.length; i++) {
            // rows are joined with a line break, none after the last one
            if (i > 0) {
                result.append(Constants.NEW_LINE);
            }
            result.append(formatLine(lines[i], formatter));
        }
        return result.toString();
    }

    // Fixes one row.
    private String formatLine(String line, CustomerFormatter formatter) {
        // limit 5: an address that itself contains "," stays in one piece,
        // and a trailing empty Address is kept instead of dropped
        String[] fields = line.split(Constants.FIELD_SEPARATOR, Constants.FIELD_COUNT);
        // not a full row: leave it untouched
        if (fields.length < Constants.FIELD_COUNT) {
            return line;
        }
        Customer customer = new Customer(fields[Constants.ID_INDEX].trim(),
                fields[Constants.NAME_INDEX].trim(), fields[Constants.EMAIL_INDEX].trim(),
                fields[Constants.PHONE_INDEX].trim(),
                fields[Constants.ADDRESS_INDEX].trim());
        formatter.format(customer);
        return customer.toString();
    }
}
