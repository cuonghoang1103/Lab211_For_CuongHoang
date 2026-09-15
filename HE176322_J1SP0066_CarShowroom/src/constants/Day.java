package constants;

/**
 * The brief's Enum Day: "List all days in a week".
 *
 * @author HE176322
 */
public enum Day {

    // Monday.
    MONDAY,
    // Tuesday.
    TUESDAY,
    // Wednesday.
    WEDNESDAY,
    // Thursday.
    THURSDAY,
    // Friday.
    FRIDAY,
    // Saturday.
    SATURDAY,
    // Sunday.
    SUNDAY;

    // The brief's lookup "Day getDay(String day), if day is not a Day Enum then return
    // null".
    public static Day getDay(String day) {
        // nothing typed: no day
        if (day == null) {
            return null;
        }
        // compare the text with the name of every day
        for (Day candidate : values()) {
            // found the day the user meant
            if (candidate.name().equalsIgnoreCase(day.trim())) {
                return candidate;
            }
        }
        return null;
    }
}
