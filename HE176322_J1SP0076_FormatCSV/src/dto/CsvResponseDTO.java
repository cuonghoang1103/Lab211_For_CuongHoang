package dto;

/**
 * DTO carrying the current CSV content FROM the controller OUT TO the view, so the user
 * can see what import/format produced.
 *
 * @author HE176322
 */
public class CsvResponseDTO {

    // The whole CSV as text, one row per line.
    private String dataCSV;

    // JavaBean constructor: an empty response.
    public CsvResponseDTO() {
    }

    // Creates the response with its content.
    public CsvResponseDTO(String dataCSV) {
        this.dataCSV = dataCSV;
    }

    // Returns the content.
    public String getDataCSV() {
        return dataCSV;
    }

    // Sets the content.
    public void setDataCSV(String dataCSV) {
        this.dataCSV = dataCSV;
    }
}
