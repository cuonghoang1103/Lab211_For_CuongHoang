package service;

import constants.Constants;
import constants.Message;
import dto.CsvRequestDTO;
import model.Customer;
import repository.CsvRepository;

/**
 * SERVICE: the brief's formatAddress and formatName (string manipulation of dataCSV), and
 * the way from the controller to the repository that keeps dataCSV. Called only by the
 * controller; no print, no keyboard, no file.
 *
 * @author HE176322
 */
public class CsvService {

    // Keeps dataCSV (Service -> Repository -> Model).
    private CsvRepository csvRepository;

    // Strategy of option 2.
    private ICustomerFormatter addressFormatter;

    // Strategy of option 3.
    private ICustomerFormatter nameFormatter;

    // Creates the service with an empty repository and its two strategies.
    public CsvService() {
        csvRepository = new CsvRepository();
        addressFormatter = new AddressFormatter();
        nameFormatter = new NameFormatter();
    }

    // Option 1 (importCSV): the repository keeps the lines main read as dataCSV.
    public void importCSV(CsvRequestDTO requestDTO) {
        csvRepository.importCSV(requestDTO);
    }

    // Option 4 (exportCSV): the repository writes dataCSV into the new file.
    public void exportCSV(String path) throws Exception {
        csvRepository.exportCSV(path);
    }

    // Returns the current dataCSV (null before any import).
    public String getDataCSV() {
        return csvRepository.getDataCSV();
    }

    // Function 2 (formatAddress): removes the redundant whitespace of every Address and
    // sets the result to dataCSV.
    public void formatAddress(String dataCSV) throws Exception {
        csvRepository.setDataCSV(formatData(dataCSV, addressFormatter));
    }

    // Function 3 (formatName): removes the redundant whitespace of every Name,
    // capitalises each word, sets the result to dataCSV and returns it.
    public String formatName(String dataCSV) throws Exception {
        String result = formatData(dataCSV, nameFormatter);

        // "Set to global variable dataCSV", and give it back as the brief declares
        csvRepository.setDataCSV(result);
        return result;
    }

    // Applies one formatter to every row (Strategy: the one loop of the Context).
    private String formatData(String dataCSV, ICustomerFormatter formatter)
            throws Exception {
        StringBuilder result = new StringBuilder();
        String[] lineArray = new String[0];

        // nothing to format before an import
        if (dataCSV == null) {
            throw new Exception(Message.NO_DATA);
        }

        // cut dataCSV into its rows
        lineArray = dataCSV.split(Constants.LINE_SPLIT);

        // every row, in order
        for (int i = 0; i < lineArray.length; i++) {
            // rows are joined with a line break, none before the first one
            if (i > 0) {
                result.append(Constants.NEW_LINE);
            }

            // the row with its column fixed
            result.append(formatLine(lineArray[i], formatter));
        }

        return result.toString();
    }

    // Fixes one row: the 5 columns without their surrounding spaces, then the formatter
    // fixes its column, then the row is written back in the brief's form.
    private String formatLine(String line, ICustomerFormatter formatter) {
        String[] fieldArray = line.split(Constants.FIELD_SEPARATOR, Constants.FIELD_COUNT);
        Customer customer = new Customer();

        // limit 5 keeps an empty last Address, and an Address holding "," in one piece;
        // a row with fewer columns (an empty line) is left untouched
        if (fieldArray.length < Constants.FIELD_COUNT) {
            return line;
        }

        // one column per field of the model
        customer.setId(fieldArray[Constants.ID_INDEX].trim());
        customer.setName(fieldArray[Constants.NAME_INDEX].trim());
        customer.setEmail(fieldArray[Constants.EMAIL_INDEX].trim());
        customer.setPhone(fieldArray[Constants.PHONE_INDEX].trim());
        customer.setAddress(fieldArray[Constants.ADDRESS_INDEX].trim());

        // polymorphism: AddressFormatter or NameFormatter, then the row as text again
        formatter.format(customer);
        return customer.toString();
    }
}
