package constants;

/**
 * The four fixed task types of the brief: 1 Code, 2 Test, 3 Design, 4 Review.
 *
 * @author HE176322
 */
public enum TaskType {

    // ID 1: writing code.
    CODE(1, "Code"),
    // ID 2: testing.
    TEST(2, "Test"),
    // ID 3: designing.
    DESIGN(3, "Design"),
    // ID 4: reviewing.
    REVIEW(4, "Review");

    // The number typed at "Task Type:".
    private final int id;
    // The name printed in the "Task Type" column.
    private final String name;

    // Creates one type; only the four values above exist.
    private TaskType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Returns the ID of the type.
    public int getId() {
        return id;
    }

    // Returns the name of the type.
    public String getName() {
        return name;
    }

    // Finds the type whose ID was typed.
    public static TaskType fromId(int id) {
        // look at the four types one by one
        for (TaskType type : values()) {
            // this type has the typed ID
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    // Smallest legal ID, read from the table so the message stays right when a type is
    // added.
    public static int firstId() {
        return values()[0].getId();
    }

    // Largest legal ID.
    public static int lastId() {
        return values()[values().length - 1].getId();
    }
}
