package constants;

/**
 * Characters and patterns the path analysis depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Separator between the parts of a Windows path.
    public static final char BACKSLASH = '\\';
    // Separator between the file name and its extension.
    public static final char DOT = '.';
    // The same backslash written as a REGEX for String.split: a backslash is special in a
    // regex, so it has to be doubled once more.
    public static final String BACKSLASH_REGEX = "\\\\";
    // A Windows path to a file: a drive letter and ":", then one or more "\name" parts; a
    // name may not contain \ / : * ?
    public static final String PATH_PATTERN = "[A-Za-z]:(\\\\[^\\\\/:*?\"<>|]+)+";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
