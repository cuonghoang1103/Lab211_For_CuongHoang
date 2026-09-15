package dto;

/**
 * DTO main -> controller: the text searched in the asset names.
 *
 * @author HE176322
 */
public class AssetRequestDTO {

    // Text searched in the names.
    private String keyword;

    // JavaBean constructor.
    public AssetRequestDTO() {
    }

    // Returns the search text.
    public String getKeyword() {
        return keyword;
    }

    // Changes the search text.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
