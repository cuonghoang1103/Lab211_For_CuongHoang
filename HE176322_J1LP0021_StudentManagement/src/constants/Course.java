package constants;

/**
 * The three courses of the brief: "There are only three courses: Java, .Net, C/C++".
 *
 * @author HE176322
 */
public enum Course {

    // The Java course.
    JAVA("Java"),
    // The .Net course (".Net" is not a legal Java name, hence DOT_NET).
    DOT_NET(".Net"),
    // The C/C++ course.
    C_CPP("C/C++");

    // The name shown on screen and typed by the user.
    private final String label;

    // Enum constructor: attaches the screen label to each constant.
    Course(String label) {
        this.label = label;
    }

    // Returns the screen label.
    public String getLabel() {
        return label;
    }

    // Finds the course whose label equals the typed text, ignoring case ("java" is Java).
    public static Course fromLabel(String text) {
        // no text at all: no course
        if (text == null) {
            return null;
        }
        // compare the text with each of the three labels
        for (Course course : values()) {
            // the label matches: this is the course
            if (course.label.equalsIgnoreCase(text.trim())) {
                return course;
            }
        }
        return null;
    }

    // Lists the three labels, "Java, .Net, C/C++", built from the constants so the error
    // message can never drift from the enum.
    public static String labels() {
        StringBuilder all = new StringBuilder();
        // append every label, with a separator between two of them
        for (Course course : values()) {
            // not the first label: separate it from the previous one
            if (all.length() > 0) {
                all.append(Constants.COURSE_SEPARATOR);
            }
            all.append(course.label);
        }
        return all.toString();
    }

    // Polymorphism: the enum prints as its label, not as "DOT_NET".
    @Override
    public String toString() {
        return label;
    }
}
