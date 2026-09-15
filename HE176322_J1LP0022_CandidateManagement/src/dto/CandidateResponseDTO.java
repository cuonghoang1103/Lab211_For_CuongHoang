package dto;

/**
 * DTO carrying one candidate FROM the service OUT TO the view - a JavaBean.
 *
 * @author HE176322
 */
public class CandidateResponseDTO {

    // "First Last" - one line of the search-screen listing.
    private String fullName;
    // Six common columns - one line of the search result.
    private String summary;
    // Six common columns plus the kind's own - one line after creating.
    private String detail;

    // JavaBean constructor: an empty row, filled through the setters.
    public CandidateResponseDTO() {
    }

    // Returns the full name.
    public String getFullName() {
        return fullName;
    }

    // Sets the full name.
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Returns the six-column line.
    public String getSummary() {
        return summary;
    }

    // Sets the six-column line.
    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Returns the full line.
    public String getDetail() {
        return detail;
    }

    // Sets the full line.
    public void setDetail(String detail) {
        this.detail = detail;
    }
}
