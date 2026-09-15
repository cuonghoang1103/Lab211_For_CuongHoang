package constants;

/**
 * The three kinds of candidate of the brief, with what the program needs to know about
 * each: the type code the brief prints (0, 1, 2), the menu number that creates it (1, 2,
 * 3), the word used in messages and the banner that heads its group in the listing.
 *
 * @author HE176322
 */
public enum CandidateType {

    // Candidate type 0, created by menu option 1.
    EXPERIENCE(0, 1, "Experience", "===========EXPERIENCE CANDIDATE============"),
    // Candidate type 1, created by menu option 2.
    FRESHER(1, 2, "Fresher", "==========FRESHER CANDIDATE=============="),
    // Candidate type 2, created by menu option 3.
    INTERN(2, 3, "Intern", "===========INTERN CANDIDATE==============");

    // Type code printed in the search result (the brief: 0, 1, 2).
    private final int code;
    // Menu number that creates this kind of candidate.
    private final int menuChoice;
    // Word used in "Create ...
    private final String label;
    // Heading of this group on the listing screen.
    private final String banner;

    // Creates one constant; enum constructors are always private.
    CandidateType(int code, int menuChoice, String label, String banner) {
        this.code = code;
        this.menuChoice = menuChoice;
        this.label = label;
        this.banner = banner;
    }

    // Returns the type code.
    public int getCode() {
        return code;
    }

    // Returns the menu number.
    public int getMenuChoice() {
        return menuChoice;
    }

    // Returns the word used in messages.
    public String getLabel() {
        return label;
    }

    // Returns the group heading.
    public String getBanner() {
        return banner;
    }

    // Finds the type with the given code (what the user types in search).
    public static CandidateType fromCode(int code) {
        // look at each of the three types once
        for (CandidateType type : values()) {
            // this constant carries the typed code
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    // Finds the type created by a menu number.
    public static CandidateType fromMenuChoice(int menuChoice) {
        // look at each of the three types once
        for (CandidateType type : values()) {
            // this constant is created by that menu number
            if (type.menuChoice == menuChoice) {
                return type;
            }
        }
        return null;
    }
}
