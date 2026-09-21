package constants;

/**
 * Numbers, formats and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option 1: create Experience candidates.
    public static final int MENU_EXPERIENCE = 1;

    // Menu option 2: create Fresher candidates.
    public static final int MENU_FRESHER = 2;

    // Menu option 3: create Intern candidates.
    public static final int MENU_INTERN = 3;

    // Menu option 4: search.
    public static final int MENU_SEARCH = 4;

    // Menu option 5: exit; also the largest option.
    public static final int MENU_EXIT = 5;

    // Smallest candidate type code (0 = Experience).
    public static final int TYPE_MIN = 0;

    // Largest candidate type code (2 = Intern).
    public static final int TYPE_MAX = 2;

    // Earliest legal birth year (the brief: 1900..Current Year).
    public static final int MIN_BIRTH_YEAR = 1900;

    // Birth date: exactly 4 digits (the brief: length is 4 character).
    public static final String BIRTH_YEAR_REGEX = "\\d{4}";

    // Phone: digits only, at least 10 of them (the brief).
    public static final String PHONE_REGEX = "\\d{10,}";

    // Email: an account name without '@' or spaces, one '@', then a domain of two or more
    // non-empty labels separated by dots (annguyen@fpt.edu.vn).
    public static final String EMAIL_REGEX = "[^@\\s]+@[^@\\s.]+(\\.[^@\\s.]+)+";

    // Smallest year of experience (the brief: 0 to 100).
    public static final int MIN_EXPERIENCE = 0;

    // Largest year of experience (the brief: 0 to 100).
    public static final int MAX_EXPERIENCE = 100;

    // Answer that repeats the create loop.
    public static final String YES = "Y";

    // Answer that stops the create loop.
    public static final String NO = "N";

    // The brief's "Candidate name (First Name + Last Name)": first, a space, last.
    public static final String FULL_NAME_FORMAT = "%s %s";

    // The brief's search-result line: name | birth date | address | phone | email | type.
    public static final String SUMMARY_FORMAT = "%s | %d | %s | %s | %s | %d";

    // A line of the listing: the six common columns, then the columns of the kind.
    public static final String DETAIL_FORMAT = "%s | %s";

    // The two columns only an Experience has: years of experience | skill.
    public static final String EXPERIENCE_FORMAT = "%d | %s";

    // The three columns only a Fresher has: graduation date | rank | education.
    public static final String FRESHER_FORMAT = "%s | %s | %s";

    // The three columns only an Intern has: majors | semester | university name.
    public static final String INTERN_FORMAT = "%s | %d | %s";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
