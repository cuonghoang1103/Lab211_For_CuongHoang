package dto;

/**
 * DTO main -> controller: the request id chosen by the manager.
 *
 * @author HE176322
 */
public class TransactionRequestDTO {

    // Request id typed.
    private String id;

    // JavaBean constructor.
    public TransactionRequestDTO() {
    }

    // Returns the id.
    public String getId() {
        return id;
    }

    // Changes the id.
    public void setId(String id) {
        this.id = id;
    }
}
